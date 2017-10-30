package com.example.wangjingyun.commonrecycleviewsdk.listener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * 刷新辅助类
 * Created by Administrator on 2017/8/23.
 */

public abstract class RefreshViewCreator {

    /**
     *
     * @param context 上下文
     * @param parent  父布局
     * @return
     */
   public abstract View getRefreshView(Context context,ViewGroup parent);

    /**
     *
     * @param currentDragHeight 当前 下拉高度
     * @param refreshViewHeight 总高度
     * @param currentRefreshStatus 当前状态
     */
   public abstract void pull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus);

    /**
     * 正在刷新
     */
    public abstract void onRefreshing();

    /**
     * 停止刷新
     */
    public abstract void onStopRefresh();

}
