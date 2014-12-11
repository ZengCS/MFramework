package com.zcs.mframework.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zcs.mframework.R;
import com.zcs.mframework.base.BaseFragment;

public class MainFragment extends BaseFragment {
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_main, null);
		this.currActivity = getActivity();
		this.inflater = inflater;
		this.res = currActivity.getResources();

		return root;
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void initComponent() {

	}

}
