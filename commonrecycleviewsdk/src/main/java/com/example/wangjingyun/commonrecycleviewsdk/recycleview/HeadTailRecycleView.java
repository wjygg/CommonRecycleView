package com.example.wangjingyun.commonrecycleviewsdk.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.wangjingyun.commonrecycleviewsdk.adapter.MultiItemCommonAdapter;

import java.util.List;

/**
 * 自定义recycleview 搭配 多布局 MultiItemCommonAdapter 使用
 *
 * Created by wangjingyun on 2017/7/11.
 */

public class HeadTailRecycleView extends RecyclerView {


    public MultiItemCommonAdapter multiItemCommonAdapter;

    public HeadTailRecycleView(Context context) {
        super(context);
    }

    public HeadTailRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadTailRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter) {


        if(adapter==null){

            return;

        }

        if(adapter instanceof MultiItemCommonAdapter){


            multiItemCommonAdapter= (MultiItemCommonAdapter) adapter;

            super.setAdapter(adapter);

            multiItemCommonAdapter.adjustSpanSize(this);

        }else{

            throw new RuntimeException("请使用 MultiItemCommonAdapter");
        }

    }

    public void addHeadView(View view){

        if(multiItemCommonAdapter!=null){

            multiItemCommonAdapter.addHeadViews(view);
        }

    }

    public void addFooterView(View view){

        if(multiItemCommonAdapter!=null){

            multiItemCommonAdapter.addFooterViews(view);
        }
    }

    public void removeHeadView(View view){

        if(multiItemCommonAdapter!=null){

            multiItemCommonAdapter.removeHeadViews(view);
        }

    }

    public void removeFooterView(View view){

        if(multiItemCommonAdapter!=null){

            multiItemCommonAdapter.removeFooterViews(view);
        }
    }

    public int getHeadViewSize(){

        if(multiItemCommonAdapter!=null){

           return  multiItemCommonAdapter.getHeadViewSize();

        }
        return 0;
    }
    public int getFooterViewSize(){

        if(multiItemCommonAdapter!=null){

            return  multiItemCommonAdapter.getFootViewSize();

        }
        return 0;
    }

}
