package com.anddle.music.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anddle.music.activity.MusicListView;
import com.anddle.music.R;
import com.anddle.music.activity.RegisterActivity;
import com.anddle.music.activity.UserMessageFirstSettingActvity;
import com.anddle.music.dialog.InputDialog;

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
                Intent intent = new Intent(mContext, UserMessageFirstSettingActvity.class);
                mContext.startActivity(intent);

//                InputDialog inputName = new InputDialog(mContext);
//                inputName.setTips("昵称");
//                inputName.setSingle(true).setOnClickBottomListener(new InputDialog.OnClickBottomListener() {
//                    @Override
//                    public void onYesClick() {
//                        Toast.makeText(mContext,"点击了确认按钮",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNoClick() {
//                        Toast.makeText(mContext,"点击了取消按钮",Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

        RelativeLayout ceshiLayout = (RelativeLayout) view.findViewById(R.id.ceshilayout);
        ceshiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"选择图片",Toast.LENGTH_SHORT).show();

            }
        });
        return view;

    }

}
