package com.huawei.classroom.student.h57;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//递归统计一个目录下所有文件的大小的加和
		FileTool  tool=new FileTool ();
		//此题目要求精确匹配答案
		long length=tool.recursiveCalcFileSize("D:\\北小洋\\21222\\程序设计初级实践\\OOP_Junior_Practice\\src\\com\\huawei\\classroom\\student\\h57\\test_dir\\");
		if(length==132) {
			System.out.println("pass ");
		} else {
			System.out.println("傻逼");
		}
		System.out.println(length);
	}

}
