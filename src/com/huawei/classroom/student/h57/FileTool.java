package com.huawei.classroom.student.h57;

import java.io.*;

public class FileTool {
	
	public long totalSum;
	
	/*
	 * 统计一个目录下所有文件大小的加和
	 */
	public long recursiveCalcFileSize(String homeDir) {
		this.totalSum = 0;
		File file = new File(homeDir);
		ListFiles(file);
		return this.totalSum;
	}
	
	public void ListFiles(File dir) {
		if(!dir.exists() || !dir.isDirectory()) {
			return;
		}
		String[] files = dir.list();
		for(int i = 0; i < files.length; i++) {
			File file = new File(dir, files[i]);
			if(file.isFile()) {
				this.totalSum += file.length();
			} else {
				ListFiles(file);
			}
		}
	}
}
