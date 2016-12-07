package com.itheima.loopviewpager.anim.transformer;

import android.view.View;

/**
 * 折叠
 */
public class AccordionUpTransformer extends LoopVerticalTransformer {

    @Override
    public void transformViewPage(View view, float position) {
        view.setPivotY(position <= 0 ? view.getMeasuredHeight() : 0);
        view.setScaleY(position <= 0 ? 1f + position : 1f - position);
    }

}
