package com.example.wangjingyun.commonrecycleviewsdk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
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

    private SparseArray<View> mHeadViews;

    private SparseArray<View> mFooterViews;

    // 基本的头部类型开始位置  用于viewType
    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    // 基本的底部类型开始位置  用于viewType
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;


    //基本的头部类型设置
    public MultiItemCommonAdapter(Context context, List<T> mDatas,MultiItemTypeListener listener) {

        super(context, mDatas, -1);

        this.listener=listener;

        mHeadViews=new SparseArray<View>();

        mFooterViews=new SparseArray<View>();

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

    //添加头部




}
