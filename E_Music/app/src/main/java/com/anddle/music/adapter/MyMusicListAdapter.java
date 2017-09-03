package com.anddle.music.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anddle.music.R;
import com.anddle.music.activity.MusicListView;
import com.anddle.music.model.MyMusicItem;

import java.util.List;

/**
 * Created by HUAHUA on 2017/8/12.
 * recyclerview的简单适配器
 */

public class MyMusicListAdapter extends RecyclerView.Adapter<MyMusicListAdapter.ViewHolder>{
        private List<MyMusicItem>mMyMusicList;
        private Context mContext;

    public MyMusicListAdapter(List<MyMusicItem> myMusicList){
        mMyMusicList = myMusicList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView listImg;
        TextView listName;
        TextView listCount;

        public ViewHolder(View view){
            super(view);
            listImg = (ImageView) view.findViewById(R.id.mylistmusic_image);
            listName = (TextView) view.findViewById(R.id.mylistmusic_name);
            listCount = (TextView) view.findViewById((R.id.mylistmusic_count));
        }


    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mine_mymusic_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder( ViewHolder holder,int position){

        MyMusicItem myMusicItem = mMyMusicList.get(position);
//        MyMusicItem myMusicItem = (MyMusicItem)itemResults.get(position);
        holder.listImg.setImageResource(myMusicItem.getAvatar());
        holder.listName.setText(myMusicItem.getTitle());
        holder.listCount.setInputType(myMusicItem.getCount());

        setOnListener(holder, position);
    }

    //点击子项的监听
    public void setOnListener(ViewHolder holder,int position) {
        switch (position) {
            case 0:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MusicListView.class);
                        v.getContext().startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount(){
        return mMyMusicList.size();
    }
}
