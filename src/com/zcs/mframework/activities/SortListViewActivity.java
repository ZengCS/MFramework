package com.zcs.mframework.activities;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zcs.mframework.R;
import com.zcs.mframework.adapter.SortAdapter;
import com.zcs.mframework.base.BaseActivity;
import com.zcs.mframework.entity.SortModel;
import com.zcs.mframework.utils.CharacterParser;
import com.zcs.mframework.utils.PinyinComparator;
import com.zcs.mframework.utils.TextUtils;
import com.zcs.mframework.widget.ClearEditText;
import com.zcs.mframework.widget.SideBar;
import com.zcs.mframework.widget.SideBar.OnTouchingLetterChangedListener;

public class SortListViewActivity extends BaseActivity {
	private static final String CURR_TITLE = "城市列表";// 标题
	private static final String CURR_HIGH_WORD = "城市";// 标题高亮词

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义title栏
		setContentView(R.layout.activity_sort_list_view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fw_title_bar);

		currActivity = this;
		res = getResources();

		// TODO 初始化组件
		initComponent();

		initViews();
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
	}

	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> sourceDataList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	private void initViews() {
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				SortModel model = (SortModel) adapter.getItem(position);
				Toast.makeText(getApplication(), model.getName(), Toast.LENGTH_SHORT).show();
			}
		});

		// sourceDataList =
		// filledData(getResources().getStringArray(R.array.cities));
		sourceDataList = initData(getResources().getStringArray(R.array.cities));

		// 根据a-z进行排序源数据
		// Collections.sort(sourceDataList, pinyinComparator);
		adapter = new SortAdapter(this, sourceDataList);
		sortListView.setAdapter(adapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param data
	 * @return
	 */
	private List<SortModel> initData(String[] data) {
		List<SortModel> mSortList = new ArrayList<SortModel>();
		String currSort = "";
		for (String s : data) {
			SortModel sortModel = new SortModel();
			if (s.matches("[A-Z*#]")) {
				currSort = s;
				continue;
			}
			sortModel.setName(s);
			sortModel.setSortLetters(currSort);
			mSortList.add(sortModel);
		}
		return mSortList;
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param data
	 * @return
	 */
	private List<SortModel> filledData(String[] data) {
		List<SortModel> mSortList = new ArrayList<SortModel>();
		for (String s : data) {
			SortModel sortModel = new SortModel();
			// if (s.matches("[A-Z]")) {
			// currSort = s;
			// }
			//
			// sortModel.setName(s);
			// sortModel.setSortLetters(s);
			sortModel.setName(s);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(s);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		if (android.text.TextUtils.isEmpty(filterStr)) {
			filterDateList = sourceDataList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : sourceDataList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		// Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}
