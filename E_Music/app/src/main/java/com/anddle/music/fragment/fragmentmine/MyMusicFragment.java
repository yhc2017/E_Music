package com.anddle.music.fragment.fragmentmine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anddle.music.MusicItem;
import com.anddle.music.R;

import java.util.List;

/**
 * Created by HUAHUA on 2017/8/11.
 */

public class MyMusicFragment extends Fragment {
    private View view;
    private List<MusicItem> mMusicList;

    private ListView mMusicListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_mymusic, container, false);

        return view;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
