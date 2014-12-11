package com.zcs.mframework.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.listener.GestureListener;
import com.zcs.mframework.utils.TextUtils;
import com.zcs.mframework.widget.CircleProgressBar;
import com.zcs.mframework.widget.MyProgressBar;

public class CircleProgressActivity extends BaseActivity {
	private static final String CURR_TITLE = "CircleProgressDemo";// 标题
	private static final String CURR_HIGH_WORD = "CircleProgress";// 标题高亮词

	private CircleProgressBar mCircleProgressBar1, mCircleProgressBar2;
	private MyProgressBar progressBar;
	private int progress = 0;
	private boolean isProgress = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_circle_progress);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// 需要监听左右滑动事件的view
		LinearLayout view = (LinearLayout) this.findViewById(R.id.circleLayout);

		// setLongClickable是必须的
		view.setLongClickable(true);
		view.setOnTouchListener(new MyGestureListener(this));

		// TODO 初始化组件
		initComponent();
	}

	/**
	 * 继承GestureListener，重写left和right方法
	 */
	private class MyGestureListener extends GestureListener {
		public MyGestureListener(Context context) {
			super(context);
		}

		@Override
		public boolean left() {
			showToast("向左滑");
			return super.left();
		}

		@Override
		public boolean right() {
			showToast("向右滑");
			return super.right();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_btn_back:
			// TODO 执行返回操作
			backWithAnimate();
			break;
		case R.id.title_btn_start:
			// showDefaultNotification();
			if (isProgress) {
				return;
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					isProgress = true;
					while (progress <= 100) {
						progress += 2;

						mCircleProgressBar1.setProgress(progress);
						mCircleProgressBar2.setProgress(progress);
						progressBar.setProgress(progress);

						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					progress = 0;
					isProgress = false;
				}
			}).start();
			break;
		default:
			break;
		}
	}

	// 处理后退键的情况
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			backWithAnimate();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void initComponent() {
		super.initCommonComponent();
		// TODO 初始化标题
		titleBarTxt.setText(CURR_TITLE);
		backBtn.setVisibility(View.VISIBLE);

		startBtn.setVisibility(View.VISIBLE);

		// TODO 设置标题中CURR_HIGH_WORD为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 按钮事件绑定
		backBtn.setOnClickListener(this);
		startBtn.setOnClickListener(this);

		mCircleProgressBar1 = (CircleProgressBar) findViewById(R.id.circleProgressBar1);
		mCircleProgressBar2 = (CircleProgressBar) findViewById(R.id.circleProgressBar2);
		progressBar = (MyProgressBar) findViewById(R.id.myProgressBar);
	}

	// 默认显示的的Notification
	private void showDefaultNotification() {
		// 定义Notication的各种属性
		CharSequence title = "i am new";
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		Notification noti = new Notification(icon, title, when + 10000);
		noti.flags = Notification.FLAG_INSISTENT;

		// 创建一个通知
		Notification mNotification = new Notification();

		// 设置属性值
		mNotification.icon = R.drawable.ic_launcher;
		mNotification.tickerText = "NotificationTest";
		mNotification.when = System.currentTimeMillis(); // 立即发生此通知

		// 带参数的构造函数,属性值如上
		// Notification mNotification = = new
		// Notification(R.drawable.icon,"NotificationTest",
		// System.currentTimeMillis()));

		// 添加声音效果
		mNotification.defaults |= Notification.DEFAULT_SOUND;

		// 添加震动,后来得知需要添加震动权限 : Virbate Permission
		// mNotification.defaults |= Notification.DEFAULT_VIBRATE ;

		// 添加状态标志

		// FLAG_AUTO_CANCEL 该通知能被状态栏的清除按钮给清除掉
		// FLAG_NO_CLEAR 该通知能被状态栏的清除按钮给清除掉
		// FLAG_ONGOING_EVENT 通知放置在正在运行
		// FLAG_INSISTENT 通知的音乐效果一直播放
		// mNotification.flags = Notification.FLAG_INSISTENT;

		// 将该通知显示为默认View
		Intent targetIntent = new Intent(currActivity, SplashActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(CircleProgressActivity.this, 0, targetIntent, 0);
		mNotification.setLatestEventInfo(CircleProgressActivity.this, "通知类型：默认View", "一般般哟。。。。", contentIntent);

		// 设置setLatestEventInfo方法,如果不设置会App报错异常
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// 注册此通知
		// 如果该NOTIFICATION_ID的通知已存在，会显示最新通知的相关信息 ，比如tickerText 等
		mNotificationManager.notify(2, mNotification);
	}

	private void removeNotification() {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 取消的只是当前Context的Notification
		mNotificationManager.cancel(2);
	}
}
