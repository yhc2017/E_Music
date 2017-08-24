package com.anddle.music.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anddle.music.activity.MusicListView;
import com.anddle.music.R;
import com.anddle.music.activity.RegisterActivity;

/**
 * Created by HUAHUA on 2017/8/8.
 * 发现音乐界面
 */

public class FragmentMusic extends Fragment{


    private View view;

    private Button button,button1;

    public Activity mContext;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragemnt_music, container, false);
        //本地音乐页面
        button = (Button) view.findViewById(R.id.music_card);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , MusicListView.class);
                mContext.startActivity(intent);
            }
        });

        //测试页面效果
        button1 = (Button) view.findViewById(R.id.ceshi);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                mContext.startActivity(intent);
            }
        });

        return view;

    }

}
