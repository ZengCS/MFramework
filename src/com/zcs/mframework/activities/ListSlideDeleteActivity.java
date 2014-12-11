package com.zcs.mframework.activities;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.zcs.mframework.R;
import com.zcs.mframework.adapter.MSlideListAdapter;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.entity.CountryEntity;
import com.zcs.mframework.utils.TextUtils;

public class ListSlideDeleteActivity extends BaseActivity {
	private static final String CURR_TITLE = "列表滑动删除";// 标题
	private static final String CURR_HIGH_WORD = "删除";// 标题高亮词
//	private SlideListItemView slideItem;
	private Button delBtn;

	private ListView mListView;
	private MSlideListAdapter mListAdapter;
	private List<CountryEntity> displayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_list_slide_delete);
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
		case R.id.listItemDelBtn:
			showToast("删除!!!");
//			slideItem.smoothScrollTo(0, 0);
			delBtn.setVisibility(View.GONE);
			break;
		case R.id.scrollItem:
			showToast("点击事件!!");
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

		delBtn = (Button) findViewById(R.id.listItemDelBtn);
		delBtn.setOnClickListener(this);

//		slideItem = (SlideListItemView) findViewById(R.id.scrollItem);
//		slideItem.setDelBtn(delBtn);
//		slideItem.setOnClickListener(this);

		// TODO 初始化List数据
		displayList = new ArrayList<CountryEntity>();
		for (int i = 1; i <= 0; i++) {
			CountryEntity entity = new CountryEntity();
			entity.setZhName("等待被删除项 - " + i);
			displayList.add(entity);
		}

		mListView = (ListView) findViewById(R.id.mSlideList);
		mListAdapter = new MSlideListAdapter(currActivity, displayList);

		mListView.setAdapter(mListAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				CountryEntity item = displayList.get(position);
				showToast(item.getZhName());
			}
		});
	}
}
