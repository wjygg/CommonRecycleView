package com.example.wangjingyun.commonrecycleviewsdk.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.wangjingyun.commonrecycleviewsdk.R;

/**
 * 分割线
 * Created by wangjingyun on 2017/8/11.
 *
 */

public class LinnerItemDecoration extends RecyclerView.ItemDecoration{

    private Paint paint;


    public LinnerItemDecoration(){

        paint=new Paint();
     //   paint.setColor(R.color.DimGray);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int childCount=parent.getChildCount();



        for(int i=1;i<childCount;i++){

            View childAt = parent.getChildAt(i);

       //     c.drawRect(,paint);

        }


    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);


        if(position!=0){

            outRect.top=10;

        }

    }
}
