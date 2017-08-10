package com.anddle.music.activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.anddle.music.MusicItem;
import com.anddle.music.service.MusicService;
import com.anddle.music.R;
import com.anddle.music.adapter.MusicItemAdapter;
import com.anddle.music.uitl.Utils;

import java.util.ArrayList;
import java.util.List;


public class MusicListView extends AppCompatActivity {

    static public String TAG = "MusicListView";
    private List<MusicItem> mMusicList;
    private ListView mMusicListView;
    private Button mPlayBtn;
    private Button mPreBtn;
    private Button mNextBtn;
    private TextView mMusicTitle;
    private TextView mPlayedTime;
    private TextView mDurationTime;
    private SeekBar mMusicSeekBar;
    private ImageView mImageView;
    private MusicUpdateTask mMusicUpdateTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_view);

        mMusicList = new ArrayList<MusicItem>();
        mMusicListView = (ListView) findViewById(R.id.music_list);
        MusicItemAdapter adapter = new MusicItemAdapter(this, R.layout.music_item, mMusicList);
        mMusicListView.setAdapter(adapter);

        mPlayBtn = (Button) findViewById(R.id.play_btn);
        mPreBtn = (Button) findViewById(R.id.pre_btn);
        mNextBtn = (Button) findViewById(R.id.next_btn);

        mMusicTitle = (TextView) findViewById(R.id.music_title);

        mDurationTime = (TextView) findViewById(R.id.duration_time);
        mPlayedTime = (TextView) findViewById(R.id.played_time);
        mMusicSeekBar = (SeekBar) findViewById(R.id.seek_music);
        mMusicSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);

        //设置监听器器
        mMusicListView.setOnItemClickListener(mOnMusicItemClickListener);
    }

//更新信息
    private class MusicUpdateTask extends AsyncTask<Object, MusicItem, Void> {

        List<MusicItem> mDataList = new ArrayList<MusicItem>();

        @Override
        protected Void doInBackground(Object... params) {
            //取出信息
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            //信息
            String[] searchKey = new String[] {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Albums.ALBUM_ID,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.DURATION
            };

            String where = MediaStore.Audio.Media.DATA + " like \"%"+getString(R.string.search_path)+"%\"";
            String [] keywords = null;
            String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

            ContentResolver resolver = getContentResolver();
            Cursor cursor = resolver.query(uri, searchKey, where, keywords, sortOrder);

            if(cursor != null)
            {
                while(cursor.moveToNext() && ! isCancelled())
                {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    Uri musicUri = Uri.withAppendedPath(uri, id);

                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)) +"\n 歌手："+ cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));


                    int albumId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ID));
                    Uri albumUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
                    MusicItem data = new MusicItem(musicUri, albumUri, name, duration, 0);
                    if (uri != null) {
                        ContentResolver res = getContentResolver();
                        data.thumb = Utils.createThumbFromUir(res, albumUri);
                    }

//                    Log.d(TAG, "real music found: " + path);

                    publishProgress(data);

                }

                cursor.close();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(MusicItem... values) {

            MusicItem data = values[0];

            mMusicList.add(data);
            MusicItemAdapter adapter = (MusicItemAdapter) mMusicListView.getAdapter();
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mMusicUpdateTask != null && mMusicUpdateTask.getStatus() == AsyncTask.Status.RUNNING){
            mMusicUpdateTask.cancel(true);
        }
        mMusicUpdateTask = null;
        //⼿手动回收使用的图片资源
         for(MusicItem item : mMusicList) {
            if( item.thumb != null ) {
                item.thumb.recycle();
                item.thumb = null;       }
        }
        mMusicList.clear();
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

    //更新内容
    private void updatePlayingInfo(MusicItem item) {
        String times = Utils.convertMSecendToTime(item.duration);
        mDurationTime.setText(times);

        times = Utils.convertMSecendToTime(item.playedTime);
        mPlayedTime.setText(times);

        mMusicSeekBar.setMax((int) item.duration);
        mMusicSeekBar.setProgress((int) item.playedTime);

        mMusicTitle.setText(item.name);
        mImageView.setImageBitmap(item.thumb);
    }

    private void enableControlPanel(boolean enabled) {
        mPlayBtn.setEnabled(enabled);
        mPreBtn.setEnabled(enabled);
        mNextBtn.setEnabled(enabled);
        mMusicSeekBar.setEnabled(enabled);
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
    //定义监听器器
    private AdapterView.OnItemClickListener mOnMusicItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //添加播放⾳音乐的代码
//            MusicItem item = mMusicList.get(position);
//            MediaPlayer mMusicPlayer  = new MediaPlayer();
//            try {
//                mMusicPlayer.reset();
//                mMusicPlayer.setDataSource(MusicListView.this, item.songUri);
//                mMusicPlayer.prepare();
//                mMusicPlayer.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            if(mMusicService != null) {
                mMusicService.addPlayList(mMusicList.get(position));
            }
        }
    };

}
