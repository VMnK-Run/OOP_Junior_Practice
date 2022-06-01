package com.huawei.classroom.student.h58;

import java.util.*;

public class Voters {
	public Map<String, List<Long>> map;
	
	public Voters() {
		map = new TreeMap<String, List<Long>>();
	}
	
	public void add(Record record) {
		long time = record.getTime();
		String user = record.getName();
		
		if(!map.containsKey(user)) {
			List<Long> list = new ArrayList<Long>();
			list.add(time);
			map.put(user, list);
		} else {
			List<Long> list = map.get(user);
			list.add(time);
		}
		List<Long> list = map.get(user);
		int len = list.size();
		boolean condition1 = true;
		boolean condition2 = true;
		int cnt1min = 0;
		int cnt10min = 0;
		for(int i = len - 1; i >= 0; i--) {
			long t = list.get(i);
			if(Math.abs(time - t) < 1 * 60 * 1000) {
				cnt1min++;
			}
			if(Math.abs(time - t) < 10 * 60 * 1000) {
				cnt10min++;
			}
			if(cnt1min > 1) {
				condition1 = false;
				list.remove(len - 1);
				break;
			}
			if(cnt10min > 5) {
				condition2 = false;
				list.remove(len - 1);
				break;
			}
		}
		if(condition1 && condition2) {
			//System.out.println(record.name + " " + record.time);
			record.setVaild(true);
		} else {
//			System.out.println(record.name + " " + record.time);
			record.setVaild(false);
		}
	}
	
	public boolean isVaild(Record record) {
		if(record.vaild) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void print() {
		System.out.println(map.get("wxid_3").size());
	}
	
}
