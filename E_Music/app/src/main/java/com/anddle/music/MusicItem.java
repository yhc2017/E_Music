package com.anddle.music;

import android.graphics.Bitmap;
import android.net.Uri;

public class MusicItem {

    public String name;
    public String player;
    public Uri songUri;
    public Uri albumUri;
    public Bitmap thumb;
    public long duration;
    public long playedTime;

    public MusicItem(Uri songUri, Uri albumUri, String strName, String strPlayer, long duration, long playedTime) {
        this.name = strName;
        this.player = strPlayer;
        this.songUri = songUri;
        this.duration = duration;
        this.playedTime = playedTime;
        this.albumUri = albumUri;
    }

    @Override
    public boolean equals(Object o) {
        MusicItem another = (MusicItem) o;

        return another.songUri.equals(this.songUri);
    }
}