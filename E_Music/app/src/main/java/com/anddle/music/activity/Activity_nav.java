package com.anddle.music.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anddle.music.adapter.FragAdapter;
import com.anddle.music.R;
import com.anddle.music.fragment.FragmentFriend;
import com.anddle.music.fragment.FragmentMusic;
import com.anddle.music.fragment.fragmentmine.FragmentMine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUAHUA on 2017/8/7.
 * 导航界面
 */

public class Activity_nav extends FragmentActivity {

    //导航栏
    private Toolbar toolbar;
    private ImageView barMore,barSearch, barMine, barMusic, barFriend;
//    private TextView
    private ArrayList<TextView> mainTabs = new ArrayList<>();
    private ViewPager mViewPager;

    /*执行事件*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        settoolbar();
        setViewPager();
        setEvents();
    }

    /*设置bar*/
    public void settoolbar() {
        //实例化控件
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);
        barSearch = (ImageView) findViewById(R.id.bar_search);
        barMine = (ImageView) findViewById(R.id.bar_mine);
        barMusic = (ImageView) findViewById(R.id.bar_music);
        barFriend = (ImageView) findViewById(R.id.bar_friends);
        barMore = (ImageView) findViewById(R.id.bar_more);

    }

    /*设置页面*/
    public void setViewPager() {

        //实例化对象
        FragmentMine fragmentMine = new FragmentMine();
        FragmentMusic fragmentMusic = new FragmentMusic();
        FragmentFriend fragmentFriend = new FragmentFriend();

        List<android.support.v4.app.Fragment> fragments = new ArrayList<android.support.v4.app.Fragment>();
        fragments.add(fragmentMine);
        fragments.add(fragmentMusic);
        fragments.add(fragmentFriend);
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);

        //
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        viewPager.setAdapter(adapter);
    }
    /*事件处理*/
    public void setEvents() {
        barMusic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
    }
}
