package com.anddle.music.fragment.fragmentmine;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anddle.music.MusicItem;
import com.anddle.music.R;
import com.anddle.music.adapter.MyMusicItem;
import com.anddle.music.adapter.MyMusicListAdapter;
import com.anddle.music.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

/**
 * Created by HUAHUA on 2017/8/11.
 * 我的音乐界面
 */

public class MyMusicFragment extends Fragment {

    private View view;
<<<<<<< HEAD
    private List<MusicItem> mMusicList;

    private ListView mMusicListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_mymusic, container, false);

=======
    private List<MyMusicItem> myMusicList = new ArrayList<>();

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    public Activity mContext;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.mContext = activity;
    }

    /*获得实例，创建对象，并且把这个加到滚动栏当中*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_mymusic, container, false);
        initMyMusicList();
>>>>>>> 8c95a2d8a064ff88cf73de5cae20723f1d8e1489
        return view;
    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void initMyMusicList(){

        //添加布局
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(mContext);//线性布局管理器
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        MyMusicListAdapter adapter = new MyMusicListAdapter(myMusicList);
        recyclerView.setAdapter(adapter);//绑定适配器
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));//设置分割线

        //添加测试数据
        MyMusicItem localMusic = new MyMusicItem(R.drawable.music_local,"本地音乐",112);
        myMusicList.add(localMusic);
        MyMusicItem recentPlayer = new MyMusicItem(R.drawable.music_recent,"最近播放",112);
        myMusicList.add(recentPlayer);
        MyMusicItem dowmManager = new MyMusicItem(R.drawable.music_dld,"下载管理",112);
        myMusicList.add(dowmManager);
        MyMusicItem localMusic1 = new MyMusicItem(R.drawable.music_artist,"我的收藏",112);
        myMusicList.add(localMusic1);

    }
}
