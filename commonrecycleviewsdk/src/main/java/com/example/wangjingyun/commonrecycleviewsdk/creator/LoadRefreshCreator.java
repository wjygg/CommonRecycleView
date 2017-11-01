package com.example.wangjingyun.commonrecycleviewsdk.creator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.example.wangjingyun.commonrecycleviewsdk.R;
import com.example.wangjingyun.commonrecycleviewsdk.listener.LoadRefreshViewCreator;

/**
 * Created by Administrator on 2017/11/1.
 */

public class LoadRefreshCreator extends LoadRefreshViewCreator{

    View view;
    /**
     * @param context 上下文
     * @param parent recycleview
     * @return
     */
    @Override
    public View getLoadRefreshView(Context context, ViewGroup parent) {
        View refreshView = LayoutInflater.from(context).inflate(R.layout.item_load_layout, parent, false);
        view=refreshView.findViewById(R.id.tv_text);
        return refreshView;
    }

    /**
     *
     * @param currentDragHeight 下拉的高度
     * @param loadViewHeight  view 的高度
     * @param currentLoadStatus 状态
     */
    @Override
    public void onPull(int currentDragHeight, int loadViewHeight, int currentLoadStatus) {

        float rotate = ((float) currentDragHeight) / loadViewHeight;
        // 不断下拉的过程中不断的旋转图片
        view.setRotation(rotate * 360);
    }

    /**
     *
     */
    @Override
    public void onLoading() {
        // 刷新的时候不断旋转
        RotateAnimation animation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(1000);
        view.startAnimation(animation);
    }

    @Override
    public void onStopLoading() {

        // 停止加载的时候清除动画
        view.setRotation(0);
        view.clearAnimation();
    }
}
