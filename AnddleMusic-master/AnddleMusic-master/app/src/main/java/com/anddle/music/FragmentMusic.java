package com.anddle.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by HUAHUA on 2017/8/8.
 * 发现音乐界面
 */

public class FragmentMusic extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_music, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) getActivity().findViewById(R.id.music_card);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    // 实例化Intent
                    Intent it = new Intent();
                    //设置Intent的Action属性
                    it.setAction("com.anddle.music.MY_ACTION");
                    // 启动Activity
                    startActivity(it);

                }
            });
        }
}
