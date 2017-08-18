package com.example.wangjingyun.commonrecycleviewsdk.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.wangjingyun.commonrecycleviewsdk.R;
import com.example.wangjingyun.commonrecycleviewsdk.recycleview.HeadTailRecycleView;

/**
 * 分割线
 * Created by wangjingyun on 2017/8/11.
 *
 */

public class LinnerItemDecoration extends RecyclerView.ItemDecoration{

    private Drawable mDrawable;

    public LinnerItemDecoration(Drawable mDrawable){

        this.mDrawable=mDrawable;

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        //多布局recycleview
        if(parent instanceof HeadTailRecycleView){


            HeadTailRecycleView headTailRecycleView=(HeadTailRecycleView)parent;


            int childCount=parent.getLayoutManager().getItemCount();

            Rect rect=new Rect();

            rect.left=parent.getPaddingLeft();

            rect.right=parent.getWidth()-parent.getPaddingRight();

            for(int i=headTailRecycleView.getHeadViewSize()+1;i<childCount;i++){

                View childAt = parent.getChildAt(i);
                rect.bottom=childAt.getTop();
                rect.top=childAt.getTop()-mDrawable.getIntrinsicHeight();
                mDrawable.setBounds(rect);
                mDrawable.draw(c);

            }
        }else{
            //单布局 recycyleview
            int childCount=parent.getLayoutManager().getItemCount();

            Rect rect=new Rect();

            rect.left=parent.getPaddingLeft();

            rect.right=parent.getWidth()-parent.getPaddingRight();

            for(int i=1;i<childCount;i++){

                View childAt = parent.getChildAt(i);
                rect.bottom=childAt.getTop();
                rect.top=childAt.getTop()-mDrawable.getIntrinsicHeight();
                mDrawable.setBounds(rect);
                mDrawable.draw(c);

            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);

        if(parent instanceof HeadTailRecycleView){

            HeadTailRecycleView headTailRecycleView=(HeadTailRecycleView)parent;

            if(position>headTailRecycleView.getHeadViewSize()){

                outRect.top=mDrawable.getIntrinsicHeight();

            }

        }else{

            if(position!=0){

                outRect.top=mDrawable.getIntrinsicHeight();

            }


        }


    }
}
