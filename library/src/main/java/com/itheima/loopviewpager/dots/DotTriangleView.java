package com.itheima.loopviewpager.dots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class DotTriangleView extends DotCustomView {

    public DotTriangleView(Context context) {
        super(context);
    }

    @Override
    public void customDraw(Canvas canvas, Paint paint) {
        Path path = new Path();
        path.moveTo(getWidth() / 2, 0);
        path.lineTo(0, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth() / 2, 0);
        canvas.drawPath(path, paint);
    }

}
