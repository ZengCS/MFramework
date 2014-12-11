package com.zcs.mframework.commons;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.zcs.mframework.R;
import com.zcs.mframework.entity.ConfigItem;

public class SystemConfig {
	private String currVersion;
	private Resources res;
	private SharedPreferences sharedPreferences;

	public SystemConfig() {
	}

	public SystemConfig(Resources res, SharedPreferences sharedPreferences) {
		this.res = res;
		this.sharedPreferences = sharedPreferences;
	}

	/**
	 * 获取软件信息
	 * 
	 * @return
	 */
	public ArrayList<ConfigItem> getAppInfo() {
		ArrayList<ConfigItem> list = new ArrayList<ConfigItem>(0);

		ConfigItem c1 = new ConfigItem("本地缓存", "23.87Mb", "", true);
		c1.setDrawable(res.getDrawable(R.drawable.photos));

		ConfigItem c2 = new ConfigItem("电子邮件", getSystemConfig("input_cfg_user_email"), "input_cfg_user_email", true);
		c2.setDrawable(res.getDrawable(R.drawable.mail));

		ConfigItem c3 = new ConfigItem("我的生日", getSystemConfig("cfg_user_birthday"), "cfg_user_birthday", true);
		c3.setDrawable(res.getDrawable(R.drawable.calender));

		ConfigItem c4 = new ConfigItem("地址管理", getSystemConfig("input_cfg_user_address"), "input_cfg_user_address", true);
		c4.setDrawable(res.getDrawable(R.drawable.pin));

		ConfigItem c5 = new ConfigItem("我的标签", getSystemConfig("input_cfg_user_tags"), "input_cfg_user_tags", true);
		c5.setDrawable(res.getDrawable(R.drawable.tag));

		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);

		return list;
	}

	public String getCurrVersion() {
		return currVersion;
	}

	/**
	 * 获取系统信息
	 * 
	 * @return
	 */
	public ArrayList<ConfigItem> getSysInfo() {
		ArrayList<ConfigItem> list = new ArrayList<ConfigItem>(0);

		ConfigItem c1 = new ConfigItem("服务器IP", getSystemConfig("input_cfg_server_ip"), "input_cfg_server_ip", true);
		ConfigItem c2 = new ConfigItem("服务器端口", getSystemConfig("input_cfg_server_port"), "input_cfg_server_port", true);

		list.add(c1);
		list.add(c2);

		return list;
	}

	private String getSystemConfig(String key) {
		return sharedPreferences.getString(key, "未设置");
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public ArrayList<ConfigItem> getUserInfo() {
		ArrayList<ConfigItem> list = new ArrayList<ConfigItem>(0);

		ConfigItem userCfg = new ConfigItem("账号信息", getSystemConfig("userNameCache"), "userNameCache", true);
		userCfg.setDrawable(res.getDrawable(R.drawable.id));

		ConfigItem passCfg = new ConfigItem("修改密码", "******", "input_cfg_modify_password", true);
		passCfg.setDrawable(res.getDrawable(R.drawable.key));

		ConfigItem validateCfg = new ConfigItem("安全级别", getSystemConfig("select_cfg_validate_level"), "select_cfg_validate_level", true);
		validateCfg.setDrawable(res.getDrawable(R.drawable.padlock));

		ConfigItem versionCfg = new ConfigItem("版本更新", getCurrVersion(), "cfg_version_update", true);
		versionCfg.setDrawable(res.getDrawable(R.drawable.ic_launcher));

		ConfigItem helpCfg = new ConfigItem("帮助信息", "帮助信息", "cfg_help_info", true);
		helpCfg.setDrawable(res.getDrawable(R.drawable.books));

		ConfigItem aboutCfg = new ConfigItem("关于MFramework", "关于MFramework", "cfg_about_app", true);
		aboutCfg.setDrawable(res.getDrawable(R.drawable.info));

		list.add(userCfg);
		list.add(passCfg);
		list.add(validateCfg);
		list.add(versionCfg);
		list.add(helpCfg);
		list.add(aboutCfg);

		return list;
	}

	public void setCurrVersion(String currVersion) {
		this.currVersion = "Ver " + currVersion;
	}
}
