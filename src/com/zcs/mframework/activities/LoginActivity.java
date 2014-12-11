package com.zcs.mframework.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.dialog.FWProgressDialog;
import com.zcs.mframework.utils.StringUtils;
import com.zcs.mframework.widget.ClearableEditText;

@SuppressLint("HandlerLeak")
public class LoginActivity extends BaseActivity {
	private static final String DFT_USERNAME = "admin";
	private static final String DFT_PASSWORD = "zcs123";

	private ClearableEditText username, password;
	private Button loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 自定义title栏
		setContentView(R.layout.activity_login);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			// TODO 执行登录操作
			loginValidate();
			break;
		default:
			break;
		}
	}

	/**
	 * 账号验证
	 */
	private void loginValidate() {
		String u = username.getText().toString();
		String p = password.getText().toString();

		if (u.equalsIgnoreCase(DFT_USERNAME) && p.equalsIgnoreCase(DFT_PASSWORD)) {
			// TODO 账号密码正确
			SharedPreferences sharedPreferences = this.getSharedPreferences("MFramework", MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.putString("userNameCache", u);
			editor.putString("passwordCache", p);
			editor.commit();

			// TODO 登录
			login();
		} else {
			showToast("Error:账号或者密码错误!");
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 执行登录
	 */
	private void login() {
		progressDialog = FWProgressDialog.createDialog(currActivity, "正在登录...", false);
		progressDialog.show();

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				changeActivity(MainActivity.class);
				progressDialog.dismiss();
			}
		}, 2000);
	}

	@Override
	public void initComponent() {
		username = (ClearableEditText) findViewById(R.id.login_username);
		password = (ClearableEditText) findViewById(R.id.login_password);
		loginBtn = (Button) findViewById(R.id.login_btn);

		// TODO 从缓存文件中读取账号密码
		SharedPreferences sharedPreferences = this.getSharedPreferences("MFramework", MODE_PRIVATE);
		String userNameCache = sharedPreferences.getString("userNameCache", DFT_USERNAME);
		String passwordCache = sharedPreferences.getString("passwordCache", "");
		
		username.setText(userNameCache);
		password.setText(passwordCache);

		if (StringUtils.isNotBlank(userNameCache) && StringUtils.isNotBlank(passwordCache)) {
			login();
		}

		loginBtn.setOnClickListener(this);
	}
}
