package com.huawei.classroom.student.h53;

import java.util.ArrayList;
import java.util.List;

public class RabbitNest {
	public int startCount;
	public List<Rabbit> rabbits;
	
	public RabbitNest(int startCount) {
		this.rabbits = new ArrayList<Rabbit>();
		this.startCount = startCount;
		for(int i = 0; i < startCount; i++) {
			Rabbit rabbit = new Rabbit();
			rabbits.add(rabbit);
		}
	}
	
	public void aDayPass() {
		for(int i = 0; i < rabbits.size(); i++) {
			Rabbit rabbit  =rabbits.get(i);
			rabbit.grow();
			if(rabbit.isDead) {
				rabbits.remove(rabbit);
				continue;
			} else if(rabbit.isAdult){
				if(rabbit.getInterval() == 90) {
					Rabbit rabbit1 = new Rabbit();
					Rabbit rabbit2 = new Rabbit();
					rabbits.add(rabbit1);
					rabbits.add(rabbit2);
					rabbit.resetInterval();
				}
			}
		}
	}
	
	public int getCount() {
		return this.rabbits.size();
	}
	
}
