package com.example.wangjingyun.commonrecycleviewsdk.creator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.example.wangjingyun.commonrecycleviewsdk.R;
import com.example.wangjingyun.commonrecycleviewsdk.listener.LoadRefreshViewCreator;
import com.example.wangjingyun.commonrecycleviewsdk.recycleview.LoadRefreshRecycleView;

/**
 * Created by Administrator on 2017/11/1.
 */

public class LoadRefreshCreator extends LoadRefreshViewCreator{

    // 加载数据的ImageView
    private TextView mLoadTv;
    private View mRefreshIv;

    /**
     * @param context 上下文
     * @param parent recycleview
     * @return
     */
    @Override
    public View getLoadRefreshView(Context context, ViewGroup parent) {

        View refreshView = LayoutInflater.from(context).inflate(R.layout.item_load_layout, parent, false);
        mLoadTv = (TextView) refreshView.findViewById(R.id.load_tv);
        mRefreshIv = refreshView.findViewById(R.id.refresh_iv);
        return refreshView;
    }

    /**
     * @param currentDragHeight 下拉的高度
     * @param loadViewHeight  view 的高度
     * @param currentLoadStatus 状态
     */
    @Override
    public void onLoad(int currentDragHeight, int loadViewHeight, int currentLoadStatus) {

        if (currentLoadStatus == LoadRefreshRecycleView.LOAD_STATUS_PULL_DOWN_REFRESH) {
            mLoadTv.setText("上拉加载更多");
        }
        if (currentLoadStatus == LoadRefreshRecycleView.LOAD_STATUS_LOOSEN_LOADING) {
            mLoadTv.setText("松开加载更多");
        }
    }

    /**
     *
     */
    @Override
    public void onLoading() {
        // 刷新的时候不断旋转
        mLoadTv.setVisibility(View.INVISIBLE);
        mRefreshIv.setVisibility(View.VISIBLE);

        // 加载的时候不断旋转
        RotateAnimation animation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(1000);
        mRefreshIv.startAnimation(animation);
    }

    @Override
    public void onStopLoading() {

        // 停止加载的时候清除动画
        mRefreshIv.setRotation(0);
        mRefreshIv.clearAnimation();
        mLoadTv.setText("上拉加载更多");
        mLoadTv.setVisibility(View.VISIBLE);
        mRefreshIv.setVisibility(View.INVISIBLE);
    }
}
