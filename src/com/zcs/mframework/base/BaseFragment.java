package com.zcs.mframework.base;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseFragment extends Fragment implements OnClickListener {
	protected View root;
	protected Activity currActivity;
	protected LayoutInflater inflater;
	protected Resources res;

	/**
	 * 初始化组件
	 */
	public abstract void initComponent();
}
