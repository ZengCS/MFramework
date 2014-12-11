package com.zcs.mframework.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.zcs.mframework.R;
import com.zcs.mframework.utils.GlobalConstant;

public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener {
	public String TAG = this.getClass().getSimpleName();

	protected TextView titleBarTxt;
	protected TextView exitBtn, backBtn, configBtn;// 左侧按钮
	protected TextView saveBtn, startBtn;// 右侧按钮
	protected Dialog msgDialog, progressDialog;
	protected Context currActivity;
	protected Resources res;

	/**
	 * 初始化公共组件
	 */
	public void initCommonComponent() {
		backBtn = (TextView) findViewById(R.id.title_btn_back);
		exitBtn = (TextView) findViewById(R.id.title_btn_exit);
		configBtn = (TextView) findViewById(R.id.title_btn_config);
		titleBarTxt = (TextView) findViewById(R.id.title_bar_title);
		saveBtn = (TextView) findViewById(R.id.title_btn_save);
		startBtn = (TextView) findViewById(R.id.title_btn_start);
	}

	/**
	 * 初始化组件
	 */
	public abstract void initComponent();

	private static Toast mToast;
	private static Context context;

	protected void showToast(String text) {
		context = context == null ? getApplicationContext() : context;

		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	/**
	 * 切换Activity
	 * 
	 * @param cls
	 *            目标Activity.class
	 * @param anim
	 *            动画参数
	 */
	protected void changeActivity(Class<?> cls, int... anim) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);
		if (anim.length == 2) {
			try {
				overridePendingTransition(anim[0], anim[1]);
			} catch (Exception e) {
				overridePendingTransition(GlobalConstant.ANIM_L_IN, GlobalConstant.ANIM_L_OUT);
			}
		} else {
			overridePendingTransition(GlobalConstant.ANIM_L_IN, GlobalConstant.ANIM_L_OUT);
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 带动画返回
	 * 
	 * @param anim
	 *            动画参数
	 */
	protected void backWithAnimate(int... anim) {
		finish();
		if (anim.length == 2) {
			try {
				overridePendingTransition(anim[0], anim[1]);
			} catch (Exception e) {
				overridePendingTransition(GlobalConstant.ANIM_R_IN, GlobalConstant.ANIM_R_OUT);
			}
		} else {
			overridePendingTransition(GlobalConstant.ANIM_R_IN, GlobalConstant.ANIM_R_OUT);
		}
	}
}
