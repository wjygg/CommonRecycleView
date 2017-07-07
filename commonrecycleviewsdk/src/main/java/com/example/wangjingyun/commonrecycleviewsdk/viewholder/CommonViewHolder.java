package com.example.wangjingyun.commonrecycleviewsdk.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用的 viewholder
 *
 * Created by wangjngyun on 2017/7/4.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {

    public SparseArray<View> mviews;

    public View mConvertView;

    public Context mcontext;

    public CommonViewHolder(Context context,View itemView, ViewGroup parent) {
        super(itemView);

        mcontext=context;

        mConvertView=itemView;

        mviews=new SparseArray<View>();

    }


    public static CommonViewHolder get(Context context, ViewGroup parent, int layoutId){

        View inflate = LayoutInflater.from(context).inflate(layoutId, parent, false);

        CommonViewHolder viewHolder=new CommonViewHolder(context,inflate,parent);

        return  viewHolder;

    }

    public static CommonViewHolder get(Context context, ViewGroup parent, View view){


        CommonViewHolder viewHolder=new CommonViewHolder(context,view,parent);

        return  viewHolder;

    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mviews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mviews.put(viewId, view);
        }
        return (T) view;
    }


    public void setText(int viewId, String text)
    {
        TextView tv = getView(viewId);
        tv.setText(text);

    }

    public void setImageResource(int viewId, int resId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(resId);

    }
}
