package com.lieni.easyadapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;

import com.lieni.easyadapter.entity.User;
import com.lieni.library.easyadapter.BaseEasyAdapter;
import com.lieni.library.easyadapter.EasyHolder;
import com.lieni.library.easyadapter.helper.RecyclerViewHelper;
import com.lieni.library.easyadapter.listener.OnFirstRefreshListener;
import com.lieni.library.easyadapter.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BaseEasyAdapter<User> adapter;
    private int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter=new BaseEasyAdapter<User>(R.layout.item_test) {
            @Override
            public void onBind(@NonNull EasyHolder holder, int position) {
            }
        };
        adapter.setNoMoreText("没有了");
        RecyclerViewHelper.create(this,R.id.rvTest,R.id.spTest, adapter)
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
                .init().firstRefresh();
    }
    private void getData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<User> users=new ArrayList<>();
                for (int i=0;i<15;i++) {
                    users.add(new User("男", i));
                }
                adapter.notifyLoadingCompleted(users,page==1,page==3);
                page++;
            }
        },5000);
    }
}
