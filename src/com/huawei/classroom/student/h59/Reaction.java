package com.huawei.classroom.student.h59;

import java.util.*;

public class Reaction {
	public Set<String> reactant;
	public Set<String> product;
	
	public Reaction(Set<String> reactant, Set<String> product) {
		this.reactant = reactant;
		this.product = product;
	}
	
	public Set<String> getReactant() {
		return this.reactant;
	}
	
	public Set<String> getProduct() {
		return this.product;
	}
}
