package com.itheima.loopviewpager.anim;

import android.content.Context;
import android.widget.Scroller;

public class FixedSpeedScroller extends Scroller {

    private int mDuration;

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

}
