package com.example.wangjingyun.commonrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wangjingyun.commonrecycleviewsdk.adapter.CommonRecycleViewAdapter;
import com.example.wangjingyun.commonrecycleviewsdk.adapter.MultiItemCommonAdapter;
import com.example.wangjingyun.commonrecycleviewsdk.listener.MultiItemTypeListener;
import com.example.wangjingyun.commonrecycleviewsdk.viewholder.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CommonRecycleViewAdapter.OnItemClickListener{

    private RecyclerView recyclerView;

   // private CommonRecycleViewAdapter<String> adapter;

    private MultiItemCommonAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //http://blog.csdn.net/lmj623565791/article/details/51118836/

        List<String> datas=new ArrayList<String>();

        for(int i=1;i<=100;i++){

            datas.add(""+i);

        }
        recyclerView= (RecyclerView) findViewById(R.id.recycleview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

   //     recyclerView.setLayoutManager(new GridLayoutManager(this,3));

       /* 单布局 adapter
       adapter=new CommonRecycleViewAdapter<String>(this,datas,R.layout.item_main_layout) {

            @Override
            public void convert(CommonViewHolder holder, String s) {

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

        View view = LayoutInflater.from(this).inflate(R.layout.item_mainhead_layout, recyclerView,false);



        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);

        adapter.addHeadViews(view);
        adapter.addFooterViews(view);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_SHORT).show();
    }
}
