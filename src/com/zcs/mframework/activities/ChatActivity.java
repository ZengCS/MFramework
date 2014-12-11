package com.zcs.mframework.activities;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.utils.MFCommons;
import com.zcs.mframework.utils.TextUtils;

public class ChatActivity extends BaseActivity {
	private static final String CURR_TITLE = "微聊天";// 标题
	private static final String CURR_HIGH_WORD = "聊天";// 标题高亮词

	private ImageView chatBg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_chat);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();
		mInflater = LayoutInflater.from(this);

		// TODO 初始化组件
		initComponent();
		// initChat();
		initBgArr();
	}

	private static int[] chatBgs = new int[8];

	private void initBgArr() {
		chatBgs[0] = R.color.white;
		for (int i = 1; i <= 7; i++) {
			try {
				chatBgs[i] = MFCommons.getDrawableIdByName("repeat_bg_chat_" + i);
			} catch (Exception e) {
				chatBgs[i] = R.drawable.repeat_bg_main;
			}
		}
	}

	private void initChat() {
		int timeHour = 15;
		int timeMin = 48;
		Random ran = new Random();
		LinearLayout helpInfoLayout = (LinearLayout) findViewById(R.id.helpInfoLayout);

		String[] asks = { "我问你什么，你就回答我什么。", "我再问你什么，你就再回答我什么。", "我还问你什么，你就还回答我什么。", "我一直问你什么，你就一直回答我什么。", "我不停问你什么，你就不停回答我什么。" };
		String[] answers = { "你问我什么，我就回答你什么。", "你再问我什么，我就再回答你什么。", "你还问我什么，我就还回答你什么。", "你一直问我什么，我就一直回答你什么。", "你不停问我什么，我就不停回答你什么。" };
		int c = 0;
		for (String s : asks) {
			// TODO 设置左侧聊天信息
			View leftView = mInflater.inflate(R.layout.chat_left_row, null);
			TextView askTxt = (TextView) leftView.findViewById(R.id.chat_l_txt);
			TextView askTime = (TextView) leftView.findViewById(R.id.chat_time_l);
			timeMin += ran.nextInt(5);
			if (timeMin >= 60) {
				timeHour++;
				timeMin -= 60;
			}
			askTime.setText("今天 " + timeHour + ":" + timeMin);
			askTxt.setText(s);

			// TODO 设置右侧聊天信息
			View rightView = mInflater.inflate(R.layout.chat_right_row, null);
			TextView answerTxt = (TextView) rightView.findViewById(R.id.chat_r_txt);
			TextView answerTime = (TextView) rightView.findViewById(R.id.chat_time_r);
			timeMin += ran.nextInt(10);
			if (timeMin >= 60) {
				timeHour++;
				timeMin -= 60;
			}
			answerTime.setText("今天 " + timeHour + ":" + timeMin);
			answerTxt.setText(answers[c]);

			// TODO 添加至布局
			helpInfoLayout.addView(leftView);
			helpInfoLayout.addView(rightView);
			c++;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_btn_back:
			// TODO 执行返回操作
			backWithAnimate();
			break;
		case R.id.title_btn_background:
			changeChatBg();
			break;
		default:
			break;
		}
	}

	int currBgIndex = 0;

	private void changeChatBg() {
		currBgIndex++;
		if (currBgIndex >= chatBgs.length) {
			currBgIndex = 0;
		}
		showToast("currBgIndex:" + currBgIndex);
		chatBg.setBackgroundResource(chatBgs[currBgIndex]);
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
		bgBtn.setVisibility(View.VISIBLE);

		// TODO 设置标题中CURR_HIGH_WORD为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 按钮事件绑定
		backBtn.setOnClickListener(this);
		bgBtn.setOnClickListener(this);

		chatBg = (ImageView) findViewById(R.id.chat_bg);
	}
}
