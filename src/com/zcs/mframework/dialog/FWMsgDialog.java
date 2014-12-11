package com.zcs.mframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcs.mframework.R;

/**
 * 信息提示或确认提示框
 * 
 * @author ZengCS
 */
public class FWMsgDialog {
	private static Button okBtn, cancelBtn;// 按钮
	private static ImageView btnSplitLine;// 按钮分割线

	/**
	 * 创建信息提示框
	 * 
	 * @param context
	 *            父级容器
	 * @param msg
	 *            提示信息
	 * @param cancelable
	 *            是否可以取消
	 * @param okStr
	 *            确定按钮文字
	 * @param onOkBtnClick
	 *            确定按钮事件
	 * @param cancelStr
	 *            取消按钮文字
	 * @param onCancelBtnClick
	 *            取消按钮事件
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Dialog createDialog(Context context, String msg, boolean cancelable, String okStr, OnClickListener onOkBtnClick, String cancelStr, OnClickListener onCancelBtnClick) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.fw_msg_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view_msg);// 加载布局
		// dx_loading_dialog.xml中的ImageView
		TextView msgTitle = (TextView) v.findViewById(R.id.dialog_msg_title);// 提示文字
		msgTitle.setText(msg);// 设置加载信息

		// TODO 初始化组件
		okBtn = (Button) v.findViewById(R.id.dialog_msg_btn_ok);
		cancelBtn = (Button) v.findViewById(R.id.dialog_msg_btn_cancel);
		btnSplitLine = (ImageView) v.findViewById(R.id.dialog_btn_split_line);

		// 设置确定按钮文字及点击事件
		if (okStr != null && !okStr.equals("")) {
			okBtn.setText(okStr);
		}
		if (onOkBtnClick != null) {
			okBtn.setOnClickListener(onOkBtnClick);
		}

		// TODO 如果取消按钮的点击事件为null,那么隐藏取消按钮和分割线
		if (onCancelBtnClick == null) {
			cancelBtn.setVisibility(View.GONE);
			btnSplitLine.setVisibility(View.GONE);
		} else {
			// 设置取消按钮文字及点击事件
			if (cancelStr != null && !cancelStr.equals("")) {
				cancelBtn.setText(cancelStr);
			}
			if (onCancelBtnClick != null) {
				cancelBtn.setOnClickListener(onCancelBtnClick);
			}
		}

		Dialog loadingDialog = new Dialog(context, R.style.custom_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(cancelable);// 不可以用“返回键”
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;
	}
}
