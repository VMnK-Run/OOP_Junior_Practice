package com.huawei.classroom.student.h53;

public class Rabbit {
	public int age;
	public boolean isAdult;
	public boolean isDead;
	public int interval; // 两次生育的间隔
	
	public Rabbit() {
		this.age = 0;
		this.interval = 89;
		this.isAdult = false;
		this.isDead = false;
	}
	
	public void grow() {
		this.age++;
		if(this.age >= 180) {
			this.isAdult = true;
		}
		if(this.age >= 700) {
			this.isDead = true;
		}
		if(this.isAdult) {
			this.interval++;
		}
	}
	
	public int getInterval() {
		return this.interval;
	}
	
	public void resetInterval() {
		this.interval = 0;
	}
}
