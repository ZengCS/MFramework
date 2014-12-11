package com.zcs.mframework.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.utils.TextUtils;

public class QRCodeScanActivity extends BaseActivity {
	private static final String CURR_TITLE = "二维码扫描";// 标题
	private static final String CURR_HIGH_WORD = "二维码";// 标题高亮词

	private final static int SCANNIN_GREQUEST_CODE = 1;
	private TextView mTextView;// 显示扫描结果
	private ImageView mImageView;// 显示扫描拍的图片
	private Button mButton;// 扫描按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_qrcode_scan);
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
		case R.id.button1:
			Intent intent = new Intent();
			intent.setClass(QRCodeScanActivity.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 显示扫描到的内容
				mTextView.setText(bundle.getString("result"));
				// 显示
				mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
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

		mTextView = (TextView) findViewById(R.id.result);
		mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
		mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(this);
	}

}
