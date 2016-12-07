package com.itheima.simpledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itheima.loopviewpager.LoopViewPager;

public class SimpleDemo1 extends AppCompatActivity {

    private LoopViewPager loopViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo1);
        loopViewPager = (LoopViewPager) findViewById(R.id.lvp_pager);
        loopViewPager.setImgData(DataFactory.imgListString());
        loopViewPager.setTitleData(DataFactory.titleListString());
        loopViewPager.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loopViewPager.stop();
    }

}
