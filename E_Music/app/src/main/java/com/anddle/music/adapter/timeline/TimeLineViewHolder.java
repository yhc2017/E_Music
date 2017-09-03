package com.anddle.music.adapter.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anddle.music.R;
import com.anddle.music.model.TimeLineModel;
import com.anddle.music.widget.TimeLineMarker;

/**
 * Created by HUAHUA on 2017/9/1.
 */

public class TimeLineViewHolder extends RecyclerView.ViewHolder {
    private TextView mTime;
    private ImageView mPic;

    //构造方法
    public TimeLineViewHolder(View itemView, int type) {
        super(itemView);

        mTime = (TextView) itemView.findViewById(R.id.timeline_item_time_month);
        mPic = (ImageView) itemView.findViewById(R.id.timeline_pic);


        TimeLineMarker mMarker = (TimeLineMarker) itemView.findViewById(R.id.item_time_line_mark);
        if (type == ItemType.ATOM) {
            mMarker.setBeginLine(null);
            mMarker.setEndLine(null);
        } else if (type == ItemType.START) {
            mMarker.setBeginLine(null);
        } else if (type == ItemType.END) {
            mMarker.setEndLine(null);
        }
    }

    public void setData(TimeLineModel data){
        mPic.setImageResource(data.getProjectpic());
        mTime.setText(data.getProjecttime());
    }
}
