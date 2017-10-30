package com.example.wangjingyun.commonrecycleviewsdk.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.wangjingyun.commonrecycleviewsdk.creator.RefreshCreator;
import com.example.wangjingyun.commonrecycleviewsdk.listener.RefreshViewCreator;

/**
 * 下拉刷新
 * Created by Administrator on 2017/10/30.
 */

public class RefreshRecycleView extends HeadTailRecycleView{

    private RefreshViewCreator refreshCreator;

    private View refreshView;

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

               
               break;

             case MotionEvent.ACTION_MOVE:

                 break;

             case MotionEvent.ACTION_UP:

                 break;
          }
        return super.dispatchTouchEvent(ev);
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
}
