package com.anddle.music.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.anddle.music.MusicItem;
import com.anddle.music.R;
import com.anddle.music.adapter.MusicItemAdapter;
import com.anddle.music.service.MusicService;

import java.util.List;

/*
    播放历史 plays
 */
public class PlaysActivity extends AppCompatActivity {

    private MusicService.MusicServiceIBinder mMusicService;
    private ListView PlaysListView;
    private List<MusicItem> PlaysList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plays);

        PlaysListView = (ListView) findViewById(R.id.plays);


    }


    public void GetPlays(){
        List<MusicItem> playList = mMusicService.getPlayList();
        MusicItem data;
        for(MusicItem music : playList) {
            data = new MusicItem(music.songUri, music.albumUri, music.name, music.player, music.duration, 0);
            PlaysList.add(data);
        }
        MusicItemAdapter adapter = (MusicItemAdapter) PlaysListView.getAdapter();

    }


}
