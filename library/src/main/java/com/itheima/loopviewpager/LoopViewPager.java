package com.itheima.loopviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itheima.loopviewpager.anim.AnimStyle;
import com.itheima.loopviewpager.anim.FixedSpeedScroller;
import com.itheima.loopviewpager.anim.transformer.AccordionTransformer;
import com.itheima.loopviewpager.anim.transformer.AccordionUpTransformer;
import com.itheima.loopviewpager.anim.transformer.CubeTransformer;
import com.itheima.loopviewpager.anim.transformer.CubeUpTransformer;
import com.itheima.loopviewpager.listener.OnCreateItemViewListener;
import com.itheima.loopviewpager.listener.OnItemClickListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LoopViewPager<T> extends FrameLayout implements View.OnTouchListener {

    private int loopTime;
    private int animTime;
    private int animStyle;
    private boolean scrollEnable;
    private boolean touchEnable;

    private ViewPager viewPager;
    private final int MIN_TIME = 1000;
    private final int CODE = 1;

    private List<LoopDotsView> loopDotsViews;
    private List<LoopTitleView> loopTitleViews;
    private int realIndex;
    private int showIndex;
    private LoopPagerAdapter loopPagerAdapter;
    private LoopPageChangeListener pageChangeListener;
    private float downX;
    private float downY;
    private long downTime;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE:
                    realIndex++;
                    viewPager.setCurrentItem(realIndex);
                    handler.sendEmptyMessageDelayed(CODE, loopTime);
                    break;
            }
        }
    };

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(false);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopViewPager);
        loopTime = a.getInt(R.styleable.LoopViewPager_loopTime, 0);
        animTime = a.getInt(R.styleable.LoopViewPager_animTime, 0);
        animStyle = a.getInt(R.styleable.LoopViewPager_animStyle, 0);
        scrollEnable = a.getBoolean(R.styleable.LoopViewPager_scrollEnable, true);
        touchEnable = a.getBoolean(R.styleable.LoopViewPager_touchEnable, true);
        a.recycle();
        init();
    }

    private void init() {
        loopDotsViews = new ArrayList<>();//轮播圆点
        loopTitleViews = new ArrayList<>();//轮播文本

        realIndex = -1;//真实的索引
        showIndex = -1;//展示的索引

        // loopTime必须大于MIN_TIME
        if (loopTime > 0 && loopTime < MIN_TIME) {
            loopTime = MIN_TIME;
        }
        // animTime必须小于loopTime
        if (animTime > 0 && animTime > loopTime) {
            animTime = loopTime;
        }
        View.inflate(getContext(), R.layout.hm_loopviewpager, this);
        viewPager = (ViewPager) findViewById(R.id.vp_pager);
        ViewPager.PageTransformer transformer = null;
        switch (animStyle) {
            case AnimStyle.ACCORDION:
                transformer = new AccordionTransformer();
                break;
            case AnimStyle.ACCORDION_UP:
                transformer = new AccordionUpTransformer();
                break;
            case AnimStyle.CUBE:
                transformer = new CubeTransformer();
                break;
            case AnimStyle.CUBE_UP:
                transformer = new CubeUpTransformer();
                break;
        }
        setPageTransformer(animTime, transformer);
    }

    public void setPageTransformer(ViewPager.PageTransformer transformer) {
        setPageTransformer(0, transformer);
    }

    public void setPageTransformer(int animTime, ViewPager.PageTransformer transformer) {
        try {
            if (animTime > 0) {
                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller(getContext());
                scroller.setmDuration(animTime);
                mScroller.set(viewPager, scroller);
            }
        } catch (Exception e) {
        }
        if (transformer != null) {
            viewPager.setPageTransformer(true, transformer);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                downTime = System.currentTimeMillis();
                if (loopTime > 0 && touchEnable) {
                    handler.removeCallbacksAndMessages(null);
                }
                break;
            case MotionEvent.ACTION_UP:
                float x = Math.abs(event.getX() - downX);
                float y = Math.abs(event.getY() - downY);
                float t = Math.abs(System.currentTimeMillis() - downTime);
                if (Math.abs(event.getX() - downX) < 20 && Math.abs(event.getY() - downY) < 20 && System.currentTimeMillis() - downTime < 200) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, realIndex);
                    }
                }
                if (loopTime > 0 && touchEnable) {
                    handler.sendEmptyMessageDelayed(CODE, loopTime);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (scrollEnable) {

            return super.onInterceptTouchEvent(ev);
        } else {
            return true;
        }
    }

    private class LoopPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgLength > 0 ? Integer.MAX_VALUE : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getDefaultItemView(position % imgLength);
            view.setClickable(false);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    private class LoopPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            int currentIndex = position % imgLength;
            if (loopDotsViews.size() > 0 && imgLength > 0) {
                for (LoopDotsView loopDotsView : loopDotsViews) {
                    loopDotsView.update(currentIndex, showIndex);
                }
            }
            if (loopTitleViews.size() > 0 && titleLength > 0) {
                for (LoopTitleView loopTitleView : loopTitleViews) {
                    if (titleData instanceof List) {
                        loopTitleView.setText("" + ((List) titleData).get(currentIndex));
                    } else if (titleData instanceof String[]) {
                        loopTitleView.setText("" + ((String[]) titleData)[currentIndex]);
                    }
                }
            }
            realIndex = position;
            showIndex = currentIndex;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

    }

    private View getDefaultItemView(final int currentIndex) {
        View view = null;
        if (onCreateItemViewListener != null) {
            view = onCreateItemViewListener.getItemView(currentIndex);
        }
        if (view == null) {
            view = new ImageView(getContext());
            if (imgData instanceof List) {
                Glide.with(getContext()).load(((List) imgData).get(currentIndex)).centerCrop().into((ImageView) view);
            } else if (imgData instanceof Integer[]) {
                Glide.with(getContext()).load(((Integer[]) imgData)[currentIndex]).centerCrop().into((ImageView) view);
            } else if (imgData instanceof String[]) {
                Glide.with(getContext()).load(((String[]) imgData)[currentIndex]).centerCrop().into((ImageView) view);
            }
        }
        return view;
    }

    public void start() {
        if (imgLength == 0 && titleLength == 0) {
            throw new IllegalArgumentException();
        }
        loopDotsViews.clear();
        loopTitleViews.clear();
        getLoopChild(this);
        realIndex = 1000 * imgLength;
        showIndex = -1;
        if (pageChangeListener == null) {
            pageChangeListener = new LoopPageChangeListener();
            viewPager.setOnPageChangeListener(pageChangeListener);
        }
        if (loopPagerAdapter == null) {
            loopPagerAdapter = new LoopPagerAdapter();
            viewPager.setAdapter(loopPagerAdapter);
        } else {
            loopPagerAdapter.notifyDataSetChanged();
        }
        viewPager.setOnTouchListener(this);
        viewPager.setCurrentItem(realIndex);
        handler.removeCallbacksAndMessages(null);
        if (loopTime > 0) {
            handler.sendEmptyMessageDelayed(CODE, loopTime);
        }
    }

    public void stop() {
        clear();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void getLoopChild(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof LoopDotsView) {
                LoopDotsView loopDotsView = (LoopDotsView) view;
                loopDotsView.setDotsLength(imgLength);
                loopDotsViews.add(loopDotsView);
            } else if (view instanceof LoopTitleView) {
                LoopTitleView loopTitleView = (LoopTitleView) view;
                loopTitleViews.add(loopTitleView);
            } else if (view instanceof ViewGroup) {
                getLoopChild((ViewGroup) view);
            }
        }
    }

    private T imgData;
    private T titleData;
    private int imgLength;
    private int titleLength;

    public LoopViewPager setImgData(T imgData) {
        this.imgData = imgData;
        this.imgLength = 0;
        if (imgData instanceof List) {
            this.imgLength = ((List) imgData).size();
        } else {
            if (imgData instanceof Integer[]) {
                this.imgLength = ((Integer[]) imgData).length;
            } else if (imgData instanceof String[]) {
                this.imgLength = ((String[]) imgData).length;
            }
        }
        if (imgLength > 0)
            return this;
        else {
            throw new IllegalArgumentException();
        }
    }

    public LoopViewPager setTitleData(T titleData) {
        this.titleData = titleData;
        this.titleLength = 0;
        if (titleData instanceof List) {
            this.titleLength = ((List) titleData).size();
        } else if (titleData instanceof String[]) {
            this.titleLength = ((String[]) titleData).length;
        }
        if (titleLength > 0) {
            if (imgData == null) {
                imgLength = titleLength;
            }
            return this;
        } else
            throw new IllegalArgumentException();
    }

    public void setImgLength(int imgLength) {
        this.imgLength = imgLength;
    }

    public void clear() {
        imgData = null;
        titleData = null;
        imgLength = 0;
        titleLength = 0;
    }

    /**
     * 条目创建监听
     */
    private OnCreateItemViewListener onCreateItemViewListener;

    public void setOnCreateItemViewListener(OnCreateItemViewListener onCreateItemViewListener) {
        this.onCreateItemViewListener = onCreateItemViewListener;
    }

    /**
     * 条目点击监听
     */
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
