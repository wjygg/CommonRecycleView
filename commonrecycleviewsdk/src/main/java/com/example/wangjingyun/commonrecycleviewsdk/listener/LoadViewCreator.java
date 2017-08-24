package com.example.wangjingyun.commonrecycleviewsdk.listener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 上拉加载 辅助类
 * Created by Administrator on 2017/8/23.
 */

public abstract class LoadViewCreator {


    public abstract View getRefreshView(Context context, ViewGroup viewGroup);


}
