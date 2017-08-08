package com.anddle.music;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class MusicListView extends AppCompatActivity {

    static public String TAG = "MuiscListActivity";
    private List<MusicItem> mMusicList;
    private ListView mMusicListView;
    private MusicUpdateTask mMusicUpdateTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_view);

        mMusicList = new ArrayList<MusicItem>();
        mMusicListView = (ListView) findViewById(R.id.music_list);
        MusicItemAdapter adapter = new MusicItemAdapter(this, R.layout.music_item, mMusicList);
        mMusicListView.setAdapter(adapter);

        mMusicUpdateTask = new MusicUpdateTask();
        mMusicUpdateTask.execute();

        //设置监听器器
        mMusicListView.setOnItemClickListener(mOnMusicItemClickListener);
    }


    private class MusicUpdateTask extends AsyncTask<Object, MusicItem, Void> {

        List<MusicItem> mDataList = new ArrayList<MusicItem>();

        @Override
        protected Void doInBackground(Object... params) {

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

                    Log.d(TAG, "real music found: " + path);

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
        //⼿手动回收使⽤用的图⽚片资源
         for(MusicItem item : mMusicList) {
            if( item.thumb != null ) {
                item.thumb.recycle();
                item.thumb = null;       }
        }
        mMusicList.clear();
    }

    //定义监听器器
    private AdapterView.OnItemClickListener mOnMusicItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //添加播放⾳音乐的代码
            MusicItem item = mMusicList.get(position);
            MediaPlayer mMusicPlayer  = new MediaPlayer();
            try {
                mMusicPlayer.reset();
                mMusicPlayer.setDataSource(MusicListView.this, item.songUri);
                mMusicPlayer.prepare();
                mMusicPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

}
