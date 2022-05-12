package com.huawei.classroom.student.h58;

import java.util.Map;

public class VoteRecord {
	/**
	 * fileName是一个投票的明细记录，里面逐行存放了 投票的时间（yyyy-MM-dd HH:mm:ss 格式） +\t+投票的微信ID+\t+候选人
	 * 存放按时间递增（但是可能出现同一秒出现若干条记录的情况）
	 * 现在需要完成投票统计的过程，具体要求如下：
	 * 1个微信ID 1分钟内 最多投1票 多余的票数无效
	 * 1个微信ID 10分钟内 最多只能投5票 多余的票无效
	 * 其中微信ID不固定，候选人姓名不固定
	 * 测试的时候要求10万行记录处理时间不超过3秒 
	 * @param fileName
	 * @return 返回一个map，其中key是候选人名字，value的票数
	 */
	public Map<String,Integer> calcRecording(String fileName){
		return null;
	}

}
