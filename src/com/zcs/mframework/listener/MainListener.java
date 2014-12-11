package com.zcs.mframework.listener;

import android.support.v4.app.Fragment;

public interface MainListener {
	public void changeMainFragment(Fragment currFragment, Fragment targetFragment, String direction);
}
