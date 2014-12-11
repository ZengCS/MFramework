package com.zcs.mframework.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.adapter.MViewPagerAdapter;
import com.zcs.mframework.utils.TextUtils;

public class SplashActivity extends Activity implements OnClickListener {
	private ViewPager viewPager;// 页卡内容
	private List<View> pages;// Tab页面列表
	private int currIndex = 0;// 当前页卡编号
	private View page1, page2, page3;// 各个页卡
	private LinearLayout dotLayout, splashLayout;// 底部圆点布局,Splash布局
	private ImageView[] dots;// 底部小点图片
	private String isFirstRunTag;// 是否是第一次运行的标签
	private TextView currVersion;// 当前版本信息

	private Button intoBtn;// 进入按钮
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// x秒后进入主界面
		// changeToMainActivity(1000);

		isFirstRunTag = "MFramework" + getVersion();

		sharedPreferences = this.getSharedPreferences("MFramework", MODE_PRIVATE);
		boolean isFirstRun = sharedPreferences.getBoolean(isFirstRunTag, true);

		if (isFirstRun) {// TODO 为第一次运行显示向导
			// TODO 初始化新功能界面ViewPager
			initViewPager();
		} else {
			// TODO 初始化Splash界面
			initSplash();
		}
	}

	/**
	 * 初始化Splash界面
	 */
	private void initSplash() {
		splashLayout = (LinearLayout) findViewById(R.id.splash_layout);
		splashLayout.setVisibility(View.VISIBLE);

		changeToMainActivity(2000);
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
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		// TODO 设置当前版本信息
		currVersion = (TextView) findViewById(R.id.txt_curr_version);
		currVersion.setText("Ver " + getVersion());

		viewPager = (ViewPager) findViewById(R.id.index_view_pager);
		viewPager.setVisibility(View.VISIBLE);
		pages = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		page1 = inflater.inflate(R.layout.index_page_1, null);
		page2 = inflater.inflate(R.layout.index_page_2, null);
		page3 = inflater.inflate(R.layout.index_page_3, null);
		pages.add(page1);
		pages.add(page2);
		pages.add(page3);
		viewPager.setAdapter(new MViewPagerAdapter(pages));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		// TODO 设置按钮中MFramework为高亮
		intoBtn = (Button) page3.findViewById(R.id.btn_into_main);
		intoBtn.setText(this.getString(R.string.into_community));
		SpannableString target = TextUtils.highlight(intoBtn.getText(), 4, 14, Color.parseColor("#113965"));
		target = TextUtils.highlight(intoBtn.getText(), "MFramework", Color.RED);
		intoBtn.setText(target);
		intoBtn.setOnClickListener(this);

		initDot();
	}

	/**
	 * Page切换监听
	 * 
	 * @author ZengCS
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int position) {
			currIndex = position;
			setCurrDot(position);
		}
	}

	/**
	 * 初始化底部小点
	 */
	private void initDot() {
		dotLayout = (LinearLayout) findViewById(R.id.dot_layout);
		dotLayout.setVisibility(View.VISIBLE);
		dots = new ImageView[3];
		// 循环取得小点图片
		for (int i = 0; i < 3; i++) {
			dots[i] = (ImageView) dotLayout.getChildAt(i);
			dots[i].setEnabled(false);// 都设为白色
			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
		}
		dots[currIndex].setEnabled(true);// 设置为白色，即选中状态
	}

	/**
	 * 设置当前选中的点
	 */
	private void setCurrDot(int position) {
		for (ImageView dot : dots) {
			dot.setEnabled(false);
		}
		dots[position].setEnabled(true);
	}

	/**
	 * 进入主界面
	 * 
	 * @param delay
	 *            延时,单位ms
	 */
	private void changeToMainActivity(long delay) {
		new Handler().postDelayed(new Runnable() {
			// 为了减少代码使用匿名Handler创建一个延时的调用
			public void run() {
				// 第一个参数: 当前activity 第二个参数:目标activity
				// Intent i = new Intent(SplashActivity.this,
				// MainActivity.class);
				Intent i = new Intent(SplashActivity.this, MainActivity.class);
				// 通过Intent打开最终真正的主界面Main这个Activity
				SplashActivity.this.startActivity(i); // 启动Main界面
				SplashActivity.this.finish(); // 关闭自己这个开场屏
				overridePendingTransition(R.anim.fade, R.anim.hold);
			}
		}, delay);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_into_main:// 进入主界面
			Editor editor = sharedPreferences.edit();
			editor.putBoolean(isFirstRunTag, false);
			editor.commit();

			changeToMainActivity(0);
			break;
		default:
			break;
		}
	}
}
