package com.zcs.mframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class SlideListItemView extends RelativeLayout {
	private static final String TAG = "CustomView";
	private Button delBtn;

	private Scroller mScroller;
	private GestureDetector mGestureDetector;

	public SlideListItemView(Context context) {
		this(context, null);
	}

	public SlideListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickable(true);
		setLongClickable(true);
		mScroller = new Scroller(context);
		mGestureDetector = new GestureDetector(context, new CustomGestureListener());
	}

	// 调用此方法滚动到目标位置
	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy);
	}

	// 调用此方法设置滚动的相对偏移
	public void smoothScrollBy(int dx, int dy) {
		// 设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
		invalidate();// 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}

	@Override
	public void computeScroll() {
		// 先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
			// 这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// 必须调用该方法，否则不一定能看到滚动效果
			postInvalidate();
		}
		super.computeScroll();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			System.out.println("*****ACTION_UP," + getScrollX());
			int x = getScrollX();
			if (x == 0) {// 触发点击事件
				System.out.println("***** 触发点击事件 *****");
			} else if (x > 200) {
				smoothScrollTo(200, 0);
				if (delBtn != null) {
					delBtn.setVisibility(View.VISIBLE);
				}
			} else {
				smoothScrollTo(0, 0);
				if (delBtn != null) {
					delBtn.setVisibility(View.GONE);
				}
			}
			break;
		default:
			return mGestureDetector.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

	class CustomGestureListener implements GestureDetector.OnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			int dis = (int) ((distanceX - 0.5) / 2);
			smoothScrollBy(dis, 0);
			// 大于0：从右往左滑，小于0：从左往右滑
			if (dis > 0) {
				smoothScrollBy(dis, 0);
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return false;
		}
	}

	//
	// public boolean isShowDelete() {
	// return isShowDelete;
	// }
	//
	// public void setShowDelete(boolean isShowDelete) {
	// this.isShowDelete = isShowDelete;
	// }

	public Button getDelBtn() {
		return delBtn;
	}

	public void setDelBtn(Button delBtn) {
		this.delBtn = delBtn;
	}
}