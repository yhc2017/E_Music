package com.anddle.music.adapter.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddle.music.R;
import com.anddle.music.model.TimeLineModel;

import java.util.List;

/**
 * Created by HUAHUA on 2017/9/2.
 * 时间线的适配器
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder>{
    private List<TimeLineModel> mDateSet;

    //构造方法
    public TimeLineAdapter(List<TimeLineModel>models){
        mDateSet = models;
    }

    //按照类型选择不同的布局
    @Override
    public int getItemViewType(int position) {
        final int size = mDateSet.size() -1;
        if (size == 0)
            return ItemType.ATOM;
        else if (position == 0)
            return ItemType.START;
        else if (position == size)
            return ItemType.END;
        else return ItemType.NORMAL;
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       //创建新的视图
        View  v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.timekine_item_layout,viewGroup,false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder timeLineViewHolder, int i) {

        timeLineViewHolder.setData(mDateSet.get(i));

    }

    @Override
    public int getItemCount() {
        return mDateSet.size();
    }
}
