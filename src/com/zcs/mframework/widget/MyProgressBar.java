package com.zcs.mframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyProgressBar extends View {
	private int max = 100;
	private int progress = 0;

	public MyProgressBar(Context context) {
		super(context);
	}

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		System.out.println("***************onDraw***************");
		System.out.println(this.getWidth() + "*" + this.getHeight());
		System.out.println("Progress:" + progress);
		int w = this.getWidth();
		int h = this.getHeight();

		Paint paint = new Paint();
		paint.setColor(0xFF2277BB);
		paint.setAntiAlias(true);
		float draw = ((float) progress / (float) max) * w;
		RectF rectf = new RectF(0, 0, draw, h);
		canvas.drawRect(rectf, paint);

		int percent = (int) (((float) progress / (float) max) * 100); // 中间的进度百分比，先转换成float在进行除法运算，不然都为0
		float textWidth = paint.measureText(percent + " %");
		float textSize = 20;
		paint.setTextSize(textSize);
		paint.setStrokeWidth(2);
		paint.setColor(Color.WHITE);

		canvas.drawText(percent + " %", (w - textWidth) / 2, h - 3 - (h - textSize) / 2, paint);
	}

	/**
	 * 获取进度.需要同步
	 * 
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步 刷新界面调用postInvalidate()能在非UI线程刷新
	 * 
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if (progress < 0) {
			throw new IllegalArgumentException("progress not less than 0");
		}
		if (progress > max) {
			progress = max;
		}
		if (progress <= max) {
			this.progress = progress;
			postInvalidate();
		}
	}

}
