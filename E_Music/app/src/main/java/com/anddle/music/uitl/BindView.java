package com.anddle.music.uitl;

import android.support.annotation.IdRes;

/**
 * Created by HUAHUA on 2017/8/18.
 * 一个提高代码编写速度的小工具
 * 获取资源并且实例化对象
 */

public @interface BindView {
    @IdRes int value();
}
