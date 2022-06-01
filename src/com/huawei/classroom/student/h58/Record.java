package com.huawei.classroom.student.h58;

public class Record {
	
	public long time;
	public String name;
	public boolean vaild;
	
	public Record(long time, String name) {
		this.time = time;
		this.name = name;
		this.vaild = true;
	}
	
	public void setVaild(boolean value) {
		this.vaild = value;
	}
	
	public long getTime() {
		return this.time;
	}
	
	public String getName() {
		return this.name;
	}
}
