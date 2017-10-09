package com.anddle.music.fragment.fragmentmine;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddle.music.R;
import com.anddle.music.adapter.timeline.TimeLineAdapter;
import com.anddle.music.model.TimeLineModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUAHUA on 2017/8/11.
 */

public class MusicCourseFragment extends Fragment {
    private View view;
    private RecyclerView mRecycler;
    public Activity mContext;
    private LinearLayoutManager layoutManager;
    private List<TimeLineModel> models = new ArrayList<>();
    TimeLineAdapter adapter;


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_musiccourse, container, false);
        initview();
        return view;
    }

    private void initview() {
        mRecycler = (RecyclerView) view.findViewById(R.id.time_line_recycler);
        layoutManager = new LinearLayoutManager(mContext);
        adapter = new TimeLineAdapter(getData());//传数据
        //做判断，避免空指针
        if (mRecycler != null ){
            mRecycler.setLayoutManager(layoutManager);//绑定布局
            mRecycler.setAdapter(adapter);//绑定适配器
        }




    }

    private List<TimeLineModel> getData() {

        models.add(new TimeLineModel(R.mipmap.china,"2017年9月3日"));
        models.add(new TimeLineModel(R.mipmap.china,"9月3日"));
        models.add(new TimeLineModel(R.mipmap.china,"2017年9月3日"));
        models.add(new TimeLineModel(R.mipmap.china,"9月3日"));
        models.add(new TimeLineModel(R.mipmap.china,"2017年9月3日"));
        models.add(new TimeLineModel(R.mipmap.china,"9月3日"));



        return models;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
