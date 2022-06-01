package com.huawei.classroom.student.h59;

import java.util.*;
import java.io.*;

public class ReactionTools {

	/**
	 * 根据reactionFile给出的一系列反应， 判断一个体系中根据init物质，判断出最后可能都存在什么物质
	 * @param reactionFile 体系中初始反应物
	 * @param initComponents 体系中初始反应物
	 * @return 最后体系中存在的全部物质
	 */
	public Set<String> findAllComponents(String reactionFile,Set<String> initComponents){
		Set<String> res = new HashSet<>(initComponents);
		List<Reaction> list = readIn(reactionFile);
		int lastSize;
		do {
			lastSize = res.size();
			for (Reaction reaction : list) { // 依次看每个反应是否能发生
				Set<String> reactants = reaction.getReactant();
				if(res.containsAll(reactants)) {
					res.addAll(reaction.getProduct());
				}
			}
		} while(res.size() != lastSize); // 直到没有新的产物产生

		return res;
	}
	
	public List<Reaction> readIn(String fileName) {
		List<Reaction> list = new ArrayList<Reaction>();
		try(Reader reader = new FileReader(fileName);
				LineNumberReader lineReader = new LineNumberReader(reader)) {
			String line = lineReader.readLine();
			while(line != null) {
				if(line.trim().length() != 0 && line.trim().charAt(0) != '#') {
					String[] str = line.split("=");
					String left = str[0];
					String right = str[1];
					String[] lefts = left.split(" \\+ ");
					String[] rights = right.split(" \\+ "); // 正则表达式
					Set<String> leftSet = new HashSet<String>();
					Set<String> rightSet = new HashSet<String>();
					for(String s : lefts) {
						leftSet.add(s.trim());
					}
					for(String s : rights) {
						rightSet.add(s.trim());
					}
					Reaction reaction1 = new Reaction(leftSet, rightSet);
					Reaction reaction2 = new Reaction(rightSet, leftSet);
					list.add(reaction1);
					list.add(reaction2);
				}
				
				line = lineReader.readLine();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
}

