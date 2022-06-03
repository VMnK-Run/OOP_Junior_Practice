package com.huawei.classroom.student.h62;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class MyDeamonConfigVo {
    private String root;
    private int port;
    private Map<String, String> passwords;

    public MyDeamonConfigVo() {

    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getRoot() {
        return this.root;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public void setPasswordFile(String fileName) {
        this.passwords = readLines(fileName);
    }

    public Map<String, String> getPasswords() {
        return this.passwords;
    }

    public Map<String, String> readLines(String fileName) {
        Map<String, String> res = new HashMap<String, String>();
        try(Reader reader = new FileReader(fileName);
            LineNumberReader lineReader = new LineNumberReader(reader);) {
            String line = lineReader.readLine();
            while (line != null) {
                if(line.trim().length() != 0 && line.trim().charAt(0) != '#') {
                    String[] str = line.split("\t");
                    String user = str[0];
                    String password = str[1];
                    res.put(user, password);
                }
                line = lineReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

}
