package com.example.wangjingyun.commonrecycleviewsdk.recycleview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.example.wangjingyun.commonrecycleviewsdk.creator.RefreshCreator;
import com.example.wangjingyun.commonrecycleviewsdk.listener.RefreshViewCreator;

/**
 * 下拉刷新
 * Created by Administrator on 2017/10/30.
 */

public class RefreshRecycleView extends HeadTailRecycleView{

    private RefreshViewCreator refreshCreator;

    private View refreshView;

    private int mRefreshViewHeight=0;

    //下拉刷新头部高度
    private float mFingerDownY=0;
    //是否在顶部向下滑动
    private boolean mCurrentDrag=false;

    // 当前的状态
    private int mCurrentRefreshStatus;
    // 默认状态
    public int REFRESH_STATUS_NORMAL = 0x0011;
    // 下拉刷新状态
    public int REFRESH_STATUS_PULL_DOWN_REFRESH = 0x0022;
    // 松开刷新状态
    public int REFRESH_STATUS_LOOSEN_REFRESHING = 0x0033;
    // 正在刷新状态
    public int REFRESH_STATUS_REFRESHING = 0x0033;

    // 手指拖拽的阻力指数
    public float mDragIndex = 0.35f;

    public RefreshRecycleView(Context context) {
        this(context,null);
    }

    public RefreshRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //实例化下拉刷新 具体类
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch(ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                mFingerDownY=ev.getRawY();

                break;
            case MotionEvent.ACTION_UP:

                //手指抬起弹回到 头部

                if(mCurrentDrag){

                    restoreRefreshView();
                }

                break;

        }


        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch(ev.getAction()){
            //move 并且滑动到头部且向下滑动 时 自己处理
            case MotionEvent.ACTION_MOVE:
                //没有滑动到头部 不处理 正在刷新不处理
                if(canChildScrollUp()|| mCurrentRefreshStatus == REFRESH_STATUS_REFRESHING){

                return super.onTouchEvent(ev);
            }
                if (mCurrentDrag) {
                    scrollToPosition(0);
                }

                // 获取手指触摸拖拽的距离
                int distanceY = (int) ((ev.getRawY() - mFingerDownY) * mDragIndex);
                // 如果是已经到达头部，并且不断的向下拉，那么不断的改变refreshView的marginTop的值
                if (distanceY > 0) {
                    int marginTop = distanceY - mRefreshViewHeight;
                    setRefreshViewMarginTop(marginTop);
                    mCurrentDrag = true;
                    updateRefreshStatus(marginTop);
                    return true;
                }
                break;
        }


        return super.onTouchEvent(ev);
    }


    /**
     * 更新刷新的状态
     */
    private void updateRefreshStatus(int marginTop) {
        if (marginTop <= -mRefreshViewHeight) {
            mCurrentRefreshStatus = REFRESH_STATUS_NORMAL;
        } else if (marginTop < 0) {
            mCurrentRefreshStatus = REFRESH_STATUS_PULL_DOWN_REFRESH;
        } else {
            mCurrentRefreshStatus = REFRESH_STATUS_LOOSEN_REFRESHING;
        }

        if (refreshCreator != null) {
            refreshCreator.pull(marginTop, mRefreshViewHeight, mCurrentRefreshStatus);
        }
    }

    public void addRefreshViewCreator(RefreshViewCreator refreshCreator) {
        this.refreshCreator = refreshCreator;
    }

    //添加 下拉刷新头部布局
    public void addRefreshView(){

        if(multiItemCommonAdapter!=null) {
            //用户默认没有传 自定义头部布局 用默认头部布局
            if(refreshCreator==null){

                refreshCreator =new RefreshCreator();

                View refreshCreatorView=refreshCreator.getRefreshView(getContext(),this);

                if (refreshCreatorView != null) {

                    addHeadView(refreshCreatorView);

                    this.refreshView = refreshCreatorView;
                }
            }else{

                View refreshView = refreshCreator.getRefreshView(getContext(), this);

                if (refreshView != null) {

                    addHeadView(refreshView);
                    this.refreshView = refreshView;
                }
            }
        }
    }
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        //默认添加 下拉刷新头部
        addRefreshView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(changed){

            if(refreshView!=null&&mRefreshViewHeight<=0){
                // 获取头部刷新View的高度
                mRefreshViewHeight = refreshView.getMeasuredHeight();
                if (mRefreshViewHeight > 0) {
                    // 隐藏头部刷新的View  marginTop  多留出1px防止无法判断是不是滚动到头部问题
                    setRefreshViewMarginTop(-mRefreshViewHeight + 1);
                }
            }

        }
    }

    /**
     * 设置刷新View的marginTop
     */
    public void setRefreshViewMarginTop(int marginTop) {
        MarginLayoutParams params = (MarginLayoutParams) refreshView.getLayoutParams();
        if (marginTop < -mRefreshViewHeight + 1) {
            marginTop = -mRefreshViewHeight + 1;
        }
        params.topMargin = marginTop;
        refreshView.setLayoutParams(params);
    }


    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {

            return ViewCompat.canScrollVertically(this, -1) || this.getScrollY() > 0;

        } else {
            return ViewCompat.canScrollVertically(this, -1);
        }
    }


    /**
     * 停止刷新
     */
    public void onStopRefresh() {
        mCurrentRefreshStatus = REFRESH_STATUS_NORMAL;
        restoreRefreshView();
        if (refreshCreator != null) {
            refreshCreator.onStopRefresh();
        }
    }

    /**
     * 重置当前刷新状态状态
     */
    private void restoreRefreshView() {
        int currentTopMargin = ((MarginLayoutParams) refreshView.getLayoutParams()).topMargin;
        int finalTopMargin = -mRefreshViewHeight + 1;
        if (mCurrentRefreshStatus == REFRESH_STATUS_LOOSEN_REFRESHING) {
            finalTopMargin = 0;
            mCurrentRefreshStatus = REFRESH_STATUS_REFRESHING;
            if (refreshCreator != null) {
                refreshCreator.onRefreshing();
            }
            if (mListener != null) {
                mListener.onRefresh();
            }
        }

        int distance = currentTopMargin - finalTopMargin;

        // 回弹到指定位置
        ValueAnimator animator = ObjectAnimator.ofFloat(currentTopMargin, finalTopMargin).setDuration(distance);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentTopMargin = (float) animation.getAnimatedValue();
                setRefreshViewMarginTop((int) currentTopMargin);
            }
        });
        animator.start();
        mCurrentDrag = false;
    }

    // 处理刷新回调监听
    private OnRefreshListener mListener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}
