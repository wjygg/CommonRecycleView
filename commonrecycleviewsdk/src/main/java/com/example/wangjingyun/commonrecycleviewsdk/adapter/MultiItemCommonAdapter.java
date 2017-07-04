package com.example.wangjingyun.commonrecycleviewsdk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.wangjingyun.commonrecycleviewsdk.listener.MultiItemTypeListener;
import com.example.wangjingyun.commonrecycleviewsdk.viewholder.CommonViewHolder;

import java.util.List;

/**
 * 封装 多布局 commonadapter
 *
 * Created by wangjingyun on 2017/7/4.
 */

public abstract class MultiItemCommonAdapter<T> extends CommonRecycleViewAdapter<T> {

    private MultiItemTypeListener listener;

    public MultiItemCommonAdapter(Context context, List<T> mDatas,MultiItemTypeListener listener) {

        super(context, mDatas, -1);

        this.listener=listener;

    }

    @Override
    public int getItemViewType(int position) {

        return listener.getItemViewType(position);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId = listener.getLayoutId(viewType);

        CommonViewHolder commonViewHolder = CommonViewHolder.get(mContext, parent, layoutId);

        return commonViewHolder;
    }


}
