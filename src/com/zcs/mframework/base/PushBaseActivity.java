package com.zcs.mframework.base;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

import com.zcs.mframework.FWApplication;
import com.zcs.mframework.R;
import com.zcs.mframework.push.ExampleUtil;
import com.zcs.mframework.utils.GlobalConstant;

public abstract class PushBaseActivity extends InstrumentedActivity implements OnClickListener {
	public String TAG = this.getClass().getSimpleName();

	protected TextView titleBarTxt;
	protected TextView exitBtn, backBtn;// 左侧按钮
	protected TextView configBtn, saveBtn;// 右侧按钮
	protected Dialog msgDialog, progressDialog, selectorDialog, inputDialog;
	protected Context currActivity;
	protected Resources res;
	private static Toast mToast;

	// JPush Server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

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
		saveBtn = (TextView) findViewById(R.id.title_btn_save);
	}

	/**
	 * 初始化组件
	 */
	public abstract void initComponent();

	protected void showToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}

	/**
	 * 初始化服务
	 */
	protected void initPush() {
		JPushInterface.init(this);
	}

	/**
	 * 停止服务
	 */
	protected void stopPush() {
		JPushInterface.stopPush(this);
	}

	/**
	 * 重启服务
	 */
	protected void resumePush() {
		JPushInterface.resumePush(this);
	}

	/**
	 * 注册消息接收服务
	 */
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				if (!ExampleUtil.isEmpty(extras)) {
					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
				}
				setCostomMsg(showMsg.toString());
			}
		}
	}

	private void setCostomMsg(String msg) {
		System.out.println("msg:" + msg);
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
		if (cls == null) {
			showToast("目标Activity为空！");
			return;
		}

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
