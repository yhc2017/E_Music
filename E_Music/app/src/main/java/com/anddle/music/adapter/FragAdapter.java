package com.anddle.music.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by HUAHUA on 2017/8/8.
 */

public class FragAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private  List<String> mFragmentTitles;

    //构造方法一
    public FragAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        mFragments = fragments;
    }

    //构造方法二
    public FragAdapter(FragmentManager fm, List<Fragment> fragments, List<String> fragmentTitles){
        super(fm);
        mFragments = fragments;
        mFragmentTitles = fragmentTitles;
    }
//
//    //构造方法一
//    public void addFragment(Fragment fragment) {
//        mFragments.add(fragment);
//    }
//

//    //构造方法二
//    public void addFragment(Fragment fragment, String title) {
//        mFragments.add(fragment);
//        mFragmentTitles.add(title);
//    }

    @Override
    public android.support.v4.app.Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }


}
