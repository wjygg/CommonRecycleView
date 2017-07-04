package com.example.wangjingyun.commonrecycleviewsdk.listener;

/**
 * Created by Administrator on 2017/7/4.
 */

public interface MultiItemTypeListener {

    //布局的id
    int getLayoutId(int itemType);
    //布局的类型
    int getItemViewType(int position);

}
