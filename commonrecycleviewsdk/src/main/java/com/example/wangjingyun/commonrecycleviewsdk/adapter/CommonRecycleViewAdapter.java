package com.example.wangjingyun.commonrecycleviewsdk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangjingyun.commonrecycleviewsdk.viewholder.CommonViewHolder;

import java.util.List;

/**
 * 单布局 commonadapter
 *
 * Created by wangjingyun on 2017/7/4.
 */

public abstract  class CommonRecycleViewAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    public Context mContext;
    public int mLayoutId;
    public List<T> mDatas;

    public CommonRecycleViewAdapter(Context context,List<T> mDatas,int mLayoutId){

        this.mContext=context;
        this.mDatas=mDatas;
        this.mLayoutId=mLayoutId;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder commonViewHolder = CommonViewHolder.get(mContext, parent, mLayoutId);
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {

        if(mItemClickListener!=null){

            holder.mConvertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(position);
                }
            });
        }

        if(mLongClickListener!=null){

            holder.mConvertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    mLongClickListener.onItemLongClick(position);

                    return false;
                }
            });

        }


     convert(holder,mDatas.get(position));

    }

    public abstract void convert(CommonViewHolder holder, T t);


    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     * 添加数据
     * @param datas
     */
    public void initDatas(List<T> datas){

        this.mDatas=datas;

        this.notifyDataSetChanged();

    }

    public void removeDatas(int position){

        this.mDatas.remove(position);

        this.notifyDataSetChanged();
    }

    public void addDatas(List<T> datas){

        this.mDatas.addAll(datas);

        this.notifyDataSetChanged();

    }

    /***************
     * 设置条目点击和长按事件
     *********************/
    public OnItemClickListener mItemClickListener;
    public OnLongClickListener mLongClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener longClickListener) {
        this.mLongClickListener = longClickListener;
    }

    public interface OnItemClickListener{

        void onItemClick(int position);
    }

    public interface OnLongClickListener{

        void onItemLongClick(int position);
    }


}
