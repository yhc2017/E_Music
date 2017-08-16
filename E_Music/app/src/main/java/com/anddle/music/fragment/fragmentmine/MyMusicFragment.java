package com.anddle.music.fragment.fragmentmine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anddle.music.R;
import com.anddle.music.activity.MusicListView;
import com.anddle.music.adapter.MyMusicItem;
import com.anddle.music.adapter.MyMusicListAdapter;
import com.anddle.music.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUAHUA on 2017/8/11.
 * 我的音乐界面
 */

public class MyMusicFragment extends Fragment {

//    private View view;
//    private Button button;

    private View view;
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

//        button = (Button) view.findViewById(R.id.local_music);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity() , MusicListView.class);
//                startActivity(intent);
//            }
//        });
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
