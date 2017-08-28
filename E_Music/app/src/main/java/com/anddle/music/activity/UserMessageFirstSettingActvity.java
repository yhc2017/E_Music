package com.anddle.music.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anddle.music.R;
import com.anddle.music.dialog.InputDialog;
import com.anddle.music.uitl.BindView;

/**
 * Created by HUAHUA on 2017/8/29.
 * 用户注册资料完善页面
 */

public class UserMessageFirstSettingActvity extends AppCompatActivity {

    //实例化对象
    @BindView(R.id.user_message_toolbar)Toolbar mToolbar;
    @BindView(R.id.user_message_photo_view)RelativeLayout mPhotoView;
    @BindView(R.id.user_message_name_view)RelativeLayout mNameView;
    @BindView(R.id.user_message_id_view)RelativeLayout mIDView;
    @BindView(R.id.user_message_sex_view)RelativeLayout mSexView;
    @BindView(R.id.user_message_college_view)RelativeLayout mCollegeView;
    @BindView(R.id.user_message_major_view)RelativeLayout mMajorView;
    @BindView(R.id.user_message_password_view)RelativeLayout mPasswordView;
    @BindView(R.id.user_message_qq_view)RelativeLayout mQQView;
    @BindView(R.id.user_message_weixin_view)RelativeLayout mWeiXinView;
    @BindView(R.id.user_message_email_view)RelativeLayout mEmailView;
    @BindView(R.id.user_message_sbmit)Button mSbmit;
    Context mContext;
    //执行事件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachBaseContext(mContext);
        setContentView(R.layout.user_message);
        setToolBar();
        setEvents();
    }

    //ToolBar
    public void setToolBar() {
        // title
        mToolbar.setTitle("用户资料");
        mToolbar.setTitleTextColor(Color.WHITE);
        //取代原本的actionbar
        setSupportActionBar(mToolbar);
    }


    //事件
    public void setEvents(){
        MyListener listener = new MyListener();
        mPhotoView.setOnClickListener(listener);
        mNameView.setOnClickListener(listener);
        mIDView.setOnClickListener(listener);
        mSexView.setOnClickListener(listener);
        mCollegeView.setOnClickListener(listener);
        mMajorView.setOnClickListener(listener);
        mPasswordView.setOnClickListener(listener);
        mQQView.setOnClickListener(listener);
        mWeiXinView.setOnClickListener(listener);
        mEmailView.setOnClickListener(listener);
        mSbmit.setOnClickListener(listener);
    }

    //选择触发的事件
    public class MyListener implements  View.OnClickListener { //接口方式
        public void onClick(View v) {
            Intent intent = null;
            int id = v.getId();
            switch (id) {
                //换头像
                case R.id.user_message_photo_view :
                    Toast.makeText(mContext,"选择图片",Toast.LENGTH_SHORT).show();
                    break;
                //昵称
                case R.id.user_message_name_view: {
                    InputDialog inputName = new InputDialog(mContext);
                    inputName.setTips("昵称");
                    inputName.setSingle(true).setOnClickBottomListener(new InputDialog.OnClickBottomListener() {
                        @Override
                        public void onYesClick() {
                            Toast.makeText(mContext,"点击了确认按钮",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNoClick() {
                            Toast.makeText(mContext,"点击了取消按钮",Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                }
                //id
                case R.id.user_message_id_view:{
                    InputDialog inputName = new InputDialog(mContext);
                    inputName.setTips("设置自己ID(设置后不能改变）");
                    inputName.setSingle(true).setOnClickBottomListener(new InputDialog.OnClickBottomListener() {
                        @Override
                        public void onYesClick() {
                            Toast.makeText(mContext,"点击了确认按钮",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNoClick() {
                            Toast.makeText(mContext,"点击了取消按钮",Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                }
                //性别
                case R.id.user_message_sex_view:{

                    break;
                }
                //大学
                case R.id.user_message_college_view:{

                    break;
                }
                //专业
                case R.id.user_message_major_view:{

                    break;
                }
                //修改密码
                case R.id.user_message_password_view:{
                    InputDialog inputName = new InputDialog(mContext);
                    inputName.setTips("请修改你的密码");
                    inputName.setMessage("建议密码同时中含有数字和字母");
                    inputName.setSingle(true).setOnClickBottomListener(new InputDialog.OnClickBottomListener() {
                        @Override
                        public void onYesClick() {
                            Toast.makeText(mContext,"点击了确认按钮",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNoClick() {
                            Toast.makeText(mContext,"点击了取消按钮",Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                }
                //绑定qq
                case R.id.user_message_qq_view:{

                    break;
                }
                //绑定微信
                case R.id.user_message_weixin_view:{

                    break;
                }
                //绑定邮箱
                case R.id.user_message_email_view:{

                    break;
                }
                //提交按钮
                case R.id.user_message_sbmit:{
                    Toast.makeText(mContext,"提交按钮",Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    }

}

