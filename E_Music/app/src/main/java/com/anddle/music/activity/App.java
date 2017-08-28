package com.anddle.music.activity;

/**
 * Created by snail on 2017/8/13.
 */

public class App {
    //name, wechat, img, sex, erweima
    private String name;
    private String wechat;
    private String img;
    private String sex;
    private String erweima;

    //snail**
    private String userid;
    private String pwd;

    public String getuserid() {
        return userid;
    }

    public void setuserid(String name) {
        this.userid = userid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    //snail**

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getErweima() {
        return erweima;
    }

    public void setErweima(String erweima) {
        this.erweima = erweima;
    }
}
