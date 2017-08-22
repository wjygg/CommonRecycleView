package com.example.wangjingyun.commonrecycleviewsdk.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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

        drawHorizontal(c,parent);

        drawVertical(c,parent);

    }

    private void drawVertical(Canvas c, RecyclerView parent){

        int childCount=parent.getLayoutManager().getItemCount();

        Rect rect=new Rect();

        for(int i=0;i<childCount;i++){

            View childAt = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams= (RecyclerView.LayoutParams) childAt.getLayoutParams();
            rect.left=childAt.getRight()+layoutParams.rightMargin;
            rect.top=childAt.getTop()-layoutParams.topMargin;
            rect.right= rect.left+mDrawable.getIntrinsicWidth();
            rect.bottom=childAt.getBottom()+layoutParams.bottomMargin;
            mDrawable.setBounds(rect);

            mDrawable.draw(c);
        }


    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {


        Rect rect=new Rect();

        int childCount=parent.getLayoutManager().getItemCount();

        for(int i=0;i<childCount;i++){

            View childAt = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            rect.left=childAt.getLeft()-layoutParams.leftMargin;
            rect.top=childAt.getBottom()+layoutParams.topMargin;
            rect.right=childAt.getRight()+layoutParams.rightMargin+mDrawable.getIntrinsicWidth();
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

        //判断能不能被整除
        int result=childCount%spanCount;

        if(result==0){
        //整除

            if(position==(childCount-3)){

                return true;
            }else if(position==(childCount-2)){

                return true;
            }else if(position==(childCount-1)){

                return true;
            }else{

                return false;
            }
        }else{
            if(position>((columnSize-1)*spanCount)-1){

                return true;
            }else{

                return false;
            }

        }
    }
}
