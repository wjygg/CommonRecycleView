package com.example.wangjingyun.commonrecycleviewsdk.recycleview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.example.wangjingyun.commonrecycleviewsdk.creator.LoadRefreshCreator;
import com.example.wangjingyun.commonrecycleviewsdk.listener.LoadRefreshViewCreator;
import com.example.wangjingyun.commonrecycleviewsdk.listener.RefreshViewCreator;

/**
 * Created by Administrator on 2017/11/1.
 */

public class LoadRefreshRecycleView extends RefreshRecycleView{

    private View loadRefreshView;

    private int loadRefreshViewHeight=0;

    private LoadRefreshViewCreator loadRefreshViewCreator;

    //手指按下的y值
    private float mFingerDownY;

    private boolean mCurrentDownDrag=false;

    // 当前的状态
    private int mCurrentLoadStatus;
    // 默认状态
    public int LOAD_STATUS_NORMAL = 0x0011;
    // 上拉加载更多状态
    public static int LOAD_STATUS_PULL_DOWN_REFRESH = 0x0022;
    // 松开加载更多状态
    public static int LOAD_STATUS_LOOSEN_LOADING = 0x0033;
    // 正在加载更多状态
    public int LOAD_STATUS_LOADING = 0x0044;

    public LoadRefreshRecycleView(Context context) {
        this(context,null);
    }

    public LoadRefreshRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadRefreshRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        addRefreshLoadView();
    }


    public void addRefreshLoadViewCreator(LoadRefreshViewCreator loadRefreshViewCreator) {
        this.loadRefreshViewCreator = loadRefreshViewCreator;
    }

    /**
     * 添加上啦加载布局
     */
    public void addRefreshLoadView(){

        //adapter 不等于null
        if(multiItemCommonAdapter!=null){

            //用户没有自定义上拉布局 用默认的布局
            if(loadRefreshViewCreator==null){

                loadRefreshViewCreator=new LoadRefreshCreator();

                View view=loadRefreshViewCreator.getLoadRefreshView(getContext(),this);

                addFooterView(view);

                this.loadRefreshView=view;
            }else{

               View loadRefreshView=loadRefreshViewCreator.getLoadRefreshView(getContext(),this);

                if(loadRefreshView!=null){

                    addFooterView(loadRefreshView);

                    this.loadRefreshView=loadRefreshView;
                }
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                //子控件消费 down 事件 进不了ontouchEvent
                mFingerDownY=ev.getRawY();
                break;

            case MotionEvent.ACTION_UP:
               //向上滑动松开了初始状态
                if (mCurrentDownDrag) {
                restoreLoadView();
                 }
                break;
        }
        return super.dispatchTouchEvent(ev);


    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_MOVE:

                //没有滚动到底部 不处理
                if(canChildScrollDown()|| mCurrentLoadStatus == LOAD_STATUS_LOADING){

                   return super.onTouchEvent(ev);
                }


                if (loadRefreshViewCreator != null) {
                    loadRefreshViewHeight = loadRefreshView.getMeasuredHeight();
                }


                // 解决上拉加载更多自动滚动问题
                if (mCurrentDownDrag) {
                    scrollToPosition(getAdapter().getItemCount() - 1);
                }


                // 获取手指触摸拖拽的距离
                int distanceY = (int) ((ev.getRawY() - mFingerDownY) * mDragIndex);
                // 如果是已经到达头部，并且不断的向下拉，那么不断的改变refreshView的marginTop的值
                if (distanceY < 0) {
                    setLoadViewMarginBottom(-distanceY);
                    updateLoadStatus(-distanceY);
                    mCurrentDownDrag = true;
                    return true;
                }

                break;
        }

        return super.onTouchEvent(ev);
    }


    /**
     * 设置加载View的marginBottom
     */
    public void setLoadViewMarginBottom(int marginBottom) {
        MarginLayoutParams params = (MarginLayoutParams) loadRefreshView.getLayoutParams();
        if (marginBottom < 0) {
            marginBottom = 0;
        }
        params.bottomMargin = marginBottom;
        loadRefreshView.setLayoutParams(params);
    }

    /**
     * 更新加载的状态
     */
    private void updateLoadStatus(int distanceY) {
        if (distanceY <= 0) {
            mCurrentLoadStatus = LOAD_STATUS_NORMAL;
        } else if (distanceY < loadRefreshViewHeight) {
            mCurrentLoadStatus = LOAD_STATUS_PULL_DOWN_REFRESH;
        } else {
            mCurrentLoadStatus = LOAD_STATUS_LOOSEN_LOADING;
        }

        if (loadRefreshViewCreator != null) {
            loadRefreshViewCreator.onPull(distanceY, loadRefreshViewHeight, mCurrentLoadStatus);
        }
    }
    //判断是不是滚到底部 if true 没有滚动到底部
    public boolean canChildScrollDown() {

        return ViewCompat.canScrollVertically(this, 1);
    }
    /**
     * 重置当前加载更多状态
     */
    private void restoreLoadView() {
        int currentBottomMargin = ((MarginLayoutParams) loadRefreshView.getLayoutParams()).bottomMargin;
        int finalBottomMargin = 0;
        if (mCurrentLoadStatus == LOAD_STATUS_LOOSEN_LOADING) {
            mCurrentLoadStatus = LOAD_STATUS_LOADING;
            if (loadRefreshViewCreator != null) {
                loadRefreshViewCreator.onLoading();
            }
            if (mListener != null) {
                mListener.onLoad();
            }
        }

        int distance = currentBottomMargin - finalBottomMargin;

        // 回弹到指定位置
        ValueAnimator animator = ObjectAnimator.ofFloat(currentBottomMargin, finalBottomMargin).setDuration(distance);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentTopMargin = (float) animation.getAnimatedValue();
                setLoadViewMarginBottom((int) currentTopMargin);
            }
        });
        animator.start();
        mCurrentDownDrag = false;
    }

    /**
     * 停止加载更多
     */
    public void onStopLoad() {
        mCurrentLoadStatus = LOAD_STATUS_NORMAL;
        restoreLoadView();
        if (loadRefreshViewCreator != null) {
            loadRefreshViewCreator.onStopLoading();
        }
    }

    // 处理加载更多回调监听
    private OnLoadMoreListener mListener;

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoad();
    }
}
