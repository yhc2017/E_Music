package com.anddle.music.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddle.music.R;

/**
 * Created by HUAHUA on 2017/8/8.
 * 校园圈界面
 */

public class FragmentFriend extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        return view;

    }
}
