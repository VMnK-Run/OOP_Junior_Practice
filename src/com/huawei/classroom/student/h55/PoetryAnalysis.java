package com.huawei.classroom.student.h55;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PoetryAnalysis {
	
	public PoetryAnalysis() {
		
	}

	/**
	 * 
	 * @param pathFilename 包含诗歌内容的源文件
	 * @param chars 需要统计的字 以半角分号分割 
	 * 统计  
	 * 
	 */
	public void analysis(String pathFilename,String chars) {
		System.out.println("词汇 词频");
		String[] content = readLines(pathFilename);
		Set<Character> set = new HashSet<Character>();
		String[] words = chars.split(";");
		for(int i = 0; i < words.length; i++) {
			String s = words[i];
			char ch = s.charAt(0);
			set.add(ch);
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(String str:content) {
			for(int i = 0; i < str.length() - 1; i++) {
				if(set.contains(str.charAt(i)) || set.contains(str.charAt(i + 1))) {
					String word = str.substring(i, i + 2);
					if(!map.containsKey(word)) {
						map.put(word, 1);
					} else {
						map.put(word, map.get(word) + 1);
					}
				}
			}
		}
		
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
		int size = Math.min(10, list.size());
		for(int i = 0; i < size; i++) { // 这里注意：不一定都是大于10个，有可能小于10个！
			String word = list.get(i).getKey();
			int times = list.get(i).getValue();
			System.out.println(word + " " + times);
		}
	}
	
	public String[] readLines(String fileName){
		String[] res = null;
		try(Reader reader = new FileReader(fileName);
				LineNumberReader lineReader = new LineNumberReader(reader)) {
			String line = lineReader.readLine();
			while(line != null) {
				res = line.split("[，。]");
				line = lineReader.readLine();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
