package com.zcs.mframework.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.InputType;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.jpush.android.api.JPushInterface;

import com.zcs.mframework.R;
import com.zcs.mframework.adapter.MGridAdapter;
import com.zcs.mframework.base.PushBaseActivity;
import com.zcs.mframework.commons.MainGridConfig;
import com.zcs.mframework.dialog.CustomInputDialogBuilder;
import com.zcs.mframework.dialog.CustomSelectorDialogBuilder;
import com.zcs.mframework.dialog.FWMsgDialog;
import com.zcs.mframework.dialog.FWProgressDialog;
import com.zcs.mframework.entity.ConfigItem;
import com.zcs.mframework.entity.MainGridItemEntity;
import com.zcs.mframework.utils.GlobalConstant;
import com.zcs.mframework.utils.HandlerMsg;
import com.zcs.mframework.utils.SharedPreferencesUtils;
import com.zcs.mframework.utils.StringUtils;
import com.zcs.mframework.utils.T;
import com.zcs.mframework.utils.TextUtils;

@SuppressLint("HandlerLeak")
public class MainActivity extends PushBaseActivity {
	private static final String CURR_TITLE = "MFramework社区";// 标题
	private static final String CURR_HIGH_WORD = "MFramework";// 标题高亮词
	private GridView mGridView;

	private boolean iszb = false;// 是否是装逼模式,默认否
	private String iszbModel = "开启";
	public static boolean isForeground = false;// 是否最前

	private LayoutInflater mInflater;

	private static List<MainGridItemEntity> mainGridList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();
		// 注册推送服务
		registerMessageReceiver();

		// TODO 开启定时服务
		Timer timer = new Timer(true);
		// 延时1000ms后执行，1分钟执行一次
		timer.schedule(pushServiceTask, GlobalConstant.PUSH_SERVICE_DELAY, GlobalConstant.PUSH_SERVICE_PERIOD);
		// timer.cancel(); //退出计时器

		// 初始化进度条
		mInflater = LayoutInflater.from(this);

		SharedPreferencesUtils.setParam(this, "String", "xiaanming");
		SharedPreferencesUtils.setParam(this, "int", 10);
		SharedPreferencesUtils.setParam(this, "boolean", true);
		SharedPreferencesUtils.setParam(this, "long", 100L);
		SharedPreferencesUtils.setParam(this, "float", 1.1f);

