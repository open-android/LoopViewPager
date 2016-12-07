package com.itheima.loopviewpager.anim.transformer;

import android.view.View;

/**
 * 折叠
 */
public class AccordionTransformer extends LoopTransformer {

    @Override
    public void transformPage(View view, float position) {
        view.setPivotX(position <= 0 ? view.getMeasuredWidth() : 0);
        view.setScaleX(position <= 0 ? 1f + position : 1f - position);
    }

}
