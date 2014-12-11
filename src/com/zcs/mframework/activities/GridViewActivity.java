package com.zcs.mframework.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.utils.TextUtils;

public class GridViewActivity extends BaseActivity {
	private static final String CURR_TITLE = "GridViewDemo";// 标题
	private static final String CURR_HIGH_WORD = "GridView";// 标题高亮词
	private GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_grid_view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();
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

		// TODO 设置标题中CURR_HIGH_WORD为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 按钮事件绑定
		backBtn.setOnClickListener(this);

		mGridView = (GridView) findViewById(R.id.mGridView);

		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> gridItemList = new ArrayList<HashMap<String, Object>>();
		for (int i = 1; i <= 100; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.ic_launcher);
			map.put("ItemText", "NO." + String.valueOf(i));
			gridItemList.add(map);
		}
		// 初始化适配器
		SimpleAdapter gridAdapter = new SimpleAdapter(this,// 当前容器
				gridItemList, // 数据源
				R.layout.night_item,// 元素布局
				new String[] { "ItemImage", "ItemText" },// 字段名称
				new int[] { R.id.itemIcon, R.id.itemName // View ID
				});
		// 添加绑定
		mGridView.setAdapter(gridAdapter);
		// 添加消息处理
		mGridView.setOnItemClickListener(new ItemClickListener());
	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	private class ItemClickListener implements OnItemClickListener {
		@SuppressWarnings("unchecked")
		public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
			HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(position);
			showToast("" + item.get("ItemText"));
		}
	}
}
