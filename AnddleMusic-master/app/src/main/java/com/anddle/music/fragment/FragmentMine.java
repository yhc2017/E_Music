package com.anddle.music.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddle.music.R;

/**
 * Created by HUAHUA on 2017/8/7.
 * 我的主界面
 */

public class FragmentMine extends Fragment {

    private RecyclerView recyclerView;
    private TabLayout userTabs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        initViews();


        return view;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //引入页面资源
    public void initViews(){
//        userTabs= (TabLayout) findViewById(R.id.userTabs);
//
//        userTabs.addTab(userTabs.newTab().setText("我的音乐"));
//
//        userTabs.addTab(userTabs.newTab().setText("音乐历程"));
//
//        userTabs.addTab(userTabs.newTab().setText("消息"));

    }

    //事件处理






}
