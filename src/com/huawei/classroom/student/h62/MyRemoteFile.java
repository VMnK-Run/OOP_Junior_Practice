package com.huawei.classroom.student.h62;

import java.io.IOException;

public class MyRemoteFile {
    private String path;
    private MyHost host;
    public MyRemoteFile(MyHost host, String path) {
        this.host = host;
        this.path = path;
        if(!host.isLogIn()) {
            this.host.logIn();
        }
        if(!this.host.isLogIn()) {
            throw new RuntimeException("host can't connect");
        }
    }

    public MyRemoteFile[] dirByNameAsc() {
        if(!this.host.isLogIn()) {
            return null;
        }
        MyRemoteFile[] res = new MyRemoteFile[0];
        try {
            res = host.allFilesByNameAsc(this.path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getPathFileName() {
        return this.path;
    }

    public boolean isFile() {
        return !this.path.endsWith("/");
    }

    public boolean isDirectory() {
        return this.path.endsWith("/");
    }

    public void writeByBytes(byte[] bytes) {
        this.host.writeByBytes(bytes, this.path);
    }

    public int length() {
        return this.host.getLength(this.path);
    }

    public void delete() {
        this.host.delete(this.path);
    }

    public boolean exists() {
        return this.host.exists(this.path);
    }
}
