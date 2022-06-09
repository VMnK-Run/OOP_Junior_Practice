package com.huawei.classroom.student.h60;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    private final int port;
    private ServerSocket serverSocket;
    public ServerThread(int port) {
        this.port = port;
        this.serverSocket = null;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            while(true) {
                Socket socket;
                socket = serverSocket.accept();
                // 这里需要另外开启线程接收每一个socket的消息
                ChatServer.clientList.add(socket);
                new ReceiveThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
