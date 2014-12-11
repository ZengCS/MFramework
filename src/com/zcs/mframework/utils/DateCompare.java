package com.zcs.mframework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class DateCompare {
	private static String dateStr = "2014-06-18 18:07:47";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;

	public static void main(String[] args) {
		try {
			Date lastDate = sdf.parse(dateStr);
			Date nowDate = new Date();
			System.out.println("上次:" + dateStr);
			System.out.println("现在:" + sdf.format(nowDate));
			String msg = compare(lastDate, nowDate);
			System.out.println("最后更新：" + msg);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static String compare(Date minDate, Date bigDate) {
		long differ = bigDate.getTime() - minDate.getTime();
		differ = (differ < 0 ? -differ : differ) / 1000;// 转换成秒

		if (differ < 60) {
			return differ + "秒前";
		} else {
			differ /= 60;// 转换成分钟
		}

		if (differ < 60) {
			return differ + "分钟前";
		} else {
			differ /= 60;// 转换成小时
		}

		if (differ < 24) {
			return differ + "小时前";
		} else {
			differ /= 24;// 转换成天
		}

		if (differ < 31) {
			return differ + "天前";
		} else {
			differ /= 30;// 转换成月
		}

		if (differ < 12) {
			return differ + "月前";
		} else {
			differ /= 12;// 转换成年
			return differ + "年前";
		}
	}
}
