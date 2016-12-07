package com.itheima.loopviewpager.anim.transformer;

import android.view.View;

public abstract class LoopVerticalTransformer extends LoopTransformer {

    @Override
    public void transformPage(View view, float position) {
        transformViewPage(view, position);
        if (position < -1) {
            view.setAlpha(0);
        } else if (position <= 1) {
            view.setAlpha(1);
            view.setTranslationX(-position * view.getMeasuredWidth());
            view.setTranslationY(position * view.getMeasuredHeight());
        } else {
            view.setAlpha(0);
        }
    }

    public abstract void transformViewPage(View view, float position);

}
