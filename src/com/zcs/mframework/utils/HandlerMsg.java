package com.zcs.mframework.utils;

public class HandlerMsg {
	public final static int EXIT_APP = 0x1F0001;// 退出程序

	public final static int EXE_LOGIN = 0x1F0002;// 执行登录
	public final static int LOGIN_SUCCESS = 0x1F0003;// 登录成功
	public final static int LOGIN_FAILED = 0x1F0004;// 登录失败

	public final static int EXE_LOGOUT = 0x1F0005;// 执行注销

	public final static int PUSH_SERVICE_STATE = 0x1F0006;// 推送服务状态监测

	public final static int START_ANIMATION = 0x1F0007;// 开始动画

	// 主界面点击事件
	public final static int MAIN_GRID_EVENT_MSGDIALOG = 0x1F0008;// 显示信息框
	public final static int MAIN_GRID_EVENT_CONFIRMDIALOG = 0x1F0009;// 显示确认框
	public final static int MAIN_GRID_EVENT_LOADINGDIALOG = 0x1F000A;// 显示加载框
	public final static int MAIN_GRID_EVENT_SELECTORDIALOG = 0x1F000B;// 显示选择对话框
	public final static int MAIN_GRID_EVENT_INPUTDIALOG = 0x1F000C;// 显示选择对话框

	public final static int SELECTOR_DIALOG_CALLBACK = 0x1F000D;// 选择对话框回调

	public final static int MAIN_GRID_EVENT_SHARE = 0x1F000E;// 分享
}
