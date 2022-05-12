package com.huawei.classroom.student.h51;

public class HongBao {

	/**
	 * 
	 * @param total  红包总金额，以元为单位，精确到分，系统测试的时候保证总金额至少够每人分得1分钱
	 * @param personCount 分红包的总人数>0
	 * @return 每个人分得的钱数
	 * 规则遵循微信分红包规则 例如：
	 * 要求 每人分得的钱数总和=total
	 * 每个人分得钱数必须是正数，且不能少于1分
	 * 
	 */
	public double[] getHongbao(double total,int personCount) {
		if(total < personCount * 0.01) {
			return null;
		}
		double result[]=new double[personCount];
		double remainMoney = total;
		double remainPerson = personCount;
		for(int i = 0; i < personCount; i++) {
			remainPerson--;
			if(remainPerson == 0) {
				result[i] = remainMoney;
				break;
			}
			double min = 0.01;
			double max = remainMoney - remainPerson * 0.01;
			double money = min + Math.random() * (max - min);
			money = (double) Math.round(money * 100) / 100;
			result[i] = money;
			remainMoney -= money;
		}
		return result;
	}
}
