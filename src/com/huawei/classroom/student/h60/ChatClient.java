package com.huawei.classroom.student.h60;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

	private boolean isLogged;
	private PrintWriter out;
	private BufferedReader in;

	 /**
	  * 根据情况适当抛出异常 
	  * @param ip
	  * @param port
	  */
	public ChatClient (String ip, int port) {
		try {
			Socket socket = new Socket(ip, port);
			out = new java.io.PrintWriter(socket.getOutputStream());
			// 获得输入流
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.isLogged = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 登录,成功返回true，否则返回false，根据情况适当抛出异常 
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean login(String userName,String password) {
		String line = userName + "\t" + password;
		out.write(line + "\r\n");
		out.flush();
		try {
			String res = in.readLine();
			if(res.equals("pass")) {
				this.isLogged = true;
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 退出，根据情况适当抛出异常 
	 */
	public void logout() {
		this.isLogged = false;
	}
	/**
	 * 发言, 只有登录以后才能发言， 根据情况适当抛出异常 
	 * 如果没有登录 抛出异常
	 *  
	 * @param str
	 */
	public void speak(String str) {
		 if(!isLogged) {
			 throw new RuntimeException("Not Log In");
		 } else {
			 out.write(str + "\r\n");
			 out.flush();
		 }
	}
	/**
	 * 读取聊天室目前的发言，根据情况适当抛出异常 
	 * 只有登录以后才可以读到,否则返回null
	 * 得到聊天室里面所有的发言（包括自己的），如果此时没有发言则立刻返回null，否则每次调用read的时候按队的方式返回一个句话
	 */
	public String read() {
		if(!isLogged) {
			return null;
		} else {
			String res = null;
			try {
				res = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return res;
		}
	}
	
}
