package com.huawei.classroom.student.h62;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MyDaemon extends Thread{
    private final MyDeamonConfigVo config;
    private ServerSocket serverSocket;
    private Socket socket;
    private final int port;
    private final Map<String, String> passwords;
    private final String root;
    private PrintWriter out;
    private BufferedReader in;

    public MyDaemon(MyDeamonConfigVo config) {
        this.config = config;
        this.port = this.config.getPort();
        this.passwords = config.getPasswords();
        this.root = config.getRoot();
        this.serverSocket = null;
        this.socket = null;
        this.out = null;
        this.in = null;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(this.port);
            socket = serverSocket.accept();
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = in.readLine();
            while(line != null) {
                this.processMessage(line);
                line = in.readLine();
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processMessage(String line) {
        String[] str = line.split("\t");
        String request = str[0];
        if(request.equals(Request.Login)) {
            processLogIn(str);
        }
        if(request.equals(Request.getAllFiles)) {
            processGetFilesNameAsc(str);
        }
        if(request.equals(Request.writeBytes)) {
            processWriteByBytes(str);
        }
        if(request.equals(Request.getLength)) {
            processGetLength(str);
        }
        if(request.equals(Request.delete)) {
            processDelete(str);
        }
        if(request.equals(Request.exists)) {
            processExists(str);
        }
    }

    public void processLogIn(String[] str) {
        String username = str[1];
        String password = str[2];
        if(this.passwords.get(username).equals(password)) {
            out.write(Request.OK + "\r\n");
        } else {
            out.write(Request.WrongPassword + "\r\n");
        }
        out.flush();
    }

    public void processGetFilesNameAsc(String[] str) {
        String path = str[1];
        String filePath = this.root + path;
        List<File> allFiles = new ArrayList<>();
        File file = new File(filePath); // 构造文件
        if(file.isFile()) {
            allFiles.add(file);
        } else { // 将所有文件加入List中
            allFiles.addAll(Arrays.asList(file.listFiles()));
        }
        allFiles.sort((o1, o2) -> {
            if((o1.isFile() && o2.isFile()) || (o1.isDirectory() && o2.isDirectory())) {
                return o1.getName().compareTo(o2.getName());
            } else if(o1.isDirectory() && o2.isFile()) {
                return -1;
            } else {
                return 1;
            }
        });
        int cnt = allFiles.size();
        out.write(String.valueOf(cnt) + "\r\n");
        out.flush();

        for(File theFile : allFiles) {
            String name = theFile.getName();
            if(theFile.isDirectory()) {
                name += "/";
            }
            out.write(name + "\r\n");
            out.flush();
        }
    }

    public void processWriteByBytes(String[] str) {
        String fileName = this.root + str[1];
        try {
//            InputStream inBytes = socket.getInputStream();
//            byte[] data = new byte[4096];
//            int readed = inBytes.read(data);
//            String line = new String(data, 0, readed);
            String message = in.readLine();
            writeFile(fileName, message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processGetLength(String[] str) {
        String fileName = this.root + str[1];
        File file = new File(fileName);
        long len;
        if(file.isFile()) {
            len = file.length();
        } else {
            len = 0;
        }
        out.write(String.valueOf(len) + "\r\n");
        out.flush();
    }

    public void processDelete(String[] str) {
        String fileName = this.root + str[1];
        File file = new File(fileName);
        if(file.exists()) {
            deleteFile(file);
        }
    }

    private void processExists(String[] str) {
        String fileName = this.root + str[1];
        File file = new File(fileName);
        if (file.exists()) {
            out.write(Request.OK + "\r\n");
        } else {
            out.write(Request.NotOk + "\r\n");
        }
        out.flush();
    }


    public static void writeFile(String fileName, String content) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
        try (OutputStream out = new FileOutputStream(fileName, false)) {
            out.write(content.getBytes());
            out.flush();
        }
    }

    public static void deleteFile(File file) {
        if (!file.isFile()) {
            File[] list = file.listFiles();
            for (File f : list) {
                deleteFile(f);
            }
        }
        file.delete();
    }
}
