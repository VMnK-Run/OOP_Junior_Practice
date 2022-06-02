package com.huawei.classroom.student.h61;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Simulate simulate=new Simulate();
		Param param=new Param();
		//测试的时候 days介于1-1000之间
		int days=100;
		//模拟某种疾病在一个城市的传播情况。所有参数见 Param。模拟结果放入SimResult中
		//不要修改Param,SimResult的代码
		//现在是第0天的白天的开始，模拟days天以后，城市居民的健康状况
		//完成SimResult 类，可以适当的增加其他的类。
		//要求算法适当的考虑算法效率问题
		long l=System.currentTimeMillis();
		SimResult result=simulate.sim(param,days);
		System.out.println((System.currentTimeMillis()-l)+"ms cost ");
		System.out.println(days+" 天后 情况如下");
		System.out.println("死亡人数："+result.getDeaths());
		System.out.println("自愈+治愈人数:"+result.getCured());
		System.out.println("现有病人数量:"+result.getPatients());
		System.out.println("现有潜伏期人数:"+result.getLatents());
		System.out.println("健康人数:"+(param.getCityPopulation()-result.getCured()- result.getDeaths()-result.getPatients()-result.getLatents()));

		SimResult h = new SimResult();
		// test case 1
		simulate = new Simulate();
		param = new Param();
		param.setDeathRateHome(0);
		param.setDeathRateHospital(0);
		h = simulate.sim(param, days);
		if(h.getDeaths() == 0) {
			System.out.println("Test 1 passed!");
		}


		// test case 2
		simulate = new Simulate();
		param = new Param();
		param.setSpreadRateCompany(0);
		param.setSpreadRateFamily(0);
		h = simulate.sim(param, days);
		if(h.getPatients() + h.getDeaths() + h.getCured() == 4) {
			System.out.println("Test 2 passed!");
		}


		// test case 3
		simulate = new Simulate();
		param = new Param();
		param.setImmuEffect(1);
		param.setImmuRate(1);
		h = simulate.sim(param, days);
		if(h.getPatients() + h.getDeaths() + h.getCured() == 4) {
			System.out.println("Test 3 passed!");
		}


		// test case 4
		simulate = new Simulate();
		param = new Param();
		param.setImmuEffect(0);
		h = simulate.sim(param, days);
		if(rangeEq(100000-26590, h.getPatients() + h.getDeaths() + h.getCured())) {
			System.out.println("Test 4 passed!");
		}


		simulate = new Simulate();
		param.setImmuEffect(0.8);
		param.setImmuRate(0.8);
		h = simulate.sim(param, days);
		if(rangeEq(98571, 100000-(h.getPatients() + h.getDeaths() + h.getCured()))) {
			System.out.println("Test 5 passed!");
		}

	}


	public static boolean rangeEq(int a, int b) {
		double range = 0.5;
		return (a * (1 - range)-500) < b && b < (a * (1 + range)+500);
	}
}
