package com.zcs.mframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.widget.ClearableEditText;

/**
 * 带输入框的对话框
 * 
 * @author ZengCS
 */
public class CustomInputDialogBuilder {
	public static final int ONE_INPUT = 1;// 只有1个输入框
	public static final int DOUBLE_INPUT = 2;// 有2个输入框
	private int inputCount = ONE_INPUT;
	private int inputType = -1;
	private String[] hints = { "请输入...", "请输入..." };

	private Button okBtn, cancelBtn;// 按钮
	private ClearableEditText t1, t2;
	private TextView errorTxt;

	private boolean cancelable = true;

	private Context context;

	public CustomInputDialogBuilder() {
	}

	public CustomInputDialogBuilder(Context context) {
		this.context = context;
	}

	/**
	 * 创建信息提示框
	 * 
	 * @param context
	 *            父级容器
	 * @param title
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
	public Dialog createDialog(String title, String okStr, OnClickListener onOkBtnClick, String cancelStr, OnClickListener onCancelBtnClick) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.fw_input_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view_msg);// 加载布局
		TextView dialogTitle = (TextView) v.findViewById(R.id.dialog_title);// 提示文字
		dialogTitle.setText(title);// 设置加载信息

		// TODO 初始化组件
		okBtn = (Button) v.findViewById(R.id.dialog_msg_btn_ok);
		cancelBtn = (Button) v.findViewById(R.id.dialog_msg_btn_cancel);

		errorTxt = (TextView) v.findViewById(R.id.input_error_msg);

		t1 = (ClearableEditText) v.findViewById(R.id.input_dialog_txt1);
		t2 = (ClearableEditText) v.findViewById(R.id.input_dialog_txt2);
		if (inputType != -1) {
			t1.setInputType(inputType);
			t2.setInputType(inputType);
		}

		t1.setHint(hints[0]);
		t2.setHint(hints[1]);

		// 只展示一个输入框
		if (this.inputCount == ONE_INPUT) {
			t2.setVisibility(View.GONE);
		}

		// 设置确定按钮文字及点击事件
		if (okStr != null && !okStr.equals("")) {
			okBtn.setText(okStr);
		}
		if (onOkBtnClick != null) {
			okBtn.setOnClickListener(onOkBtnClick);
		}

		// 设置取消按钮文字及点击事件
		if (cancelStr != null && !cancelStr.equals("")) {
			cancelBtn.setText(cancelStr);
		}
		if (onCancelBtnClick != null) {
			cancelBtn.setOnClickListener(onCancelBtnClick);
		}

		Dialog loadingDialog = new Dialog(context, R.style.custom_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(cancelable);// 不可以用“返回键”
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;
	}

	/**
	 * 获取输入的文字
	 * 
	 * @return
	 */
	public String[] getInputStrings() {
		if (this.inputCount == ONE_INPUT) {
			String[] s = { t1.getText().toString() };
			return s;
		} else {
			String[] s = { t1.getText().toString(), t2.getText().toString() };
			return s;
		}
	}

	/**
	 * 清空输入内容
	 */
	public void clearInput() {
		t1.setText("");
		t2.setText("");
		errorTxt.setVisibility(View.GONE);
	}

	public void showErrorMsg(String msg) {
		errorTxt.setText(msg);
		errorTxt.setVisibility(View.VISIBLE);

		Animation animation = new TranslateAnimation(0, 0, -30, 0);
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(280l);
		errorTxt.startAnimation(animation);
	}

	public boolean isCancelable() {
		return cancelable;
	}

	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	public int getInputCount() {
		return inputCount;
	}

	public void setInputCount(int inputCount) {
		this.inputCount = inputCount;
	}

	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public String[] getHints() {
		return hints;
	}

	public void setHints(String[] hints) {
		if (hints != null) {
			if (hints.length == 1) {
				this.hints[0] = hints[0];
			} else {
				this.hints = hints;
			}
		}
	}
}
