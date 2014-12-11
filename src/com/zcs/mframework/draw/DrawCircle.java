package com.zcs.mframework.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class DrawCircle implements DrawGraphics {

	private Paint paint = null;
	private Paint paint_eye = null;

	public DrawCircle() {
		paint = new Paint();
		paint_eye = new Paint();
	}

	@Override
	public void draw(Canvas canvas) {
		// TODOAuto-generated method stub
		// 绘制圆形(圆心x，圆心y，半径r，画笔p)
		paint_eye.setAntiAlias(true);
		paint.setAntiAlias(true);
		RectF rectF = new RectF(120, 60, 370, 240);
		paint_eye.setColor(Color.WHITE);
		paint.setColor(Color.GREEN);
		canvas.drawCircle(190, 110, 18, paint_eye);
		canvas.drawCircle(300, 110, 18, paint_eye);
		canvas.drawArc(rectF, 180, 180, true, paint);
	}
}