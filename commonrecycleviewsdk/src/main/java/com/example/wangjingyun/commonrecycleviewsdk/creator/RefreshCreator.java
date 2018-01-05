package com.example.wangjingyun.commonrecycleviewsdk.creator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.example.wangjingyun.commonrecycleviewsdk.R;
import com.example.wangjingyun.commonrecycleviewsdk.listener.RefreshViewCreator;

/**
 * 下拉刷新 实现类
 * Created by Administrator on 2017/10/30.
 */

public class RefreshCreator extends RefreshViewCreator{

    // 加载数据的ImageView
    private View mRefreshIv;
    //回调头部局
    @Override
    public View getRefreshView(Context context, ViewGroup parent) {


        View refreshView = LayoutInflater.from(context).inflate(R.layout.item_refresh_layout, parent, false);
        mRefreshIv = refreshView.findViewById(R.id.refresh_iv);
        return refreshView;

    }

    //下拉刷新
    @Override
    public void pull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus) {
        float rotate = ((float) currentDragHeight) / refreshViewHeight;
        // 不断下拉的过程中旋转图片
        mRefreshIv.setRotation(rotate * 360);
    }

    //正在刷新
    @Override
    public void onRefreshing() {

        // 刷新的时候不断旋转
        RotateAnimation animation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(1000);
        mRefreshIv.startAnimation(animation);
    }

    //停止刷新
    @Override
    public void onStopRefresh() {

        // 停止加载的时候清除动画
        mRefreshIv.setRotation(0);
        mRefreshIv.clearAnimation();

    }
}
