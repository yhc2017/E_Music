package com.anddle.music.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anddle.music.activity.MusicListView;
import com.anddle.music.R;

/**
 * Created by HUAHUA on 2017/8/8.
 * 发现音乐界面
 */

public class FragmentMusic extends Fragment{

    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_music, container, false);
        button = (Button) view.findViewById(R.id.music_card);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , MusicListView.class);
                startActivity(intent);
            }
        });
        return view;

    }

}
