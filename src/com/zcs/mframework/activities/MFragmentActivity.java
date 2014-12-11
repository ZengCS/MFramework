package com.zcs.mframework.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseFragmentActivity;
import com.zcs.mframework.fragment.MainFragment;
import com.zcs.mframework.listener.MainListener;
import com.zcs.mframework.utils.TextUtils;

public class MFragmentActivity extends BaseFragmentActivity implements MainListener {
	private static final String CURR_TITLE = "FragmentDemo";// 标题
	private static final String CURR_HIGH_WORD = "Fragment";// 标题高亮词

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_mfragment);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();
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
	public void changeMainFragment(Fragment currFragment, Fragment targetFragment, String direction) {
		if (targetFragment == currFragment) {
			return;
		}
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		if ("Left".equals(direction)) {
			transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
		} else {
			transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
		}

		// 先判断是否被add过
		if (!targetFragment.isAdded() && !(targetFragment instanceof MainFragment)) {
			showToast("[" + targetFragment + "]尚未被添加过，\n隐藏当前[" + currFragment + "]!");
			// 隐藏当前的fragment，add下一个到Activity中
			transaction.hide(currFragment).add(R.id.fragment_main_content, targetFragment);
		} else {
			showToast("[" + targetFragment + "]已被添加过，\n隐藏当前[" + currFragment + "]!");
			// 隐藏当前的fragment，显示下一个
			transaction.hide(currFragment).show(targetFragment);
		}

		// transaction.replace(R.id.fragment_main_content, targetFragment);
		transaction.commit();
	}
}
