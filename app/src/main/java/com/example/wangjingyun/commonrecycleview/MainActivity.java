package com.example.wangjingyun.commonrecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.wangjingyun.commonrecycleviewsdk.adapter.CommonRecycleViewAdapter;
import com.example.wangjingyun.commonrecycleviewsdk.adapter.MultiItemCommonAdapter;
import com.example.wangjingyun.commonrecycleviewsdk.decoration.LinnerItemDecoration;
import com.example.wangjingyun.commonrecycleviewsdk.listener.MultiItemTypeListener;
import com.example.wangjingyun.commonrecycleviewsdk.recycleview.LoadRefreshRecycleView;
import com.example.wangjingyun.commonrecycleviewsdk.recycleview.RefreshRecycleView;
import com.example.wangjingyun.commonrecycleviewsdk.viewholder.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CommonRecycleViewAdapter.OnItemClickListener,RefreshRecycleView.OnRefreshListener,LoadRefreshRecycleView.OnLoadMoreListener{

    private LoadRefreshRecycleView recyclerView;

   // private CommonRecycleViewAdapter<String> adapter;

    private MultiItemCommonAdapter<String> adapter;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            recyclerView.onStopLoad();
            Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //http://blog.csdn.net/lmj623565791/article/details/51118836/

        List<String> datas=new ArrayList<String>();

        for(int i=1;i<=10;i++){

            datas.add(""+i);

        }
       recyclerView= (LoadRefreshRecycleView) findViewById(R.id.recycleview);
    //    recyclerView.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
       recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


       // 单布局 adapter
      /* adapter=new CommonRecycleViewAdapter<String>(this,datas,R.layout.item_main_layout) {


           @Override
           public void convert(CommonViewHolder holder, int position, String s) {

               holder.setText(R.id.text,s);
           }
       };*/

       //多布局adapter
       adapter=new MultiItemCommonAdapter<String>(this, datas, new MultiItemTypeListener() {
           @Override
           public int getLayoutId(int itemType) {

               if(itemType==R.layout.item_mulitype_layout){

                   return  R.layout.item_mulitype_layout;
               }

               return R.layout.item_main_layout;

           }

           @Override
           public int getItemViewType(int position) {

               if(position==0){

                   return  R.layout.item_mulitype_layout;
               }

               return R.layout.item_main_layout;
           }
       }) {
           @Override

           public void convert(CommonViewHolder holder,int position, String s) {

               if(position!=0){

                   holder.setText(R.id.text,s);
               }
           }
       };

        View view = LayoutInflater.from(this).inflate(R.layout.item_refresh_layout, recyclerView,false);

        View view1 = LayoutInflater.from(this).inflate(R.layout.item_twomainhead_layout, recyclerView,false);

        adapter.setOnItemClickListener(this);

   //     recyclerView.addItemDecoration(new GridItemDecoration(getResources().getDrawable(R.drawable.line_shape)));


        recyclerView.setAdapter(adapter);

        recyclerView.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
      //  recyclerView.addHeadView(view);
     //   recyclerView.addHeadView(view1);
       // recyclerView.addFooterView(view);
        //recyclerView.addFooterView(view1);
       // recyclerView.addRefreshViewCreator(new RefreshCreator()); 自定义下拉布局动画
        recyclerView.addItemDecoration(new LinnerItemDecoration(getResources().getDrawable(R.drawable.line_shape)));


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
      //正在 刷新  请求接口

        //停止刷新
        recyclerView.onStopRefresh();
    }

    @Override
    public void onLoad() {

        Toast.makeText(MainActivity.this,"请求中",Toast.LENGTH_SHORT).show();
        new Thread(){

            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    Message msg=new Message();
                    handler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
