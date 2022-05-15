/**
 * 
 */
package com.huawei.classroom.student.h56;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;

/**
 * @author Administrator
 *
 */
public class FileTool {
	
	private String orgStr;
	private String targetStr;
	 
	/**
	 * 将homeDir 目录下（包括子目录）所有的文本文件（扩展名为.txt，扩展名不是.txt的文件不要动，扩展名区分大小写) 文件中，orgStr替换为 targetStr
	 * 所有文本文件均为UTF-8编码
	 * 例如将某个目录中所有文本文件中的 南开大学 替换为 天津大学
	 * @param homeDir
	 * @param orgStr
	 * @param targetStr
	 */
	public void replaceTxtFileContent(String homeDir,String orgStr,String targetStr) {
		this.orgStr = orgStr;
		this.targetStr = targetStr;
		File file = new File(homeDir);
		ListFiles(file);
	}
	
	public static String readIn(File fsrc) {
		Reader reader = null;
		StringBuffer buf = new StringBuffer();
		try {
			reader = new FileReader(fsrc);
			char[] chars = new char[1024];
			int readed = reader.read(chars);
			// 从一个流里面读取内容的经典写法
			while (readed != -1) {
				buf.append(chars, 0, readed);
				readed = reader.read(chars);
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			//reader!=null的判断是否可以取消，为什么？
			close(reader);
		}
		return buf.toString();
	}
	
	public static void close(Closeable inout) {
		if(inout != null) {
			try {
				inout.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} 
	}
	
	public static void readOut(File file, String content) {
		try (OutputStream out = new FileOutputStream(file, false)) {
			out.write(content.getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ListFiles(File dir) {
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}

		String[] files = dir.list();
		for (int i = 0; i < files.length; i++) {
			File file = new File(dir, files[i]);
			if (file.isFile()) {
				String fileName = file.getName();
				if(fileName.substring(fileName.length() - 3).equals("txt")) {
					String text = readIn(file);
					text = text.replaceAll(this.orgStr, this.targetStr);
					readOut(file, text);
				}
			} else {
				ListFiles(file); // 对于子目录,进行递归调用
			}
		}
	}

}
