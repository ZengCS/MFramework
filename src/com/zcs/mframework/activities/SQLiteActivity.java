package com.zcs.mframework.activities;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcs.mframework.R;
import com.zcs.mframework.adapter.DBListAdapter;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.entity.CountryEntity;
import com.zcs.mframework.sqlite.CountryGlobal;
import com.zcs.mframework.sqlite.CountryManager;
import com.zcs.mframework.utils.TextUtils;

public class SQLiteActivity extends BaseActivity {
	private static final String CURR_TITLE = "SQLiteDemo";
	private static final String CURR_HIGH_WORD = "SQLite";// 标题高亮词

	// TODO 数据库相关
	private CountryManager mCountryManager;
	private TextView dbCount;
	private int c = 1;
	private LinearLayout layoutDbList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_sqlite);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();

		mCountryManager = new CountryManager(currActivity);
		// TODO 加载数据库
		loadSQLiteData();
	}

	/**
	 * 加载SQLite数据库数据
	 */
	private void loadSQLiteData() {
		List<CountryEntity> list = mCountryManager.list();
		dbCount.setText("数据库中共有[" + list.size() + "]条数据！");

		layoutDbList.removeViews(1, layoutDbList.getChildCount() - 1);
		DBListAdapter dbAdapter = new DBListAdapter(currActivity, layoutDbList);
		dbAdapter.initList(list);
	}

	/**
	 * 清空数据
	 */
	private void deleteAll() {
		mCountryManager.deleteAll();
		c = 1;
	}

	/**
	 * 初始化数据库
	 */
	private void initDB() {
		// TODO 初始化数据之前，先删除原来的数据
		deleteAll();

		Set<Entry<String, String>> mSet = CountryGlobal.COUNTRY_MAP.entrySet();
		Iterator<Entry<String, String>> mIterator = mSet.iterator();
		CountryEntity entity;
		while (mIterator.hasNext()) {
			Map.Entry<String, String> mEntry = (Map.Entry<String, String>) mIterator.next();
			entity = new CountryEntity();
			entity.setZhName(mEntry.getValue());
			entity.setEnName(mEntry.getKey());
			entity.setRank(c);
			entity.setDescription(mEntry.getValue() + "[" + mEntry.getKey() + "]是一个风情万种的国度！");
			entity.setState("和平");
			mCountryManager.insert(entity);
			c++;
		}
		showToast("数据库初始成功！");
		loadSQLiteData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_btn_back:
			// TODO 执行返回操作
			backWithAnimate();
			break;
		case R.id.btn_db_init:
			// TODO 初始化数据库
			initDB();
			break;
		case R.id.btn_db_clear:
			// TODO 清空数据库
			deleteAll();
			showToast("数据库已经清空。");
			loadSQLiteData();
			break;
		case R.id.btn_db_refresh:
			// TODO 刷新
			showToast("刷新");
			loadSQLiteData();
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

		// TODO 设置标题中ListView为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 初始化组件
		dbCount = (TextView) findViewById(R.id.tv_db_count);
		layoutDbList = (LinearLayout) findViewById(R.id.layout_db_list);

		// TODO 按钮事件绑定
		backBtn.setOnClickListener(this);
		findViewById(R.id.btn_db_init).setOnClickListener(this);
		findViewById(R.id.btn_db_clear).setOnClickListener(this);
		findViewById(R.id.btn_db_refresh).setOnClickListener(this);
	}
}
