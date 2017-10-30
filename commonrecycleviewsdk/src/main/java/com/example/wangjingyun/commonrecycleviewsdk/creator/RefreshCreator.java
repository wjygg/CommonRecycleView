package com.example.wangjingyun.commonrecycleviewsdk.creator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangjingyun.commonrecycleviewsdk.R;
import com.example.wangjingyun.commonrecycleviewsdk.listener.RefreshViewCreator;

/**
 * 下拉刷新 实现类
 * Created by Administrator on 2017/10/30.
 */

public class RefreshCreator extends RefreshViewCreator{

    //回调头部局
    @Override
    public View getRefreshView(Context context, ViewGroup parent) {

        View refreshView = LayoutInflater.from(context).inflate(R.layout.item_mainhead_layout, parent, false);
        return refreshView;

    }

    //下拉刷新
    @Override
    public void pull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus) {

    }

    //正在刷新
    @Override
    public void onRefreshing() {

    }

    //停止刷新
    @Override
    public void onStopRefresh() {

    }
}
