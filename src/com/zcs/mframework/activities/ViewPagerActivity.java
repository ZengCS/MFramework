package com.zcs.mframework.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.adapter.MViewPagerAdapter;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.utils.TextUtils;

@SuppressLint("HandlerLeak")
public class ViewPagerActivity extends BaseActivity {
	private static final String CURR_TITLE = "ViewPagerDemo";
	private static final String CURR_HIGH_WORD = "ViewPager";// 标题高亮词

	private ViewPager viewPager;// 页卡内容
	private ImageView imageView;// 动画图片
	private TextView title1, title2, title3;
	private TextView[] titles;
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View view1, view2, view3;// 各个页卡
	private LinearLayout dotLayout;// 底部圆点布局
	private ImageView[] dots;// 底部小点图片
	private static boolean showDot = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_view_pager);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();

		// TODO 各种初始化
		initCursorLine();
		initViewPager();
		if (showDot) {
			initDot();
		}
	}

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.my_view_pager);
		views = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		view1 = inflater.inflate(R.layout.v_page_1, null);
		view2 = inflater.inflate(R.layout.v_page_2, null);
		view3 = inflater.inflate(R.layout.v_page_3, null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		viewPager.setAdapter(new MViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 头标点击监听
	 */
	private class MyPagerTitleListener implements OnClickListener {
		private int index = 0;

		public MyPagerTitleListener(int idx) {
			this.index = idx;
		}

		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
			setHighTitle(index);
		}
	}

	/**
	 * 初始化底部小点
	 */
	private void initDot() {
		dotLayout = (LinearLayout) findViewById(R.id.my_dot_layout);
		dotLayout.setVisibility(View.VISIBLE);
		dots = new ImageView[titles.length];
		// 循环取得小点图片
		for (int i = 0; i < titles.length; i++) {
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
		if (showDot) {
			for (ImageView dot : dots) {
				dot.setEnabled(false);
			}
			dots[position].setEnabled(true);
		}
	}

	/**
	 * 设置当前高亮title
	 */
	private void setHighTitle(final int position) {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				for (TextView tv : titles) {
					tv.setTextColor(res.getColor(R.color.lightgray));
				}
				titles[position].setTextColor(res.getColor(R.color.eclipse));
			}
		}, 300);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:// TODO
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 初始化标题下的光标
	 */
	private void initCursorLine() {
		imageView = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
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

		// TODO 设置标题中ListView为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 按钮事件绑定
		backBtn.setOnClickListener(this);

		// TODO 初始化Titles
		title1 = (TextView) findViewById(R.id.tv_title_1);
		title2 = (TextView) findViewById(R.id.tv_title_2);
		title3 = (TextView) findViewById(R.id.tv_title_3);

		titles = new TextView[3];
		titles[0] = title1;
		titles[1] = title2;
		titles[2] = title3;

		title1.setOnClickListener(new MyPagerTitleListener(0));
		title2.setOnClickListener(new MyPagerTitleListener(1));
		title3.setOnClickListener(new MyPagerTitleListener(2));
	}

	/**
	 * Page切换监听
	 * 
	 * @author ZengCS
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int position) {
			Animation animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);
			currIndex = position;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
			setCurrDot(position);
			setHighTitle(position);
		}
	}
}
