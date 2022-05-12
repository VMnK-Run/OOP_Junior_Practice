package com.huawei.classroom.student.h52;

import java.util.HashSet;
import java.util.Set;

public class NumDecompose {
	/**
	 * 将num进行质因数分解，将分解到的质因数放到Set里面返回
	 */
	public Set<Integer> decompose(int num) {
		Set<Integer> res = new HashSet<>();
		if(num <= 0) {
			return null;
		} else if (num == 1) {
			res.add(1);
		} else {
			for(int i = 2; i <= num; i++) {
				if(num % i == 0) { // i是因数
					res.add(i);
					num /= i;
					i--; //回退到上一个数，保证一直是在分解为质数
				}

			}
		}
		
		return res;
		
	}
}
