package com.zcs.mframework.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseFragment;
import com.zcs.mframework.listener.MainListener;

public class TabFragment extends BaseFragment {
	private Button tab1, tab2, tab3, tab4;
	private MainListener lis;
	private Button[] tabs;
	private static int currTabIndex = 0;// 当前Tab下标
	private MainFragment mainFragment = null;
	private FragmentOne f1 = null;
	private FragmentTwo f2 = null;
	private FragmentThree f3 = null;
	public static Fragment currFragment = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		lis = (MainListener) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_tab, container);
		this.currActivity = getActivity();
		this.inflater = inflater;
		this.res = currActivity.getResources();

		initComponent();
		return root;
	}

	@Override
	public void initComponent() {
		mainFragment = new MainFragment();
		currFragment = mainFragment;
		currTabIndex = 0;

		// TODO Tab按钮组
		tab1 = (Button) root.findViewById(R.id.tab_1);
		tab2 = (Button) root.findViewById(R.id.tab_2);
		tab3 = (Button) root.findViewById(R.id.tab_3);
		tab4 = (Button) root.findViewById(R.id.tab_4);

		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
		tab4.setOnClickListener(this);

		tabs = new Button[4];
		tabs[0] = tab1;
		tabs[1] = tab2;
		tabs[2] = tab3;
		tabs[3] = tab4;
	}

	/**
	 * 更新Fragment
	 * 
	 * @param v
	 *            当前Tab对象
	 * @param targetFragment
	 *            切换至Fragment对象
	 */
	private void updateFragment(View v, Fragment targetFragment) {
		int clickTabIndex = 0;
		// TODO 设置高亮Tab按钮
		for (int i = 0; i < tabs.length; i++) {
			Button tab = tabs[i];
			if (v.equals(tab)) {// 点击的Tab
				v.setBackgroundResource(R.drawable.btn_light_blue);
				clickTabIndex = i;
			} else {// 非点击的Tab
				tab.setBackgroundResource(R.drawable.btn_blue_lightblue);
			}
		}

		String direction = "Left";
		// TODO 判断Fragment进入的方向,左或者右
		if (clickTabIndex > currTabIndex) {
			// TODO leftIn
			direction = "Left";
		} else {
			// TODO rightIn
			direction = "Right";
		}

		// TODO 切换页面
		lis.changeMainFragment(currFragment, targetFragment, direction);
		currFragment = targetFragment;
		currTabIndex = clickTabIndex;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_1:
			if (mainFragment == null) {
				mainFragment = new MainFragment();
			}
			updateFragment(v, mainFragment);
			break;
		case R.id.tab_2:
			if (f1 == null) {
				f1 = new FragmentOne();
			}
			updateFragment(v, f1);
			break;
		case R.id.tab_3:
			if (f2 == null) {
				f2 = new FragmentTwo();
			}
			updateFragment(v, f2);
			break;
		case R.id.tab_4:
			if (f3 == null) {
				f3 = new FragmentThree();
			}
			updateFragment(v, f3);
			break;
		default:
			break;
		}
	}
}
