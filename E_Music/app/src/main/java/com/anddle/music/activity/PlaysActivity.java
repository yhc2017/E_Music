package com.anddle.music.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anddle.music.MusicItem;
import com.anddle.music.R;
import com.anddle.music.service.MusicService;

import java.util.List;

/*
    播放历史 plays
 */
public class PlaysActivity extends AppCompatActivity {

    private MusicService.MusicServiceIBinder mMusicService;
    private List<MusicItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plays);
        List<MusicItem> playList = mMusicService.getPlayList();

    }



}
