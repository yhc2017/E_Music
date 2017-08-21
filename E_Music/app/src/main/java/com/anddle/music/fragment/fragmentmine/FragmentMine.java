package com.anddle.music.fragment.fragmentmine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddle.music.R;
import com.anddle.music.adapter.FragAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUAHUA on 2017/8/7.
 * 我的主界面
 */

public class FragmentMine extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private TabLayout userTabs;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initViews();
        return view;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //引入页面资源
    public void initViews(){
//        ScrollView scrollView= (ScrollView) view.findViewById(R.id.scrollView);
        //用户名片

        //使用适配器将ViewPager与Fragment绑定在一起
        //title
        List<String> fragmentTitles = new ArrayList<>();
        fragmentTitles.add("我的音乐");
        fragmentTitles.add("音乐历程");
        fragmentTitles.add("消息");

        //viewpager
        MyMusicFragment myMusicFragment = new MyMusicFragment();
        MusicCourseFragment musicCourseFragment = new MusicCourseFragment();
        NewsFragment newsFragment = new NewsFragment();

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(myMusicFragment);
        fragments.add(musicCourseFragment);
        fragments.add(newsFragment);

        //使用构造方法二
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), fragments,fragmentTitles);

        viewPager = (ViewPager) view.findViewById(R.id.tab);
//        if (viewPager != null) {
//            setupViewPager(viewPager);
            viewPager.setOffscreenPageLimit(2);//设置页面左右两边加载最大页数
//        }
        viewPager.setAdapter(adapter);

        //TabLayout
        userTabs = (TabLayout) view.findViewById(R.id.userTabs);
        userTabs.setTabMode(TabLayout.MODE_FIXED);
        //将TabLayout与ViewPager绑定在一起
        userTabs.setupWithViewPager(viewPager);//可能有坑





    }

//    //设置页面方法
//    private void setupViewPager(ViewPager viewPager) {
//
//    }

    //事件处理






}
