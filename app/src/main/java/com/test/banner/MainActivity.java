package com.test.banner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.banner.demo.BannerAnimationActivity;
import com.test.banner.demo.BannerLocalActivity;
import com.test.banner.demo.BannerStyleActivity;
import com.test.banner.demo.CustomBannerActivity;
import com.test.banner.demo.CustomViewPagerActivity;
import com.test.banner.demo.IndicatorPositionActivity;
import com.test.banner.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, OnBannerListener {
    static final int REFRESH_COMPLETE = 0X1112;
    SuperSwipeRefreshLayout mSwipeLayout;
    ListView listView;
    Banner banner;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    String[] urls = getResources().getStringArray(R.array.url4);
                    List list = Arrays.asList(urls);
                    List arrayList = new ArrayList(list);
                    banner.update(arrayList);
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.list);
        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        banner = new Banner(this);
        banner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                App.H / 4));
        listView.addHeaderView(banner);

        String[] data = getResources().getStringArray(R.array.demo_list);
        listView.setAdapter(new SampleAdapter(this, data));
        listView.setOnItemClickListener(this);

        GradientDrawable selectedDrawable = new GradientDrawable();
        selectedDrawable.setGradientType(GradientDrawable.OVAL);
        selectedDrawable.setColor(Color.parseColor("#FF444A"));
        selectedDrawable.setCornerRadius(30);

        GradientDrawable unSelectedDrawable = new GradientDrawable();
        unSelectedDrawable.setGradientType(GradientDrawable.OVAL);
        unSelectedDrawable.setColor(Color.parseColor("#FFf000"));
        unSelectedDrawable.setCornerRadius(30);
        banner.setIndicatorDrawable(selectedDrawable, unSelectedDrawable, 30, 30);
////        简单使用
//        banner.setImages(App.images).setImageLoader(new GlideImageLoader()).setOnBannerListener
//                (this).start();

        List<View> views = new ArrayList<>();
        String[] urls = getResources().getStringArray(R.array.url);
        List list = Arrays.asList(urls);
        List<String> images = new ArrayList(list);
        for (int i = 0; i < 2; i++) {
            ImageView tv = new ImageView(this);
            tv.setScaleType(ImageView.ScaleType.FIT_XY);
            FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(-1,-1);
            tv.setLayoutParams(lp);

            Glide.with(this).load(images.get(i)).into(tv);
            views.add(tv);
        }

        banner.setViews(views);

    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
    }


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }


    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                startActivity(new Intent(this, BannerAnimationActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, BannerStyleActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, IndicatorPositionActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, CustomBannerActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, BannerLocalActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, CustomViewPagerActivity.class));
                break;
        }
    }


}
