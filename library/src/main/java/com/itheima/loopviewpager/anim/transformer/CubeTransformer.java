package com.itheima.loopviewpager.anim.transformer;

import android.view.View;

/**
 * 立方体
 */
public class CubeTransformer extends LoopTransformer {

    @Override
    public void transformPage(View view, float position) {
        view.setPivotX(position <= 0 ? view.getMeasuredWidth() : 0);
        view.setPivotY(view.getMeasuredHeight() * 0.5f);
        view.setRotationY(90f * position);
    }

}
