package com.example.wangjingyun.commonrecycleviewsdk.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
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

        if (isHeaderPosition(position)) {
            // 直接返回position位置的key
            return mHeadViews.keyAt(position);
        }
        if (isFooterPosition(position)) {
            // 直接返回position位置的key
            position = position - mHeadViews.size() - mDatas.size();
            return mFooterViews.keyAt(position);
        }
        // 返回列表Adapter的getItemViewType
        position = position - mHeadViews.size();

        //抛出主体position
        return listener.getItemViewType(position);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // viewType 可能就是 SparseArray 的key
        if (isHeaderViewType(viewType)) {
            View headerView = mHeadViews.get(viewType);
            return CommonViewHolder.get(mContext, parent, headerView);
        }

        if (isFooterViewType(viewType)) {
            View footerView = mFooterViews.get(viewType);

            return CommonViewHolder.get(mContext, parent, footerView);
        }

        int layoutId = listener.getLayoutId(viewType);

        CommonViewHolder commonViewHolder = CommonViewHolder.get(mContext, parent, layoutId);

        return commonViewHolder;

    }


    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {

        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }
        // 计算一下位置
        position = position - mHeadViews.size();

        //继承父布局 body点击事件
        super.onBindViewHolder(holder,position);


    }



    /**
     * 是不是头部类型
     */
    private boolean isHeaderViewType(int viewType) {
        int position = mHeadViews.indexOfKey(viewType);
        return position >= 0;
    }


    /**
     * 是不是底部类型
     */
    private boolean isFooterViewType(int viewType) {
        int position = mFooterViews.indexOfKey(viewType);
        return position >= 0;
    }

    @Override
    public int getItemCount() {

        return super.getItemCount()+mHeadViews.size()+mFooterViews.size();
    }

    //添加头部
    public void addHeadViews(View view){

        int position=mHeadViews.indexOfValue(view);

        if(position<0){

            mHeadViews.put(BASE_ITEM_TYPE_HEADER++,view);
        }

        notifyDataSetChanged();

    }

    //添加底部
    public void addFooterViews(View view){

      int position=mFooterViews.indexOfValue(view);
        if(position<0){

         mFooterViews.put(BASE_ITEM_TYPE_FOOTER++,view);

        }
        notifyDataSetChanged();
    }

    //删除头布局
    public void removeHeadViews(View view){

        int position=mHeadViews.indexOfValue(view);

        if(position<0) return;

        mHeadViews.remove(position);

    }
    //删除底部布局
    public void removeFooterViews(View view){

        int position=mFooterViews.indexOfValue(view);

        if(position<0) return;

        mFooterViews.remove(position);

        notifyDataSetChanged();

    }

    //判断是不是头部位置

    public boolean isHeaderPosition(int position){

        return position<mHeadViews.size();

    }

    /**
     * 是不是底部位置
     */
    private boolean isFooterPosition(int position) {
        return position >= (mHeadViews.size() + mDatas.size());
    }


    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
     *
     * @param recycler
     * @version 1.0
     */
    public void adjustSpanSize(RecyclerView recycler) {
        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter =
                            isHeaderPosition(position) || isFooterPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }


}
