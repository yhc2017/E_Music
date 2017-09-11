package com.anddle.music.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anddle.music.MusicItem;
import com.anddle.music.R;
import com.anddle.music.adapter.FragAdapter;
import com.anddle.music.fragment.FragmentFriend;
import com.anddle.music.fragment.FragmentMusic;
import com.anddle.music.fragment.fragmentmine.FragmentMine;
import com.anddle.music.handler.HandlerUtil;
import com.anddle.music.service.LockService;
import com.anddle.music.service.MusicService;
import com.anddle.music.uitl.Utils;
import com.anddle.music.widget.SplashScreen;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.anddle.music.fragment.fragmentmine.FragmentMine.uphotoIV;

public class MusicListActivity extends BaseActivity {

    private Button mPlayBtn;
    private Button mPreBtn;
    private Button mNextBtn;

    private Button user;

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

    //sanil*
//    public static ImageView uphotoIV, usexIV, ucodeIV;
//    public static TextView unameTV, uidTV;
    //sanil*

    //snail**1
//    public SharedPreferences pref;
//    public static SharedPreferences.Editor editor;
//    public static String returnedData;
    //snail**1

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
        user = (Button) findViewById(R.id.user_pic);

        mMusicTitle = (TextView) findViewById(R.id.music_title);

        mDurationTime = (TextView) findViewById(R.id.duration_time);
        mPlayedTime = (TextView) findViewById(R.id.played_time);
        mMusicSeekBar = (SeekBar) findViewById(R.id.seek_music);
        mMusicSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);

        Intent i = new Intent(this, MusicService.class);
        startService(i);
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);

        //开始锁屏service
        startService(new Intent(this, LockService.class));


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

    @Override
    protected void onStart() {
        super.onStart();
        //snail*
        sendRequestWithOkHttp("http://117.48.203.145/queryUserInfo.php", new okhttp3.Callback(){
            /**
             * Called when the request could not be executed due to cancellation, a connectivity problem or
             * timeout. Because networks can fail during an exchange, it is possible that the remote server
             * accepted the request before the failure.
             *
             * @param call
             * @param e
             */
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d("MusicListActivity","出现异常");
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MusicListActivity.this, "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            /**
             * Called when the HTTP response was successfully returned by the remote server. The callback may
             * proceed to read the response body with {@link Response#body}. The response is still live until
             * consume the response body on another thread.
             * <p>
             * <p>Note that transport-layer success (receiving a HTTP response code, headers and body) does
             * not necessarily indicate application-layer success: {@code response} may still indicate an
             * unhappy HTTP response code like 404 or 500.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseData = response.body().string(); //得到返回具体内容

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            parseJSONWithGson(responseData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });
        //snail*
        

//
//        setViewPager();
//        Button_Click();
//        settoolbar();

//        //3秒移开导图
//        HandlerUtil.getInstance(this).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                splashScreen.removeSplashScreen();
//            }
//        }, 3000);

//        Toast.makeText(MusicListActivity.this,"555",Toast.LENGTH_SHORT).show();

    }

    //snail*
    private void sendRequestWithOkHttp(String address, okhttp3.Callback callback) {

//        Intent intent = getIntent();
//        returnedData = intent.getStringExtra("userinfo");
//        Log.d("aa",returnedData);
//        String data = "111";

        //snail**3
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        boolean state = pref.getBoolean("registerState", false);
        String id = pref.getString("userid", "");
        Log.d("aa", "状态："+state);
        Log.d("aa", "登录id："+id);

//        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String data = null;
//        boolean state = pref.getBoolean("registerState", false);
        if (state) {
//            data = pref.getString("userid", "");
            data = id;
        } else {
//            data = returnedData;
            data = "222";
        }

        //snail**3

        Toast.makeText(MusicListActivity.this, data, Toast.LENGTH_SHORT).show();


        //*******
        OkHttpClient client = new OkHttpClient(); //创建 OkHttpClient实例
        RequestBody requestBody = new FormBody.Builder().add("userid", data).build();
        Request request = new Request.Builder().url("http://117.48.203.145/queryUserInfo.php").post(requestBody).build();
        client.newCall(request).enqueue(callback);
        //*******


    }
    protected void parseJSONWithGson(String jsonData) throws JSONException {

        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>()
        {}.getType());
        for (App app : appList) {

            final String url1 = app.getImg();
            final String url2 = app.getSex();
            final String url3 = app.getErweima();
            final String necheng = app.getName();
            final  String wechatID = app.getWechat();

            Log.d("MusicListActivity", "img" + url1);
            Log.d("MusicListActivity", "sex" + url2);
            Log.d("MusicListActivity", "erweima" + url3);
            Log.d("MusicListActivity", "necheng" + necheng);
            Log.d("MusicListActivity", "userid" + wechatID);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    FragmentMine.unameTV.setText(necheng);
                    FragmentMine.uidTV.setText(wechatID);

                    Glide.with(MusicListActivity.this).load(url1).into(uphotoIV);
                    Glide.with(MusicListActivity.this).load(url2).into(FragmentMine.usexIV);
                    Glide.with(MusicListActivity.this).load(url3).into(FragmentMine.ucodeIV);

                    FragmentMine.uphotoIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                Log.d("aa","bb");
                            Intent intent = new Intent(MusicListActivity.this, LoginActivity.class);
                            startActivityForResult(intent, 1);
                        }
                    });



                }
            });


        }

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    returnedData = data.getStringExtra("registerState");
//                    Log.d("MusicListActivity","777"+returnedData);
//                } else {
//                    returnedData = "111";
//                }
//                break;
//            default:
//                break;
//        }
//    }

    //snail*



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

    private ServiceConnection mServiceConnection = new ServiceConnection() {

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

//            case R.id.user_photo: {
//                Log.d("MusicListActivity", "bbb" + "cccc");
//            }
            default:
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
