package com.anddle.music.model;

/**
 * Created by HUAHUA on 2017/8/31.
 * 时间轴数据模型
 */

public class TimeLineModel {
    private String projecttime;
    private int projectpic;

    public TimeLineModel(int china) {
        //初始化构造方法
    }

    public TimeLineModel(int projectpic,String projecttime) {
        this.projectpic = projectpic;
        this.projecttime = projecttime;

    }
    public String getProjecttime() {
        return projecttime;
    }

    public void setProjecttime(String projecttime) {
        this.projecttime = projecttime;
    }

    public int getProjectpic() {
        return projectpic;
    }

    public void setProjectpic(int projectpic) {
        this.projectpic = projectpic;
    }


}