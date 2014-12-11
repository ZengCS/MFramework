package com.zcs.mframework.utils;

import java.lang.reflect.Field;

public class MFCommons {
	/**
	 * 根据资源名称获取资源ID
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static int getDrawableIdByName(String name) throws Exception {
		Field field = Class.forName("com.zcs.mframework.R$drawable").getField(name);
		return field.getInt(field);
	}
}
