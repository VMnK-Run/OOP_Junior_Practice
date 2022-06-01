package com.huawei.classroom.student.h60;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ReceiveThread extends Thread{
    private PrintWriter out;
    private BufferedReader in;
    public ReceiveThread(Socket socket) {
        try {
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String line;
        boolean logIn;
        try {
            do {
                line = in.readLine();
                String[] str = line.split("\t");
                String name = str[0];
                String passwd = str[1];
                if(ChatServer.passwords.get(name).equals(passwd)) {
                    out.write("pass" + "\r\n");
                    out.flush();
                    logIn = true;
                } else {
                    out.write("No" + "\r\n");
                    out.flush();
                    logIn = false;
                }
            } while (!logIn);

            while (true) {
                line = in.readLine();

                // 这个题目里聊天室的要求即为：每当读到一句新的话，需要把这句话发送给所有的客户端
                for(Socket client : ChatServer.clientList) {
                    PrintWriter out = new PrintWriter(client.getOutputStream());
                    out.write(line + "\r\n");
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