		SharedPreferencesUtils.getParam(this, "String", "");
		SharedPreferencesUtils.getParam(this, "int", 0);
		SharedPreferencesUtils.getParam(this, "boolean", false);
		SharedPreferencesUtils.getParam(this, "long", 0L);
		SharedPreferencesUtils.getParam(this, "float", 0.0f);
	}

	@Override
	public void initComponent() {
		super.initCommonComponent();
		// TODO 初始化组件
		titleBarTxt.setText(CURR_TITLE);
		// 设置按钮显示状态
		configBtn.setVisibility(View.VISIBLE);

		// TODO 设置标题中MFramework为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 绑定按钮点击事件
		configBtn.setOnClickListener(this);

		// TODO 打开消息推送Activity
		findViewById(R.id.btn_jpush_init).setOnClickListener(this);
		findViewById(R.id.btn_jpush_stop).setOnClickListener(this);
		findViewById(R.id.btn_jpush_resume).setOnClickListener(this);

		// TODO 初始化GridView
		initMainGridView();
	}

	/**
	 * 初始化主GridView
	 */
	private void initMainGridView() {
		// 初始化数据List
		if (mainGridList == null || mainGridList.size() == 0) {
			mainGridList = new ArrayList<MainGridItemEntity>(0);
			for (int i = 0; i < MainGridConfig.names.length; i++) {
				MainGridItemEntity m = new MainGridItemEntity();
				m.setName(MainGridConfig.names[i]);
				m.setIconId(MainGridConfig.icons[i]);
				m.setActivityClass(MainGridConfig.activitys[i]);
				mainGridList.add(m);
			}
		}

		MGridAdapter mGridAdapter = new MGridAdapter(currActivity, mainGridList);
		mGridView = (GridView) findViewById(R.id.mMainGridView);

		// 添加绑定
		mGridView.setAdapter(mGridAdapter);
		// 添加消息处理
		mGridView.setOnItemClickListener(new GridItemClickListener());
	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	private class GridItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
			MainGridItemEntity item = mainGridList.get(position);
			try {
				Integer flag = (Integer) item.getActivityClass();
				mHandler.sendEmptyMessage(flag);
			} catch (Exception e) {
				Class<?> targetActivity = (Class<?>) item.getActivityClass();
				changeActivity(targetActivity);
			}
		}
	}

	@Override
	protected void showToast(String text) {
		super.showToast(text);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerMsg.MAIN_GRID_EVENT_MSGDIALOG:
				showMsgDialog();
				break;
			case HandlerMsg.MAIN_GRID_EVENT_CONFIRMDIALOG:
				showConfirmDialog();
				break;
			case HandlerMsg.MAIN_GRID_EVENT_LOADINGDIALOG:
				showLoadingDialog();
				break;
			case HandlerMsg.MAIN_GRID_EVENT_SELECTORDIALOG:
				showSelectorDialog();
				break;
			case HandlerMsg.MAIN_GRID_EVENT_INPUTDIALOG:
				showInputDialog();
				break;
			case HandlerMsg.MAIN_GRID_EVENT_SHARE:// 分享
				shareToFriends();
				break;
			case HandlerMsg.SELECTOR_DIALOG_CALLBACK:
				// TODO 选择对话框回调
				View v = (View) msg.obj;
				showToast("您的选择是：" + v.getContentDescription());
				if ("jPush_start".equals(v.getContentDescription())) {
					JPushInterface.init(currActivity);
				} else if ("jPush_stop".equals(v.getContentDescription())) {
					JPushInterface.stopPush(currActivity);
				} else if ("jPush_resume".equals(v.getContentDescription())) {
					JPushInterface.resumePush(currActivity);
				}
				selectorDialog.dismiss();
				break;
			case HandlerMsg.PUSH_SERVICE_STATE:// TODO 推送服务状态监听
				if (JPushInterface.isPushStopped(currActivity)) {
					showToast("推送服务已停止，即将自动重启");
					JPushInterface.resumePush(currActivity);
				} else {
					// showToast("推送服务正常!");
				}
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 调用系统的分享功能
	 */
	private void shareToFriends() {
		showToast("分享!");

		String path = Environment.getExternalStorageDirectory().getPath() + "/BaiduMapSDK/";

		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		File file = new File(path + "capture.png");

		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		shareIntent.putExtra(Intent.EXTRA_TEXT, "你怎么还没有来啊，这里超好玩的！(分享自MFramework)");
		shareIntent.setType("image/jpeg");
		startActivity(Intent.createChooser(shareIntent, "分享"));
	}

	/**
	 * 展示消息对话框
	 */
	protected void showMsgDialog() {
		msgDialog = FWMsgDialog.createDialog(currActivity, "你好，世界！", true, "我知道了", new OnClickListener() {
			@Override
			public void onClick(View v) {
				showToast("你好！");
				msgDialog.dismiss();
			}
		}, null, null);
		msgDialog.show();
	}

	/**
	 * 展示输入对话框
	 */
	protected void showInputDialog() {
		if (inputDialog != null) {
			inputDialog.show();
			return;
		}

		final CustomInputDialogBuilder builder = new CustomInputDialogBuilder(currActivity);
		// TODO 设置有多少个输入框
		builder.setInputCount(CustomInputDialogBuilder.DOUBLE_INPUT);
		// TODO 设置输入类型为-密码
		builder.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		// TODO 设置输入框提示文字
		String[] hints = { "请输入密码...", "请再次输入密码..." };
		builder.setHints(hints);

		inputDialog = builder.createDialog("密 码 修 改", "确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] inputs = builder.getInputStrings();
				if (StringUtils.isBlank(inputs[0])) {
					builder.showErrorMsg("请输入新的密码!");
				} else if (StringUtils.isBlank(inputs[1])) {
					builder.showErrorMsg("请再次输入新的密码!");
				} else if (!inputs[0].equals(inputs[1])) {
					builder.showErrorMsg("两次输入不一致!");
				} else {// 输入成功
					inputDialog.dismiss();
					builder.clearInput();
					showToast("密码修改成功!");
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

	/**
	 * 展示选择对话框
	 */
	protected void showSelectorDialog() {
		if (selectorDialog != null) {
			selectorDialog.show();
			return;
		}

		CustomSelectorDialogBuilder builder = new CustomSelectorDialogBuilder(currActivity, mHandler);
		builder.setOnSelectFlag(HandlerMsg.SELECTOR_DIALOG_CALLBACK);// 选择后回调的msg.what

		// TODO 设置可选项
		ConfigItem[] selectorItems = new ConfigItem[3];

		ConfigItem c1 = new ConfigItem("开启服务", "Start jPush Service", "jPush_start", true);
		ConfigItem c2 = new ConfigItem("停止服务", "Stop jPush Service", "jPush_stop", true);
		ConfigItem c3 = new ConfigItem("重启服务", "Resume jPush Service", "jPush_resume", true);

		selectorItems[0] = c1;
		selectorItems[1] = c2;
		selectorItems[2] = c3;

		builder.setSelectorItems(selectorItems);

		selectorDialog = builder.createDialog("jPush选项", "取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectorDialog.dismiss();
			}
		});

		selectorDialog.show();
	}

	/**
	 * 展示自定义加载框
	 */
	protected void showLoadingDialog() {
		progressDialog = FWProgressDialog.createDialog(currActivity, "拼命加载中...", false);
		progressDialog.show();
		showToast("万万没想到，啦啦啦啦啦...");
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				progressDialog.dismiss();
			}
		}, 3000);
	}

	/**
	 * 展示自定义确认框
	 */
	protected void showConfirmDialog() {
		if (iszb) {
			iszbModel = "关闭";
		} else {
			iszbModel = "开启";
		}
		// TODO 展示确认框
		msgDialog = FWMsgDialog.createDialog(currActivity, iszbModel + "攻城狮模式？", true, iszbModel, new OnClickListener() {
			@Override
			public void onClick(View v) {
				showToast("成功" + iszbModel + "攻城狮模式！");
				msgDialog.dismiss();
				iszb = !iszb;
			}
		}, "取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				showToast("取消");
				msgDialog.dismiss();
			}
		});
		msgDialog.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_btn_exit:
			// TODO 执行退出操作
			exitConfirm();
			break;
		case R.id.title_btn_config:
			// TODO 进入系统设置
			changeActivity(ConfigActivity.class, R.anim.push_down_in, R.anim.push_down_out);
			break;
		case R.id.btn_jpush_init:
			// TODO 初始化JPush服务
			JPushInterface.init(currActivity);
			showToast("服务开启成功！");
			break;
		case R.id.btn_jpush_stop:
			// TODO 停止JPush服务
			JPushInterface.stopPush(currActivity);
			showToast("服务停止成功！");
			break;
		case R.id.btn_jpush_resume:
			// TODO 重启JPush服务
			JPushInterface.resumePush(currActivity);
			showToast("服务重启成功！");
			break;
		default:
			break;
		}
	}

	/**
	 * 连续按两次返回键就返回桌面
	 */
	private long firstTime;

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - firstTime < 3000) {
			backToHome();
		} else {
			firstTime = System.currentTimeMillis();
			T.showShort(this, R.string.press_again_backrun);
		}
	}

	private void backToHome() {
		Intent home = new Intent(Intent.ACTION_MAIN);
		home.addCategory(Intent.CATEGORY_HOME);
		startActivity(home);
	}

	/**
	 * 退出提示
	 */
	private void exitConfirm() {
		msgDialog = FWMsgDialog.createDialog(currActivity, res.getString(R.string.exit_confirm), true, "退出", new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					msgDialog.dismiss();
					finish();
					Process.killProcess(Process.myPid());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				msgDialog.dismiss();
			}
		});

		msgDialog.show();
	}

	/**
	 * 推送服务状态监听任务
	 */
	TimerTask pushServiceTask = new TimerTask() {
		public void run() {
			mHandler.sendEmptyMessage(HandlerMsg.PUSH_SERVICE_STATE);
		}
	};

	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}

	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}
}
