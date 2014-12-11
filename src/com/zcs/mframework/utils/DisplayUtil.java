package com.zcs.mframework.utils;

import android.content.Context;

public class DisplayUtil {
	public static int dip2px(Context paramContext, float paramFloat) {
		float f = paramContext.getResources().getDisplayMetrics().density;
		return (int) (paramFloat * f + 0.5F);
	}

	public static int px2dip(Context paramContext, float paramFloat) {
		float f = paramContext.getResources().getDisplayMetrics().density;
		return (int) (paramFloat / f + 0.5F);
	}
}