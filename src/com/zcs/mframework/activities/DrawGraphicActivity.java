package com.zcs.mframework.activities;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.utils.TextUtils;
import com.zcs.mframework.widget.MyProgressBar;

public class DrawGraphicActivity extends BaseActivity {
	private static final String CURR_TITLE = "画安卓机器人";// 标题
	private static final String CURR_HIGH_WORD = "机器人";// 标题高亮词

	private MyProgressBar progressBar;
	private int progress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_draw_graphic);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();
		// playSound();
		startProgress();
	}

	private void startProgress() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (progress <= 100) {
					progress++;
					progressBar.setProgress(progress);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				progress = 0;
			}
		}).start();
	}

	private void playSound() {
		// MediaPlayer.create(this, R.raw.office).start();
		MediaPlayer.create(this, R.raw.beep).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_btn_back:
			// TODO 执行返回操作
			backWithAnimate();
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

		// TODO 设置标题中CURR_HIGH_WORD为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 按钮事件绑定
		backBtn.setOnClickListener(this);
		progressBar = (MyProgressBar) findViewById(R.id.myProgressBar);
	}

}
