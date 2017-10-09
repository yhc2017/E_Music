package com.anddle.music.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.anddle.music.MusicItem;
import com.anddle.music.R;
import com.anddle.music.service.MusicService;

public class MyLockScreenActivity extends AppCompatActivity {

    private Button mPlayBtn;
    private Button mPreBtn;
    private Button mNextBtn;
    private TextView mMusicTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.lock_view);

        Intent i = new Intent(this, MusicService.class);
        startService(i);
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);

        mPlayBtn = (Button) findViewById(R.id.play_btn3);
        mPreBtn = (Button) findViewById(R.id.pre_btn3);
        mNextBtn = (Button) findViewById(R.id.next_btn3);
        mMusicTitle = (TextView) findViewById(R.id.music_name);

    }

    private MusicService.MusicServiceIBinder mMusicService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mMusicService = (MusicService.MusicServiceIBinder) service;
//            mMusicService.registerOnStateChangeListener(mStateChangeListenr);

            MusicItem item = mMusicService.getCurrentMusic();
            if(item == null) {
//                enableControlPanel(false);
                return;
            }
            else {
                updatePlayingInfo(item);
            }
            if(mMusicService.isPlaying()) {
                mPlayBtn.setBackgroundResource(R.mipmap.ic_pause);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void updatePlayingInfo(MusicItem item) {
        mMusicTitle.setText(item.name);
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.play_btn: {
                if(mMusicService != null) {
                    if(!mMusicService.isPlaying()) {
                        mMusicService.play();
                    }
                    else {
                        mMusicService.pause();
                    }
                }
            }
            break;

            case R.id.next_btn: {
                if(mMusicService != null) {
                    mMusicService.playNext();
                }
            }
            break;

            case R.id.pre_btn: {
                if(mMusicService != null) {
                    mMusicService.playPre();
                }
            }
            break;
        }
    }
}
