package com.example.wangjingyun.commonrecycleviewsdk.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/8/16.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration{

    private Drawable mDrawable;

    public GridItemDecoration(Drawable drawable){

        this.mDrawable=drawable;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int bottom=mDrawable.getIntrinsicHeight();
        int right=mDrawable.getIntrinsicWidth();

        int position=parent.getChildAdapterPosition(view);

        int childCount=parent.getLayoutManager().getItemCount();
        RecyclerView.LayoutManager manager=parent.getLayoutManager();
        int spanCount=((GridLayoutManager)manager).getSpanCount();
        //最右边一列
        if(isRightPosition(position,spanCount,childCount)){

            right=0;
        }
        if(isBottomPosition(position,spanCount,childCount)){

            bottom=0;
        }

        outRect.bottom=bottom;
        outRect.right=right;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        Rect rect=new Rect();

        int childCount=parent.getLayoutManager().getItemCount();

        for(int i=0;i<childCount;i++){

            View childAt = parent.getChildAt(i);

            rect.left=childAt.getLeft();
            rect.top=childAt.getBottom();
            rect.right=childAt.getWidth();
            rect.bottom=rect.top+mDrawable.getIntrinsicHeight();

            mDrawable.setBounds(rect);
            mDrawable.draw(c);
        }
    }

    public boolean isRightPosition(int position,int spanCount,int childCount){

        if((position+1)%spanCount==0){

            return true;
        }
        return false;
    }

    public boolean isBottomPosition(int position,int spanCount,int childCount){

        int columnSize=(childCount/spanCount)+1;

        //1行全是 true
        if(columnSize==1){

            return true;

        }else {

            if(position>((columnSize-1)*spanCount)-1){

                return true;
            }else{

                return false;
            }

        }

    }
}
