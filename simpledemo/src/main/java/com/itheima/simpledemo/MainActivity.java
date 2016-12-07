package com.itheima.simpledemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Class[] classes = {SimpleDemo1.class, SimpleDemo2.class};
    private String[] names = {"1、如何使用", "2、如何自定义"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.lv_list);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return classes.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView textView = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
                textView.setText(names[i]);
                return textView;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MainActivity.this, classes[i]));
            }
        });
    }

}
