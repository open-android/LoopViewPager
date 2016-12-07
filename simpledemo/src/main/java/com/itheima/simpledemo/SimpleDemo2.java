package com.itheima.simpledemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itheima.loopviewpager.LoopViewPager;
import com.itheima.loopviewpager.anim.transformer.LoopTransformer;
import com.itheima.loopviewpager.anim.transformer.LoopVerticalTransformer;
import com.itheima.loopviewpager.listener.OnCreateItemViewListener;
import com.itheima.loopviewpager.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleDemo2 extends AppCompatActivity {

    private List<View> viewList;
    private LoopViewPager loopViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo2);
        loopViewPager = (LoopViewPager) findViewById(R.id.lvp_pager);
        initData();

        // 自定义View
        loopViewPager.setOnCreateItemViewListener(new OnCreateItemViewListener() {
            @Override
            public View getItemView(int position) {
                return viewList.get(position);
            }
        });
        //处理点击事件
        loopViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SimpleDemo2.this, "position=" + position, Toast.LENGTH_SHORT).show();
            }
        });
        // 自定义动画
        loopViewPager.setPageTransformer(1500, new LoopVerticalTransformer() {
            @Override
            public void transformViewPage(View view, float position) {
                view.setPivotX(view.getMeasuredWidth() * 0.5f);
                view.setPivotY(position <= 0 ? view.getMeasuredHeight() : 0);
                view.setRotationX(-90f * position);
            }
        });
        loopViewPager.setImgLength(viewList.size());
//        loopViewPager.setTitleData(DataFactory.titleListString());
        loopViewPager.start();
    }

    private void initData() {
        viewList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            View view = null;
            if (i == 2) {
                ImageView imageView = new ImageView(this);
                Glide.with(SimpleDemo2.this).load("http://pic.58pic.com/58pic/13/72/07/55Z58PICKka_1024.jpg").centerCrop().into(imageView);
                view = imageView;
            } else if (i == 4) {
                ImageView imageView = new ImageView(this);
                Glide.with(SimpleDemo2.this).load("http://www.bz55.com/uploads/allimg/120615/1-120615140A8.jpg").centerCrop().into(imageView);
                view = imageView;
            } else {
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLUE);
                textView.setTextSize(36);
                textView.setText("TextView " + i);
                textView.setBackgroundColor(Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
                view = textView;
            }
            viewList.add(view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loopViewPager.stop();
    }

}
