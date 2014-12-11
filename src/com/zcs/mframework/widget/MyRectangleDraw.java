package com.zcs.mframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyRectangleDraw extends View {
	private Paint paint;

	public MyRectangleDraw(Context context) {
		this(context, null);
	}

	public MyRectangleDraw(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyRectangleDraw(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		int w = this.getWidth();
		int h = this.getHeight();

		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setAntiAlias(true); // 设置画笔为无锯齿
		paint.setColor(Color.BLACK); // 设置画笔颜色
		canvas.drawColor(Color.WHITE); // 白色背景
		paint.setStrokeWidth((float) 3.0); // 线宽
		paint.setStyle(Style.STROKE); // 空心效果
		Rect r1 = new Rect(); // Rect对象
		r1.left = 50; // 左边
		r1.top = 50; // 上边
		r1.right = 250; // 右边
		r1.bottom = 150; // 下边
		canvas.drawRect(r1, paint); // 绘制矩形
		RectF r2 = new RectF(); // RectF对象
		r2.left = 50; // 左边
		r2.top = 200; // 上边
		r2.right = 250; // 右边
		r2.bottom = 300; // 下边
		canvas.drawRoundRect(r2, 10, 10, paint); // 绘制圆角矩形
	}

}
