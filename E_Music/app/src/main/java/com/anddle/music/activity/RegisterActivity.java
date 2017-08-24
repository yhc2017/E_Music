package com.anddle.music.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.anddle.music.R;
import com.anddle.music.fragment.FragmentMusic;

/**
 * Created by HUAHUA on 2017/8/24.
 * 手机号注册页面
 */

public class RegisterActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    Toast mToast;
    Intent intent;

    //执行事件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        setToolbar();
        initViews();
        setEvents();
    }


    //设置Toolbar
    public void setToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        // title
        mToolbar.setTitle("手机号注册");
        mToolbar.setTitleTextColor(Color.WHITE);
        //取代原本的actionbar
        setSupportActionBar(mToolbar);
        //侧边栏的按钮
        mToolbar.setNavigationIcon(R.drawable.actionbar_back);
        //设置NavigationIcon的点击事件,需要放在setSupportActionBar之后设置才会生效,
        //因为setSupportActionBar里面也会setNavigationOnClickListener
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(RegisterActivity.this , FragmentMusic.class);
                startActivity(intent);

            }
        });

    }

    //所有按钮实例成对象
    public void initViews() {}


    //所有的对按钮的事件进行监听
    public void setEvents() {
        //匿名方法
        MyListener listener = new MyListener();}

    //选择触发的事件
    public class MyListener implements  View.OnClickListener { /*用接口的方式*/
        public void onClick(View v) {
            Intent intent = null;
            int id = v.getId();   /*得到v的id付给id*/
            switch (id) {
            }
        }
    }

}
