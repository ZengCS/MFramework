package com.zcs.mframework.utils;

import android.util.Log;

public class LogUtils {

	private static final int level = 6;

	public static void e(String tag, String msg) {
		if (level > 1)
			Log.e(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (level > 2)
			Log.w(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (level > 3)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (level > 4)
			Log.d(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (level > 5)
			Log.v(tag, msg);
	}
}
