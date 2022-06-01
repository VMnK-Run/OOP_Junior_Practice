package com.huawei.classroom.student.h58;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.zone.ZoneRulesException;
import java.util.*;

public class VoteRecord {
	
	public List<String[]> voteDetail;
	public final String foramString = "yyyy-MM-dd HH:mm:ss";
	
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
		Voters voters = new Voters();
		Map<String, Integer> map = new HashMap<String, Integer>();
		DateFormat fmt = new SimpleDateFormat(foramString);
		this.voteDetail = getVoteDetail(fileName);
		for(int i = 0; i < voteDetail.size(); i++) {
			String date = voteDetail.get(i)[0];
			Date tDate = null;
			try {
				tDate = fmt.parse(date);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			long time = tDate.getTime();
			String user = voteDetail.get(i)[1];
			String name = voteDetail.get(i)[2];
			Record record = new Record(time, user);
			voters.add(record);
			if(!voters.isVaild(record)) {
				continue;
			}
			if(!map.containsKey(name)) {
				map.put(name, 1);
			} else {
				map.put(name, map.get(name) + 1);
			}
		}
		return map;
	}
	
	
	public List<String[]> getVoteDetail(String fileName) {
		List<String[]> res = new ArrayList<String[]>();
		try (Reader reader = new FileReader(fileName);
				LineNumberReader lineReader = new LineNumberReader(reader)){
			String line = lineReader.readLine();
			while(line != null) {
				res.add(line.split("\t"));
				line = lineReader.readLine();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
