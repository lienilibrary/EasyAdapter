package com.lieni.easyadapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.lieni.easyadapter.entity.User;
import com.lieni.library.easyadapter.BaseEasyAdapter;
import com.lieni.library.easyadapter.EasyHolder;
import com.lieni.library.easyadapter.helper.RecyclerViewHelper;
import com.lieni.library.easyadapter.listener.OnFirstRefreshListener;
import com.lieni.library.easyadapter.listener.OnItemChildClickListener;
import com.lieni.library.easyadapter.listener.OnItemChildLongClickListener;
import com.lieni.library.easyadapter.listener.OnItemClickListener;
import com.lieni.library.easyadapter.listener.OnItemLongClickListener;
import com.lieni.library.easyadapter.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFirstRefreshListener,OnLoadMoreListener{
    private BaseEasyAdapter<String> adapter;
    private int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        sample1();
//        sample2();
        sample3();
    }
    private void sample1(){
        BaseEasyAdapter<String> adapter=new BaseEasyAdapter<String>(R.layout.item_test) {
            @Override
            public void onBind(@NonNull EasyHolder holder, int position) {
            }
        };
        RecyclerViewHelper.create(this,R.id.recyclerView,adapter).init();
        adapter.notifyLoadingCompleted(null);//更新数据
    }
    private void sample2(){
        //adapter已全局定义
        adapter=new BaseEasyAdapter<String>(R.layout.item_test) {
            @Override
            public void onBind(@NonNull EasyHolder holder, int position) { }
        };
        //click
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        //long click
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onLongClick(View v, int position) {

            }
        });
        //child click
        adapter.addOnItemChildClickListener(R.id.tvText, new OnItemChildClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        //child long click
        adapter.addOnItemChildLongClickListener(R.id.tvText, new OnItemChildLongClickListener() {
            @Override
            public void onLongClick(View v, int position) {

            }
        });

        RecyclerViewHelper.create(this,R.id.recyclerView,adapter)
                .setOnFirstRefreshListener(new OnFirstRefreshListener() {
                    @Override
                    public void onFirstRefresh() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<String> strings=new ArrayList<>();
                                for (int i=0;i<15;i++) strings.add("string "+i);
                                adapter.notifyLoadingCompleted(strings);
                            }
                        },2000);
                    }
                })
                .init()
                .firstRefresh();
    }
    private void sample3(){
        //adapter已全局定义
        adapter=new BaseEasyAdapter<String>(R.layout.item_test) {
            @Override
            public void onBind(@NonNull EasyHolder holder, int position) { }
        };
        adapter.setNoMoreText("没有更多数据了");
        RecyclerViewHelper.create(this,R.id.recyclerView,R.id.swipeRefreshLayout, adapter)
                .setOnFirstRefreshListener(new OnFirstRefreshListener() {
                    @Override
                    public void onFirstRefresh() {
                        page=1;
                        getData();
                    }
                })
                .setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        getData();
                    }
                })
                .setLayoutManager(new GridLayoutManager(this,2))
                .addSpaceDecoration(40,100)
                .init().firstRefresh();
    }

    @Override
    public void onFirstRefresh() {
        page=1;
        getData();
    }

    @Override
    public void onLoadMore() {
        getData();
    }

    private void getData(){
//        adapter.notifyLoadingFailed();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> strings=new ArrayList<>();
                for (int i=0;i<20;i++) {
                    strings.add("string "+i);
                }
                adapter.notifyLoadingCompleted(strings,page==1,page==3);
                page++;
            }
        },1000);
    }

}
