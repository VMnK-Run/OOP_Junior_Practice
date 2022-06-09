package com.huawei.classroom.student.h60;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ChatServer {

	private final int port;
	public static Map<String, String> passwords = new HashMap<>();
	public static List<Socket> clientList = new ArrayList<>();
	/**
	 * 初始化 ， 根据情况适当抛出异常
	 * @param port
	 * @param passwordFilename 所有用户的用户名 口令
	 */

	public ChatServer (int port, String passwordFilename) {
	 	this.port = port;
		passwords = readIn(passwordFilename);
	}
	/**
	 *  根据情况适当抛出异常
	 * 开始监听
	 */
	public void startListen( ) {
		ServerThread thread = new ServerThread(this.port);
		thread.start();
	}

	public Map<String, String> readIn(String fileName) {
		Map<String, String> res = new HashMap<>();
		try(Reader reader = new FileReader(fileName);
			LineNumberReader lineReader = new LineNumberReader(reader)) {
			String line = lineReader.readLine();
			while(line != null) {
				String[] combine = line.split("\t");
				String name = combine[0];
				String passwd = combine[1];
				res.put(name, passwd);
				line = lineReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	 
}
