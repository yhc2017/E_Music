package com.anddle.music.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by HUAHUA on 2017/8/8.
 */

public class FragAdapter extends FragmentPagerAdapter{
    private List<android.support.v4.app.Fragment> mFragments;

    public FragAdapter(FragmentManager fm, List<android.support.v4.app.Fragment> fragments){
        super(fm);
        mFragments = fragments;
    }
    @Override
    public android.support.v4.app.Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }
}
