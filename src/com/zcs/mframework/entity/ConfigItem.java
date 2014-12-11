package com.zcs.mframework.entity;

import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;

/**
 * 系统设置选项对象
 * 
 * @author ZengCS
 * @since 2014年7月31日
 */
public class ConfigItem {
	private String name;// 设置名称
	private String val;// 当前值
	private String key;// 对应配置中的key
	private Drawable drawable;
	private boolean available = true;// 是否可用
	private OnClickListener onClickListener;

	public ConfigItem() {
	}

	public ConfigItem(String name, String val, String key, boolean available) {
		super();
		this.name = name;
		this.val = val;
		this.key = key;
		this.available = available;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public OnClickListener getOnClickListener() {
		return onClickListener;
	}

	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
}
