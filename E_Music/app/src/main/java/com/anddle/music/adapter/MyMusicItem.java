package com.anddle.music.adapter;

/**
 * Created by HUAHUA on 2017/8/12.
 * 我的音乐 自定义类型
 */

public class MyMusicItem {
    public String title;   //信息标题
    public int count; //统计歌曲数量
    public int avatar; //图片ID
//    public boolean countChanged = true;

    public MyMusicItem(int avatar,String title,int count){
        this.avatar = avatar;
        this.title = title;
        this.count = count;
    }
    //标题
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    //统计歌曲数量
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    //图片
    public int getAvatar() {
        return avatar;
    }
    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
