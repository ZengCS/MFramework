package com.zcs.mframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.entity.ConfigItem;
import com.zcs.mframework.utils.HandlerMsg;

/**
 * 带选项的消息对话框
 * 
 * @author ZengCS
 */
public class CustomSelectorDialogBuilder {
	private boolean cancelable = true;
	private Handler handler;
	private LayoutInflater mInflater;
	private Context context;
	private int onSelectFlag;
	private ConfigItem[] selectorItems;

	private Button okBtn;// 按钮
	private LinearLayout selectorLayout;// 选择区域
	private TextView cfgItemName, cfgItemVal;

	public CustomSelectorDialogBuilder() {
	}

	public CustomSelectorDialogBuilder(Context context, Handler mHandler) {
		this.context = context;
		this.handler = mHandler;
	}

	/**
	 * 创建对话框
	 * 
	 * @param title
	 *            标题
	 * @param okStr
	 *            确定按钮
	 * @param onOkBtnClick
	 *            确定按钮对应点击事件
	 * @return 返回Dialog对象
	 */
	@SuppressWarnings("deprecation")
	public Dialog createDialog(String title, String okStr, OnClickListener onOkBtnClick) {
		mInflater = LayoutInflater.from(context);
		View v = mInflater.inflate(R.layout.fw_selector_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view_msg);// 加载布局
		// dx_loading_dialog.xml中的ImageView
		TextView msgTitle = (TextView) v.findViewById(R.id.dialog_title);// 提示文字
		msgTitle.setText(title);// 设置加载信息

		// TODO 初始化组件
		okBtn = (Button) v.findViewById(R.id.dialog_msg_btn_ok);
		selectorLayout = (LinearLayout) v.findViewById(R.id.selector_layout);

		if (selectorItems != null) {
			initConfig(selectorItems);
		}

		// 设置确定按钮文字及点击事件
		if (okStr != null && !okStr.equals("")) {
			okBtn.setText(okStr);
		}
		if (onOkBtnClick != null) {
			okBtn.setOnClickListener(onOkBtnClick);
		}

		Dialog loadingDialog = new Dialog(context, R.style.custom_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(cancelable);// 设置能否取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;
	}

	/**
	 * 初始化设置选项
	 * 
	 * @param cfgs
	 * @param layout
	 */
	private void initConfig(ConfigItem[] cfgs) {
		int c = 0;
		for (final ConfigItem cfg : cfgs) {
			cfg.setOnClickListener(cfgItemOnclickListener);

			View cfgItem = null;
			c++;
			if (c == 1) {// TODO 添加第一个选项
				cfgItem = mInflater.inflate(R.layout.config_item_t, null);
			} else if (c == cfgs.length) { // TODO 添加最后一个选项
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
			selectorLayout.addView(cfgItem);
		}
	}

	/**
	 * 选项菜单点击时触发事件
	 */
	private OnClickListener cfgItemOnclickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				// String tag = v.getContentDescription().toString();
				Message msg = new Message();
				msg.what = HandlerMsg.SELECTOR_DIALOG_CALLBACK;
				msg.obj = v;
				handler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public boolean isCancelable() {
		return cancelable;
	}

	/**
	 * 窗口是否可以被取消，默认为true
	 * 
	 * @param cancelable
	 */
	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	public int getOnSelectFlag() {
		return onSelectFlag;
	}

	public void setOnSelectFlag(int onSelectFlag) {
		this.onSelectFlag = onSelectFlag;
	}

	public ConfigItem[] getSelectorItems() {
		return selectorItems;
	}

	public void setSelectorItems(ConfigItem[] selectorItems) {
		this.selectorItems = selectorItems;
	}
}
