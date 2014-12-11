package com.zcs.mframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class AndroidRobotView extends View {
	private Paint paint;
	private Canvas canvas;

	public AndroidRobotView(Context context) {
		super(context);
	}

	public AndroidRobotView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AndroidRobotView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.paint = new Paint();// 画笔
		paint.setAntiAlias(true);// 消除锯齿
		paint.setColor(0xFFA4C739);// 设置颜色
		this.canvas = canvas;
		drawRobot();
	}

	/**
	 * @param offsetX
	 * @param offsetY
	 */
	private void drawRobot() {
		// 绘制机器人的头
		drawHead();
		// 绘制眼睛
		drawEyes();
		// 绘制天线
		drawTianxian();
		// 绘制身体
		drawBody();
		// 绘制胳膊
		drawArm();
		// 绘制腿
		drawLegs();
	}

	/**
	 * 画头
	 */
	private void drawHead() {
		RectF head = new RectF(10, 10, 100, 100);
		head.offset(100, 20);// 设置在x轴上偏移100像素,y轴上便宜20像素
		canvas.drawArc(head, -10, -160, false, paint);// 画弧
	}

	/**
	 * 画眼睛
	 */
	private void drawEyes() {
		paint.setColor(Color.WHITE);// 设为白色
		canvas.drawCircle(135, 53, 4, paint);
		canvas.drawCircle(175, 53, 4, paint);
		paint.setColor(0xFFA4C739);// 恢复绿色
	}

	/**
	 * 话天线
	 */
	private void drawTianxian() {
		paint.setStrokeWidth(2);// 设置画笔的宽度
		canvas.drawLine(120, 15, 135, 35, paint);
		canvas.drawLine(190, 15, 175, 35, paint);
	}

	/**
	 * 画身体
	 */
	private void drawBody() {
		canvas.drawRect(110, 75, 200, 150, paint);
		RectF body = new RectF(110, 140, 200, 160);
		canvas.drawRoundRect(body, 10, 10, paint);
	}

	/**
	 * 画手臂
	 */
	private void drawArm() {
		RectF arm = new RectF(85, 75, 105, 140);
		canvas.drawRoundRect(arm, 10, 10, paint);
		arm.offset(120, 0);// 设置在x轴上偏移120像素
		canvas.drawRoundRect(arm, 10, 10, paint);
	}

	/**
	 * 画腿
	 */
	private void drawLegs() {
		RectF leg = new RectF(125, 150, 145, 200);
		canvas.drawRoundRect(leg, 10, 10, paint);
		leg.offset(40, 0);// 设置在x轴上偏移40像素
		canvas.drawRoundRect(leg, 10, 10, paint);
	}
}
