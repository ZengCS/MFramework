package com.zcs.mframework.sqlite;

import java.util.HashMap;
import java.util.Map;

public class CountryGlobal {
	public static final String[] FIFA2014Brazil = { "A组-巴西", "A组-克罗地亚", "A组-墨西哥", "A组-喀麦隆", "B组-西班牙", "B组-荷兰", "B组-智利", "B组-澳大利亚", "C组-哥伦比亚", "C组-希腊", "C组-科特迪瓦", "C组-日本", "D组-乌拉圭", "D组-哥斯达黎加",
			"D组-英格兰", "D组-意大利", "E组-瑞士", "E组-厄瓜多尔", "E组-法国", "E组-洪都拉斯", "F组-阿根廷", "F组-波黑", "F组-伊朗", "F组-尼日利亚", "G组-德国", "G组-葡萄牙", "G组-加纳", "G组-美国", "H组-比利时", "H组-阿尔及利亚", "H组-俄罗斯", "H组-韩国" };

	// 国家Map
	public static final Map<String, String> COUNTRY_MAP = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("argentina", "阿根廷");
			put("australia", "澳大利亚");
			put("austria", "奥地利");
			put("belgium", "比利时");
			put("canada", "加拿大");
			put("china", "中国");
			put("denmark", "丹麦");
			put("france", "法国");
			put("germany", "德国");
			put("hk", "中国香港");
			put("india", "印度");
			put("ireland", "爱尔兰");
			put("italy", "意大利");
			put("jamaica", "牙买加");
		}
	};
}
