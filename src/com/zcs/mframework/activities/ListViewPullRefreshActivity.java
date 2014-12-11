package com.zcs.mframework.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zcs.mframework.R;
import com.zcs.mframework.adapter.MListAdapter;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.entity.CountryEntity;
import com.zcs.mframework.pullrefresh.ui.PullToRefreshBase;
import com.zcs.mframework.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.zcs.mframework.pullrefresh.ui.PullToRefreshListView;
import com.zcs.mframework.sqlite.CountryManager;
import com.zcs.mframework.utils.TextUtils;

@SuppressLint("SimpleDateFormat")
public class ListViewPullRefreshActivity extends BaseActivity {
	private static final String CURR_TITLE = "ListViewDemo";// 标题
	private static final String CURR_HIGH_WORD = "ListView";// 标题高亮词

	// TODO 下拉刷新相关
	private PullToRefreshListView mPullListView;
	private ListView mListView;
	private MListAdapter mListAdapter;

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private boolean mIsStart = true;
	private int mCurIndex = 0;
	private static final int mPageSize = 10;// 每次加载记录数

	// TODO 数据库相关
	private CountryManager mCountryManager;
	private List<CountryEntity> entityList, displayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_list_view_pull_refresh);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();

		// TODO 初始化数据列表
		mCountryManager = new CountryManager(currActivity);
		entityList = mCountryManager.list();
		if (entityList.size() == 0) {
			showToast("请先初始化数据库！！！");
			finish();
			changeActivity(SQLiteActivity.class);
			return;
		}
		displayList = new ArrayList<CountryEntity>();

		mPullListView = (PullToRefreshListView) findViewById(R.id.mPullListView);

		mPullListView.setPullLoadEnabled(false);
		mPullListView.setScrollLoadEnabled(true);

		mPullListView.setDividerDrawable(res.getDrawable(R.drawable.list_divider));

		mCurIndex = mPageSize;
		for (int i = 0; i < mCurIndex; i++) {
			displayList.add(entityList.get(i));
		}
		mListAdapter = new MListAdapter(currActivity, displayList);
		// mListAdapter = new MListAdapter(currActivity, itemList);
		mListView = mPullListView.getRefreshableView();
		mListView.setAdapter(mListAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				CountryEntity item = displayList.get(position);
				showToast(item.getZhName());
			}
		});

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mIsStart = true;
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				mIsStart = false;
				new GetDataTask().execute();
			}
		});
		setLastUpdateTime();

		mPullListView.doPullRefreshing(true, 200);
	}

	/**
	 * 获取数据任务类
	 * 
	 * @author ZengCS
	 */
	private class GetDataTask extends AsyncTask<Void, Void, List<CountryEntity>> {
		@Override
		protected List<CountryEntity> doInBackground(Void... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// return CountryGlobal.FIFA2014Brazil;
			return entityList;
		}

		@Override
		protected void onPostExecute(List<CountryEntity> result) {
			boolean hasMoreData = true;
			if (mIsStart) {// 下拉刷新
				showToast("没有最新数据。");
			} else {
				int start = mCurIndex;
				int end = mCurIndex + mPageSize;
				if (end >= entityList.size()) {
					end = entityList.size();
					hasMoreData = false;
				}

				for (int i = start; i < end; ++i) {
					displayList.add(entityList.get(i));
				}
				mCurIndex = end;
			}

			// 刷新列表
			mListAdapter.notifyDataSetChanged(displayList);

			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();

			super.onPostExecute(result);
		}
	}

	/**
	 * 设置上次更新时间
	 */
	private void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		mPullListView.setLastUpdatedLabel(text);
	}

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}
		return mDateFormat.format(new Date(time));
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

		// TODO 设置标题中ListView为高亮
		SpannableString target = TextUtils.highlight(titleBarTxt.getText(), CURR_HIGH_WORD, Color.RED);
		titleBarTxt.setText(target);

		// TODO 按钮事件绑定
		backBtn.setOnClickListener(this);
	}
}
