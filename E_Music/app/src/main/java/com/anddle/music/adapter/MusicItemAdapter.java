package com.anddle.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anddle.music.MusicItem;
import com.anddle.music.R;
import com.anddle.music.uitl.Utils;

import java.util.List;

/**
 * 项目名称：E_Music
 * 创建人：zhou-jx
 * 创建时间：2017/8/14 15:51
 * 修改人：zhou-jx
 * 修改时间：2017/8/14 15:51
 * 备注：构建适配器 ， 简单地来说， 适配器就是 Item数组 ， 动态数组 有多少元素就生成多少个Item；
 */

public class MusicItemAdapter extends BaseAdapter {

    private List<MusicItem> mData;
    private final LayoutInflater mInflater;
    private final int mResource;
    private Context mContext;

    public MusicItemAdapter(Context context, int resId, List<MusicItem> data)
    {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
        mResource = resId;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData != null ? mData.get(position): null ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(mResource, parent, false);
        }

        MusicItem item = mData.get(position);

        TextView title = (TextView) convertView.findViewById(R.id.music_title);
        title.setText(item.name);


        TextView createTime = (TextView) convertView.findViewById(R.id.music_duration);

        String times = Utils.convertMSecendToTime(item.duration);
        times = String.format(mContext.getString(R.string.duration), times);
        createTime.setText(times);
        ImageView thumb = (ImageView) convertView.findViewById(R.id.music_thumb);
        if(thumb != null) {
            if (item.thumb != null) {
                thumb.setImageBitmap(item.thumb);
            } else {
                thumb.setImageResource(R.mipmap.default_cover);
            }
        }

        return convertView;
    }

}
