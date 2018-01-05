package com.example.wangjingyun.commonrecycleviewsdk.listener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/11/1.
 */

public abstract class LoadRefreshViewCreator {

    /**
     *
     * @param context 上下文
     * @param parent recycleview
     * @return
     */
    public abstract View getLoadRefreshView(Context context, ViewGroup parent);

    /**
     * @param currentDragHeight 下拉的高度
     * @param loadViewHeight  view 的高度
     * @param currentLoadStatus 状态
     */
    public abstract void onLoad(int currentDragHeight, int loadViewHeight, int currentLoadStatus);

    /**
     * 正在加载中
     */
    public abstract void onLoading();

    /**
     * 停止加载
     */
    public abstract  void onStopLoading();


}
