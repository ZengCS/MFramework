package com.zcs.mframework.activities;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcs.mframework.FWApplication;
import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.commons.SystemConfig;
import com.zcs.mframework.dialog.CustomInputDialogBuilder;
import com.zcs.mframework.dialog.FWMsgDialog;
import com.zcs.mframework.entity.ConfigItem;
import com.zcs.mframework.utils.LogUtils;
import com.zcs.mframework.utils.StringUtils;
import com.zcs.mframework.utils.TextUtils;

public class ConfigActivity extends BaseActivity {
	private boolean isAnimation = false;// 是否正在动画状态
	private static final String CURR_TITLE = "系统设置";// 标题
	private static final String CURR_HIGH_WORD = "设置";// 标题高亮词
	private LinearLayout cfgLayout1, cfgLayout2, cfgLayout3;
	private View congifSlideLayout, shadeLayout;

	private int toX = 0, toY = 0, fromX = 0, fromY = 0;// 偏移量设置
	private float fromAlpha = 0, toAlpha = 1;
	private static final int SLIDE_LAYOUT_HEIGHT = 400;
	private SharedPreferences sharedPreferences;
	private SystemConfig sysCfg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_config);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();
		mInflater = LayoutInflater.from(this);

		sharedPreferences = this.getSharedPreferences("MFramework", MODE_PRIVATE);

		// TODO 初始化组件
		initComponent();

		// TODO 初始化设置面板
		sysCfg = new SystemConfig(res, sharedPreferences);
		sysCfg.setCurrVersion(getVersion());

		initConfig(cfgLayout1, sysCfg.getUserInfo());
		initConfig(cfgLayout2, sysCfg.getAppInfo());
		initConfig(cfgLayout3, sysCfg.getSysInfo());

		getDpi();
	}

	private void updateSystemConfig(String key, String val) {
		Editor editor = sharedPreferences.edit();
		editor.putString(key, val);
		editor.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_btn_back:
			// TODO 执行返回操作
			backWithAnimate();
			break;
		case R.id.title_btn_save:
			// TODO 保存数据
			saveConfig();
			break;
		case R.id.btn_cfg_clear_mark:// 清除使用痕迹
			// TODO 展示确认框
			msgDialog = FWMsgDialog.createDialog(currActivity, "确定要清除使用痕迹并退出吗？", true, "确定", new OnClickListener() {
				@Override
				public void onClick(View v) {
					clearAndLogout();
					msgDialog.dismiss();
				}
			}, "再想想", new OnClickListener() {
				@Override
				public void onClick(View v) {
					msgDialog.dismiss();
				}
			});
			msgDialog.show();
			break;
		default:
			break;
		}
	}

	/**
	 * 清除使用痕迹并注销
	 */
	private void clearAndLogout() {
		String isFirstRunTag = "MFramework" + getVersion();
		SharedPreferences sharedPreferences = this.getSharedPreferences("MFramework", MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(isFirstRunTag, true);// 设置为第一次运行
		editor.remove("userNameCache");
		editor.remove("passwordCache");

		editor.clear();
		editor.commit();
		showToast("已清除");
		// 先干掉所有
		FWApplication.getInstance().killAll();

		changeActivity(SplashActivity.class);
	}

	/**
	 * 保存配置
	 */
	private void saveConfig() {
		backWithAnimate();
	}

	/**
	 * 重置配置
	 */
	public void resetConfig() {
	}

	// 处理后退键的情况
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (congifSlideLayout.getVisibility() == View.VISIBLE) {
				displayConfigSlideLayout(View.GONE);
			} else {
				backWithAnimate();
			}
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
		saveBtn.setVisibility(View.VISIBLE);

		// TODO 设置标题中ListView为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 按钮事件绑定
		backBtn.setOnClickListener(this);
		saveBtn.setOnClickListener(this);

		findViewById(R.id.btn_cfg_clear_mark).setOnClickListener(this);

		// TODO 初始化设置组件
		cfgLayout1 = (LinearLayout) findViewById(R.id.config_layout_1);
		cfgLayout2 = (LinearLayout) findViewById(R.id.config_layout_2);
		cfgLayout3 = (LinearLayout) findViewById(R.id.config_layout_3);

		congifSlideLayout = findViewById(R.id.config_slide_layout);
		shadeLayout = findViewById(R.id.shade_layout);
		shadeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				displayConfigSlideLayout(View.GONE);
			}
		});
	}

	private String getDpi() {
		String dpi = null;
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
		Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
			Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, dm);
			dpi = dm.widthPixels + "*" + dm.heightPixels;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dpi;
	}

	/**
	 * 展示/隐藏底部滑动操作面板
	 * 
	 * @param visibility
	 */
	private void displayConfigSlideLayout(final int visibility) {
		if (!isAnimation) {
			isAnimation = true;
			congifSlideLayout.setVisibility(visibility);
			shadeLayout.setVisibility(visibility);
			if (visibility == View.GONE) {// 隐藏
				fromY = 0;
				toY = SLIDE_LAYOUT_HEIGHT;
				fromAlpha = 1;
				toAlpha = 0;
			} else {// 显示
				fromY = SLIDE_LAYOUT_HEIGHT;
				toY = 0;
				fromAlpha = 0;
				toAlpha = 1;
			}
			Animation animation = new TranslateAnimation(fromX, toX, fromY, toY);
			animation.setFillAfter(false);// True:图片停在动画结束位置
			animation.setDuration(ANIMATION_DURATION);
			congifSlideLayout.startAnimation(animation);

			Animation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
			alphaAnimation.setFillAfter(false);
			alphaAnimation.setDuration(ANIMATION_DURATION);
			shadeLayout.startAnimation(alphaAnimation);

			animation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					isAnimation = false;
				}
			});
		}
	}

	private TextView cfgItemName, cfgItemVal;

	/**
	 * 选项菜单点击时触发事件
	 */
	private OnClickListener cfgItemOnclickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				String tag = v.getContentDescription().toString();
				if (tag.startsWith("input_")) {// 弹出一个输入框
					showInputDialog(tag);
				} else if (tag.startsWith("select_")) {// 弹出一个选择框
					displayConfigSlideLayout(View.VISIBLE);
				} else {
					showToast("标识符：" + tag);
				}
			} catch (Exception e) {
				LogUtils.e(TAG, "Error:" + e.getMessage());
				e.printStackTrace();
			}
		}
	};

	private String currKey = "";

	/**
	 * 展示输入对话框
	 */
	protected void showInputDialog(String key) {
		this.currKey = key;
		if (inputDialog != null) {
			inputDialog.show();
			return;
		}

		final CustomInputDialogBuilder builder = new CustomInputDialogBuilder(currActivity);
		// TODO 设置有多少个输入框
		builder.setInputCount(CustomInputDialogBuilder.ONE_INPUT);
		String[] hints = { "请输入..." };
		builder.setHints(hints);

		inputDialog = builder.createDialog("系 统 设 置", "确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] inputs = builder.getInputStrings();
				if (StringUtils.isBlank(inputs[0])) {
					builder.showErrorMsg("请输入非空内容!");
				} else {// 输入成功
					inputDialog.dismiss();
					builder.clearInput();
					updateSystemConfig(currKey, inputs[0]);
					refreshConfig();
				}
			}
		}, "取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				inputDialog.dismiss();
				builder.clearInput();
			}
		});

		inputDialog.show();
	}

	protected void refreshConfig() {
		initConfig(cfgLayout1, sysCfg.getUserInfo());
		initConfig(cfgLayout2, sysCfg.getAppInfo());
		initConfig(cfgLayout3, sysCfg.getSysInfo());
	}

	/**
	 * 初始化设置选项
	 * 
	 * @param cfgList
	 * @param layout
	 */
	private void initConfig(LinearLayout layout, ArrayList<ConfigItem> cfgList) {
		layout.removeAllViews();

		int c = 0;
		for (final ConfigItem cfg : cfgList) {
			cfg.setOnClickListener(cfgItemOnclickListener);

			View cfgItem = null;
			c++;
			if (c == 1) {// TODO 添加第一个选项
				cfgItem = mInflater.inflate(R.layout.config_item_t, null);
			} else if (c == cfgList.size()) { // TODO 添加最后一个选项
				cfgItem = mInflater.inflate(R.layout.config_item_b, null);
			} else {// TODO 添加中间的选项
				cfgItem = mInflater.inflate(R.layout.config_item_m, null);
			}
			cfgItemName = (TextView) cfgItem.findViewById(R.id.cfg_item_name);
			cfgItemName.setText(cfg.getName());
			if (cfg.getDrawable() != null) {
				cfgItemName.setCompoundDrawablesWithIntrinsicBounds(cfg.getDrawable(), null, null, null);
			}

			cfgItemVal = (TextView) cfgItem.findViewById(R.id.cfg_item_value);
			cfgItemVal.setText(cfg.getVal());
			cfgItem.setContentDescription(cfg.getKey());

			if (cfg.getOnClickListener() != null) {
				cfgItem.setOnClickListener(cfg.getOnClickListener());
			}
			layout.addView(cfgItem);
		}
	}

	@Override
	protected void backWithAnimate(int... anim) {
		super.backWithAnimate(R.anim.push_up_in, R.anim.push_up_out);
	}
}
