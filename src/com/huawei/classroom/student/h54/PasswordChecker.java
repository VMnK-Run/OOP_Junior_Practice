/**
 * 
 */
package com.huawei.classroom.student.h54;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 *
 */
public class PasswordChecker {
	/**
	 * 判断一个口令是否是一个复杂度合法的口令，复杂度合法的口令有如下要求：
	 * 1  长度>=8
	 * 2 最少包含一个数字
	 * 3 最少包含一个小写英文字母
	 * 4 虽少包含一个大写英文字母
	 * 5 最少包含一个特殊字符 特殊字符定义为   ~!@#$%^&*()_+
	 * 
	 *   
	 */
	private final Set<Character> specialChar = new HashSet<>();
	private final String specialSeq = "~!@#$%^&*()_+";
	
	public PasswordChecker() {
		for(int i = 0; i < specialSeq.length(); i++) {
			specialChar.add(specialSeq.charAt(i));
		}
	}
	
	public boolean isValidPassword(String password){
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;
		if(password.length() >= 8) {
			flag1 = true;
		}
		for(int i = 0; i < password.length(); i++) {
			char ch = password.charAt(i);
			if(ch >= '0' && ch <= '9') flag2 = true;
			if(ch >= 'a' && ch <= 'z') flag3 = true;
			if(ch >= 'A' && ch <= 'Z') flag4 = true;
			if(specialChar.contains(ch)) flag5 = true;
		}
		return flag1 && flag2 && flag3 && flag4 && flag5;
		
	}
}
