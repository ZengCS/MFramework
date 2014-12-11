package com.zcs.mframework.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

/**
 * 有关文本的一些工具类
 * 
 * @author ZengCS 2014年5月19日17:16:04
 */
public class TextUtils {
	/**
	 * 对指定位置的字符进行高亮显示
	 * 
	 * @param src
	 *            源字符串
	 * @param start
	 *            高亮起点
	 * @param end
	 *            高亮终点
	 * @param color
	 *            颜色
	 * @return
	 */
	public static SpannableString highlight(CharSequence src, int start, int end, int color) {
		SpannableString spannable = new SpannableString(src);
		CharacterStyle span = new ForegroundColorSpan(color);
		spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}

	/**
	 * 高亮指定字符串
	 * 
	 * @param src
	 *            源字符串
	 * @param target
	 *            需要高亮的字符串
	 * @param color
	 *            高亮颜色
	 * @return
	 */
	public static SpannableString highlight(CharSequence src, String target, int color) {
		int idx = src.toString().indexOf(target);
		int start = 0;
		int end = 0;
		if (idx > -1) {
			start = idx;
			end = idx + target.length();
		}
		return highlight(src, start, end, color);
	}
}
