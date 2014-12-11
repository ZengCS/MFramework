package com.zcs.mframework.activities;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.utils.DisplayUtil;
import com.zcs.mframework.utils.HandlerMsg;
import com.zcs.mframework.utils.TextUtils;

@SuppressLint("HandlerLeak")
public class AnimationActivity extends BaseActivity {
	private static final String CURR_TITLE = "AnimationDemo";// 标题
	private static final String CURR_HIGH_WORD = "Animation";// 标题高亮词
	private RelativeLayout animationLayout;

	private ImageView pandaImg;// 图片
	private int maxX, maxY, screenW, screenH, imgW, imgH;// 高度宽度设置
	private int toX, toY, fromX, fromY;// 偏移量设置
	private boolean isAnimation = false;// 是否正在动画
	private long animationCycle = 400l;// 动画周期
	private Random rand = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_animation);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();
		// TODO 获取屏幕信息
		initScreenInfo();

		animationLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				System.out.println("点击的位置:" + x + "," + y);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// TODO 触摸屏幕时刻
					System.out.println("触摸屏幕时刻:" + event.getX() + "," + event.getY());
					toX = (int) event.getX() - 64;
					toY = (int) event.getY() - 64;

					if (!animationing) {
						startAnimation();
					}
					break;
				case MotionEvent.ACTION_MOVE:
					// TODO 触摸并移动时刻
					System.out.println("触摸并移动时刻:" + event.getX() + "," + event.getY());
					break;
				case MotionEvent.ACTION_UP:
					// TODO 终止触摸时刻
					System.out.println("终止触摸时刻:" + event.getX() + "," + event.getY());
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	private void initScreenInfo() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenW = dm.widthPixels;// 获取分辨率宽度
		screenH = dm.heightPixels;// 获取分辨率高度

		Bitmap bm = BitmapFactory.decodeResource(res, R.drawable.dadatoo);
		imgW = bm.getWidth();// 获取图片宽度
		imgH = bm.getHeight();// 获取图片高度

		maxX = screenW - imgW;
		maxY = screenH - imgH - DisplayUtil.dip2px(currActivity, 80);

		fromX = 0;
		fromY = 0;

		toX = (screenW - imgW) / 2;// 计算X偏移量
		toY = (screenH - imgH) / 2;// 计算Y偏移量
		Matrix matrix = new Matrix();
		// matrix.postTranslate(offsetX, offsetY);
		matrix.postTranslate(fromX, fromY);
		pandaImg.setImageMatrix(matrix);// 设置动画初始位置为正中心

		String info = "screenW:" + screenW + ", screenH:" + screenH;
		info += "imgW:" + imgW + ", imgH:" + imgH;
		info += "offsetX:" + toX + ", offsetY:" + toY;
		System.out.println(info);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerMsg.START_ANIMATION:
				// TODO 开始动画线程
				postDelayed(animationRunable, animationCycle);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 动画线程
	 */
	Runnable animationRunable = new Runnable() {
		@Override
		public void run() {
			startAnimation();
		}
	};
	private boolean animationing = false;

	/**
	 * 开始动画
	 */
	private void startAnimation() {
		animationing = true;
		Animation animation = new TranslateAnimation(fromX, toX, fromY, toY);
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(animationCycle);
		pandaImg.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animationing = false;
			}
		});
		updateOffset();
	}

	/**
	 * 更新随机位置
	 */
	private void updateOffset() {
		// TODO 设置下一次的起点为上一次的终点
		fromX = toX;
		fromY = toY;

		// TODO 设置下一次的终点为屏幕内的随机点
		// toX = rand.nextInt(maxX);
		// toY = rand.nextInt(maxY);
		//
		// if (isAnimation) {
		// mHandler.sendEmptyMessage(HandlerMsg.START_ANIMATION);
		// }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_btn_back:
			// TODO 执行返回操作
			backWithAnimate();
			break;
		case R.id.title_btn_start:
			TextView btn = (TextView) v;
			showToast("" + btn.getText());
			if (btn.getText().equals(res.getString(R.string.start))) {
				btn.setText(res.getString(R.string.stop));
				// TODO 开始动画
				isAnimation = true;
				startAnimation();
			} else {
				// TODO 停止动画
				isAnimation = false;
				btn.setText(res.getString(R.string.start));
			}
			// mHandler.sendEmptyMessage(0);
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
		// startBtn.setVisibility(View.VISIBLE);

		// TODO 设置标题中CURR_HIGH_WORD为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 按钮事件绑定
		backBtn.setOnClickListener(this);
		startBtn.setOnClickListener(this);

		pandaImg = (ImageView) findViewById(R.id.panda_img);

		animationLayout = (RelativeLayout) findViewById(R.id.animationLayout);
	}
}
