package com.anddle.music.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.anddle.music.MusicItem;
import com.anddle.music.R;
import com.anddle.music.adapter.FragAdapter;
import com.anddle.music.fragment.FragmentFriend;
import com.anddle.music.fragment.FragmentMusic;
import com.anddle.music.fragment.fragmentmine.FragmentMine;
import com.anddle.music.handler.HandlerUtil;
import com.anddle.music.service.MusicService;
import com.anddle.music.uitl.Utils;
import com.anddle.music.widget.SplashScreen;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends BaseActivity {

    private Button mPlayBtn;
    private Button mPreBtn;
    private Button mNextBtn;
    private TextView mMusicTitle;
    private TextView mPlayedTime;
    private TextView mDurationTime;
    private SeekBar mMusicSeekBar;
    private ImageView mImageView;

    NavigationView navView;
    //导航栏
    private Toolbar mToolbar;
    private ImageView barMore,barSearch, barMine, barMusic, barFriend;
    private android.widget.PopupWindow mPopupWindow;
    private SplashScreen splashScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        splashScreen = new SplashScreen(this);
        splashScreen.show(R.drawable.loading, SplashScreen.SLIDE_LEFT);//一进入APP先是一幅图片（引导图片）

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        navView = (NavigationView) findViewById(R.id.nav_view);

        mPlayBtn = (Button) findViewById(R.id.play_btn);
        mPreBtn = (Button) findViewById(R.id.pre_btn);
        mNextBtn = (Button) findViewById(R.id.next_btn);

        mMusicTitle = (TextView) findViewById(R.id.music_title);

        mDurationTime = (TextView) findViewById(R.id.duration_time);
        mPlayedTime = (TextView) findViewById(R.id.played_time);
        mMusicSeekBar = (SeekBar) findViewById(R.id.seek_music);
        mMusicSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);

        Intent i = new Intent(this, MusicService.class);
        startService(i);
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);


        setViewPager();
        Button_Click();
        settoolbar();

        //3秒移开导图
        HandlerUtil.getInstance(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                splashScreen.removeSplashScreen();
            }
        }, 3000);
    }

    /*设置bar*/
    public void settoolbar() {
        //实例化控件
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);
        barSearch = (ImageView) findViewById(R.id.bar_search);
        barMine = (ImageView) findViewById(R.id.bar_mine);
        barMusic = (ImageView) findViewById(R.id.bar_music);
        barFriend = (ImageView) findViewById(R.id.bar_friends);
        barMore = (ImageView) findViewById(R.id.bar_more);

        //实例化对象
        FragmentMine fragmentMine = new FragmentMine();
        FragmentMusic fragmentMusic = new FragmentMusic();
        FragmentFriend fragmentFriend = new FragmentFriend();

        List<android.support.v4.app.Fragment> fragments = new ArrayList<android.support.v4.app.Fragment>();
        fragments.add(fragmentMine);
        fragments.add(fragmentMusic);
        fragments.add(fragmentFriend);
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);

        //
        final ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        viewPager.setOffscreenPageLimit(2);//设置页面左右两边加载最大页数
        viewPager.setAdapter(adapter);

        barMusic = (ImageView) findViewById(R.id.bar_music);
        barMusic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewPager.setCurrentItem(1);
            }
        });

        barMine = (ImageView) findViewById(R.id.bar_mine);
        barMine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewPager.setCurrentItem(0);
            }
        });

        barFriend = (ImageView) findViewById(R.id.bar_friends);
        barFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewPager.setCurrentItem(2);
            }
        });
    }


    /*设置页面*/
    public void setViewPager() {

    }

    //按钮事件
    public void Button_Click(){
        //点专辑图进入播放界面
        mImageView = (ImageView) findViewById(R.id.image_thumb);
        mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MusicListActivity.this, PlayMusicView.class);
                startActivity(intent);
            }

        });

        ImageView bar_more = (ImageView) findViewById(R.id.bar_more);
        bar_more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPlayList();
            }

        });

        //侧边栏事件
        //首选   navView.setCheckedItem(R.id.add_friends);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_friends: {
                        //添加好友
                    }
                    break;
                    case R.id.send_news: {
                        //发布动态
                    }
                    break;
                    case R.id.listening: {
                        //听歌识曲
                    }
                    break;
                    case R.id.time_close: {
                        //定时关闭
                    }
                    break;
                    case R.id.back_news: {
                        //反馈
                    }
                    break;
                    case R.id.back_0: {
                        new AlertDialog.Builder(MusicListActivity.this).setTitle("系统提示")//设置对话框标题

                                .setMessage("是否退出！")//设置显示的内容

                                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮

                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//退出
                                        Intent stopIntent = new Intent(MusicListActivity.this, MusicService.class);
                                        stopService(stopIntent);
                                        System.exit(0);
                                    }

                                }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮

                            @Override
                            public void onClick(DialogInterface dialog, int which) {//响应事件

                            }
                        }).show();//在按键响应事件中显示此对话框

                }
                    break;
                }
                return true;
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if(mMusicUpdateTask != null && mMusicUpdateTask.getStatus() == AsyncTask.Status.RUNNING) {
//            mMusicUpdateTask.cancel(true);
//        }
//
//        mMusicUpdateTask = null;
//        mMusicService.unregisterOnStateChangeListener(mStateChangeListenr);
//        unbindService(mServiceConnection);
//
//        for(MusicItem item : mMusicList) {
//            if( item.thumb != null ) {
//                item.thumb.recycle();
//                item.thumb = null;
//            }
//        }
//        mMusicList.clear();
    }

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            if(mMusicService != null) {
                mMusicService.seekTo(seekBar.getProgress());
            }
        }
    };


    private void enableControlPanel(boolean enabled) {
        mPlayBtn.setEnabled(enabled);
        mPreBtn.setEnabled(enabled);
        mNextBtn.setEnabled(enabled);
        mMusicSeekBar.setEnabled(enabled);
    }

    //更新内容
    private void updatePlayingInfo(MusicItem item) {
        String times = Utils.convertMSecendToTime(item.duration);
        mDurationTime.setText(times);

        times = Utils.convertMSecendToTime(item.playedTime);
        mPlayedTime.setText(times);

        mMusicSeekBar.setMax((int) item.duration);
        mMusicSeekBar.setProgress((int) item.playedTime);

        mMusicTitle.setText(item.name);
        if(item.thumb != null)
            mImageView.setImageBitmap(item.thumb);
        else mImageView.setImageResource(R.mipmap.default_cover);
    }

    private MusicService.OnStateChangeListenr mStateChangeListenr = new MusicService.OnStateChangeListenr() {

        @Override
        public void onPlayProgressChange(MusicItem item) {

            updatePlayingInfo(item);
        }

        @Override
        public void onPlay(MusicItem item) {
            mPlayBtn.setBackgroundResource(R.mipmap.ic_pause);
            updatePlayingInfo(item);
            enableControlPanel(true);
        }

        @Override
        public void onPause(MusicItem item) {
            mPlayBtn.setBackgroundResource(R.mipmap.ic_play);
            enableControlPanel(true);
        }
    };



    private MusicService.MusicServiceIBinder mMusicService;

    private ServiceConnection mServiceConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mMusicService = (MusicService.MusicServiceIBinder) service;
            mMusicService.registerOnStateChangeListener(mStateChangeListenr);

            MusicItem item = mMusicService.getCurrentMusic();
            if(item == null) {
                enableControlPanel(false);
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

    private void showPlayList() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_playlist);
        builder.setTitle(R.string.play_list);

        List<MusicItem> playList = mMusicService.getPlayList();
        ArrayList<String> data = new ArrayList<String>();
        for(MusicItem music : playList) {
            data.add(music.name);
        }
        if(data.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
            builder.setAdapter(adapter, null);
        }
        else {
            builder.setMessage(getString(R.string.no_song));
        }

        builder.setCancelable(true);
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.play_list_menu: {
//
//                showPlayList();
//
// }
//            break;
//
//        }
//
//        return true;
//    }
}
