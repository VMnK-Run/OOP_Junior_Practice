package com.huawei.classroom.student.h62;

import java.io.*;
import java.net.Socket;

public class MyHost {
    private String ip;
    private int port;
    private String username;
    private String password;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isLogIn;

    public MyHost() {
        this.socket = null;
        this.out = null;
        this.in = null;
        this.isLogIn = false;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void logIn() {
        try {
            this.socket = new Socket(this.ip, this.port);
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = Request.Login + "\t" + this.username + "\t"+this.password + "\t" + Request.End;
            out.write(line + "\r\n");
            out.flush();
            String res = in.readLine();
            this.isLogIn = res.equals(Request.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLogIn() {
        return this.isLogIn;
    }

    public MyRemoteFile[] allFilesByNameAsc(String path) throws IOException{
        out.write(Request.getAllFiles + "\t" + path + "\r\n");
        out.flush();
        int cnt = 0;
        String res = in.readLine();
        cnt = Integer.parseInt(res);
        MyRemoteFile[] files = new MyRemoteFile[cnt];
        for(int i = 0; i < cnt; i++) {
            res = in.readLine();
            files[i] = new MyRemoteFile(this, path + res);
        }
        return files;
    }

    public void writeByBytes(byte[] bytes, String path) {
        out.write(Request.writeBytes + "\t" + path + "\r\n");
        out.flush();
        try {
            OutputStream outer = socket.getOutputStream();
            outer.write(bytes);
            outer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLength(String path) {
        out.write(Request.getLength + "\t" + path + "\r\n");
        out.flush();
        String res = null;
        try {
            res = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(res);
    }

    public void delete(String path) {
        // 这个只需要发送指令即可
        out.write(Request.delete + "\t" + path + "\r\n");
        out.flush();
    }

    public boolean exists(String path) {
        out.write(Request.exists + "\t" + path + "\r\n");
        out.flush();
        String res = null;
        try {
            res = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(res.equals(Request.OK)) {
            return true;
        } else {
            return false;
        }
    }
}
