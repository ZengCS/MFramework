package com.zcs.mframework.commons;

import com.zcs.mframework.R;
import com.zcs.mframework.activities.AnimationActivity;
import com.zcs.mframework.activities.ArticleActivity;
import com.zcs.mframework.activities.BaiduLocationActivity;
import com.zcs.mframework.activities.ChatActivity;
import com.zcs.mframework.activities.CircleProgressActivity;
import com.zcs.mframework.activities.DrawGraphicActivity;
import com.zcs.mframework.activities.GridViewActivity;
import com.zcs.mframework.activities.ListViewPullRefreshActivity;
import com.zcs.mframework.activities.MFragmentActivity;
import com.zcs.mframework.activities.MyButtonsActivity;
import com.zcs.mframework.activities.QRCodeScanActivity;
import com.zcs.mframework.activities.SQLiteActivity;
import com.zcs.mframework.activities.SortListViewActivity;
import com.zcs.mframework.activities.ViewPagerActivity;
import com.zcs.mframework.utils.HandlerMsg;

/**
 * 主界面菜单管理
 * 
 * @author ZengCS
 * @since 2014年8月1日
 */
public class MainGridConfig {
	/**
	 * 图标
	 */
	public static Integer[] icons = { R.drawable.calendar,// 第1个
			R.drawable.calendar,// 第2个
			R.drawable.calendar,// 第3个
			R.drawable.calendar,// 第4个
			R.drawable.star_128,// 第4.1个
			R.drawable.star_128,// 第4.2个
			R.drawable.star_128,// 第5个
			R.drawable.star_128,// 第6个
			R.drawable.trends,// 第7个
			R.drawable.trends,// 第8个
			R.drawable.trends,// 第9个
			R.drawable.trends, // 第10个
			R.drawable.wallet, // 第11个
			R.drawable.wallet, // 第12个
			R.drawable.wallet, // 第13个
			R.drawable.wallet, // 第14个
			R.drawable.chat_128, // 第15个
			// R.drawable.chat_128, // 第16个
			R.drawable.chat_128, // 第17个
			R.drawable.chat_128, // 第18个
			R.drawable.chat_128, // 第19个
	};

	/**
	 * 功能名称
	 */
	public static String[] names = { "按钮样式",// 第1个
			"信息框",// 第2个
			"确认框",// 第3个
			"加载框",// 第4个
			"选择框",// 第4.1个
			"输入框",// 第4.2个
			"动画",// 第5个
			"圆进度条",// 第6个
			"SQlite",// 第7个
			"滑动刷新",// 第8个
			"Fragment",// 第9个
			"ViewPager",// 第10个
			"GridView",// 第11个
			"百度定位",// 第12个
			"技术交流",// 第13个
			"分享",// 第14个
			"微聊天",// 第15个
			// "List删除",// 第16个
			"城市列表",// 第17个
			"二维码",// 第18个
			"画机器人",// 第19个
	};

	/**
	 * 对应的Activity
	 */
	public static Object[] activitys = { MyButtonsActivity.class,// 第1个-自定义按钮样式
			HandlerMsg.MAIN_GRID_EVENT_MSGDIALOG,// 第2个-自定义信息框
			HandlerMsg.MAIN_GRID_EVENT_CONFIRMDIALOG, // 第3个-自定义确认框
			HandlerMsg.MAIN_GRID_EVENT_LOADINGDIALOG, // 第4个-自定义加载框
			HandlerMsg.MAIN_GRID_EVENT_SELECTORDIALOG, // 第4.1个-自定义选择对话框
			HandlerMsg.MAIN_GRID_EVENT_INPUTDIALOG, // 第4.2个-自定义输入对话框
			AnimationActivity.class,// 第5个-我的动画
			CircleProgressActivity.class,// 第6个-圆形进度条
			SQLiteActivity.class,// 第7个-数据库SQlite
			ListViewPullRefreshActivity.class,// 第8个-列表滑动刷新
			MFragmentActivity.class,// 第9个-FragmentDemo
			ViewPagerActivity.class,// 第10个-ViewPagerDemo
			GridViewActivity.class,// 第11个-GridViewDemo
			BaiduLocationActivity.class,// 第12个-百度定位Demo
			ArticleActivity.class,// 第13个-百度定位Demo
			HandlerMsg.MAIN_GRID_EVENT_SHARE, // 第14个-分享
			ChatActivity.class,// 第15个-微聊天
			// ListSlideDeleteActivity.class,// 第16个-List元素滑动删除
			SortListViewActivity.class,// 第17个-城市列表
			QRCodeScanActivity.class,// 第18个-二维码扫描
			DrawGraphicActivity.class,// 第19个-二维码扫描
	};
}
