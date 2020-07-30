package com.lieni.library.easyadapter.helper;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lieni.library.easyadapter.BaseEasyAdapter;
import com.lieni.library.easyadapter.listener.OnItemChildClickListener;
import com.lieni.library.easyadapter.listener.OnRefreshListener;

/**
 * @author  Jason Ran
 * @date 2019/9/6
 */

public class RecyclerViewHelper {
    private Builder builder;
    private RecyclerViewHelper(Builder builder) {
        this.builder=builder;
        init();
    }
    private void init(){
        if(builder==null||builder.recyclerView==null||builder.easyAdapter==null) return;
        if(builder.layoutManager==null) builder.layoutManager=new LinearLayoutManager(builder.recyclerView.getContext());
        builder.recyclerView.setLayoutManager(builder.layoutManager);
        builder.recyclerView.setAdapter(builder.easyAdapter);

        if(builder.refreshListener==null) return;
        //设置加载失败点击事件
        builder.easyAdapter.setOnTopReloadListener(new OnItemChildClickListener() {
            @Override
            public void onClick(View v, int position) {
                builder.refreshListener.onPullDown();
            }
        });
        builder.easyAdapter.setOnBottomReloadListener(new OnItemChildClickListener() {
            @Override
            public void onClick(View v, int position) {
                builder.refreshListener.onLoadMore();
            }
        });

        //滑动到底部，加载更多判断
        if(builder.layoutManager instanceof LinearLayoutManager) {
            builder.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (recyclerView.getAdapter() == null || recyclerView.getLayoutManager() == null || !(recyclerView.getLayoutManager() instanceof LinearLayoutManager))
                        return;
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    BaseEasyAdapter adapter = (BaseEasyAdapter) recyclerView.getAdapter();

                    if (adapter.isWaitingForLoading() && newState == RecyclerView.SCROLL_STATE_IDLE &&
                            manager.findLastVisibleItemPosition() + 1 == adapter.getItemCount()) {
                        adapter.notifyLoading();
                        builder.refreshListener.onLoadMore();
                    }
                }
            });
        }

        //设置refreshLayout监听
        if(builder.refreshLayout!=null) {
            builder.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    builder.refreshListener.onPullDown();
                }
            });
            builder.easyAdapter.setRefreshLayout(builder.refreshLayout);
        }

    }

    /**
     * 首次刷新
     * @param anim 是否显示加载动画
     * @return
     */
    public RecyclerViewHelper firstRefresh(final boolean anim){
        if(builder.refreshLayout==null) return this;
        builder.refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(anim) builder.refreshLayout.setRefreshing(true);
                if(builder.refreshListener!=null) builder.refreshListener.onPullDown();
            }
        });
        return this;
    }

    public Builder getBuilder() {
        return builder;
    }

    public static class Builder{
        private RecyclerView recyclerView;
        private BaseEasyAdapter easyAdapter;
        private OnRefreshListener refreshListener;
        private RecyclerView.LayoutManager layoutManager;
        private SwipeRefreshLayout refreshLayout;

        public SwipeRefreshLayout getRefreshLayout() {
            return refreshLayout;
        }

        public Builder setRefreshLayout(SwipeRefreshLayout refreshLayout) {
            this.refreshLayout = refreshLayout;
            return this;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }

        public Builder setRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public BaseEasyAdapter getEasyAdapter() {
            return easyAdapter;
        }

        public Builder setEasyAdapter(BaseEasyAdapter easyAdapter) {
            this.easyAdapter = easyAdapter;
            return this;
        }

        public OnRefreshListener getRefreshListener() {
            return refreshListener;
        }

        public Builder setRefreshListener(OnRefreshListener refreshListener) {
            this.refreshListener = refreshListener;
            return this;
        }

        public RecyclerView.LayoutManager getLayoutManager() {
            return layoutManager;
        }

        public Builder setLayoutManager(RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            return this;
        }
        public RecyclerViewHelper build(){
            return new RecyclerViewHelper(this);
        }
    }

}
