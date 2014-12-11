package com.zcs.mframework.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zcs.mframework.FWApplication;
import com.zcs.mframework.R;
import com.zcs.mframework.activities.PushReceiveActivity;
import com.zcs.mframework.activities.SplashActivity;
import com.zcs.mframework.utils.GlobalConstant;

public abstract class BaseActivity extends Activity implements OnClickListener {
	public String TAG = this.getClass().getSimpleName();
	public static final long ANIMATION_DURATION = 280l;

	protected TextView titleBarTxt, backBtn, exitBtn, configBtn;
	protected TextView saveBtn, startBtn, bgBtn;// 右侧按钮
	protected Dialog msgDialog, progressDialog, selectorDialog, inputDialog;
	protected Context currActivity;
	protected Resources res;
	protected LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FWApplication.getInstance().addActivity(this);
	}

	/**
	 * 初始化公共组件
	 */
	public void initCommonComponent() {
		backBtn = (TextView) findViewById(R.id.title_btn_back);
		exitBtn = (TextView) findViewById(R.id.title_btn_exit);
		configBtn = (TextView) findViewById(R.id.title_btn_config);
		titleBarTxt = (TextView) findViewById(R.id.title_bar_title);
		startBtn = (TextView) findViewById(R.id.title_btn_start);
		saveBtn = (TextView) findViewById(R.id.title_btn_save);
		bgBtn = (TextView) findViewById(R.id.title_btn_background);
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
		if (anim != null && anim.length == 2) {
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
		// TODO 如果当前是推送消息查看,点击返回后重新打开程序
		if (this instanceof PushReceiveActivity) {
			Intent i = new Intent(this, SplashActivity.class);
			startActivity(i);
		}
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

	/**
	 * 隐藏键盘
	 * 
	 * @param v
	 */
	protected void hideSoftInputFromWindow(View v) {
		try {
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getScreenHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	public int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public int[] getScreenSize() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int[] size = new int[2];
		size[0] = dm.widthPixels;
		size[1] = dm.heightPixels;
		return size;
	}
}
