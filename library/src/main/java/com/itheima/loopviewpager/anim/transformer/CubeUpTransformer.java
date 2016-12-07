package com.itheima.loopviewpager.anim.transformer;

import android.view.View;

/**
 * 立方体
 */
public class CubeUpTransformer extends LoopVerticalTransformer {

    @Override
    public void transformViewPage(View view, float position) {
        view.setPivotX(view.getMeasuredWidth() * 0.5f);
        view.setPivotY(position <= 0 ? view.getMeasuredHeight() : 0);
        view.setRotationX(-90f * position);
    }

}
