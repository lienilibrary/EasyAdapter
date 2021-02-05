package com.lieni.library.easyadapter.helper;


import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lieni.library.easyadapter.BaseEasyAdapter;
import com.lieni.library.easyadapter.decoration.SpaceDecoration;
import com.lieni.library.easyadapter.listener.OnFirstRefreshListener;
import com.lieni.library.easyadapter.listener.OnItemChildClickListener;
import com.lieni.library.easyadapter.listener.OnLoadMoreListener;
import com.lieni.library.easyadapter.util.UnitUtil;

/**
 * @author Jason Ran
 * @date 2019/9/6
 */

public class RecyclerViewHelper {
    private Builder builder;

    private RecyclerViewHelper(Builder builder) {
        this.builder = builder;
        init();
    }

    private void init() {
        if (builder == null || builder.recyclerView == null || builder.adapter == null) return;
        if (builder.manager == null) {
            builder.manager = new LinearLayoutManager(builder.recyclerView.getContext());
        }

        builder.recyclerView.setLayoutManager(builder.manager);
        builder.recyclerView.setAdapter(builder.adapter);
        if (builder.decoration != null) {
            builder.recyclerView.addItemDecoration(builder.decoration);
        }

        if (builder.onLoadMoreListener != null) {
            //底部加载失败点击事件
            builder.adapter.setOnBottomReloadListener(new OnItemChildClickListener() {
                @Override
                public void onClick(View v, int position) {
                    builder.onLoadMoreListener.onLoadMore();
                }
            });

            //滑动到底部，加载更多判断
            if (builder.manager instanceof LinearLayoutManager) {
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
                            builder.onLoadMoreListener.onLoadMore();
                        }
                    }
                });
            }
        }

        if (builder.onFirstRefreshListener != null) {
            //设置加载失败点击事件
            builder.adapter.setOnTopReloadListener(new OnItemChildClickListener() {
                @Override
                public void onClick(View v, int position) {
                    builder.onFirstRefreshListener.onFirstRefresh();
                }
            });
            //设置refreshLayout监听
            if (builder.refreshLayout != null) {
                builder.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        builder.onFirstRefreshListener.onFirstRefresh();
                    }
                });
                builder.adapter.setRefreshLayout(builder.refreshLayout);
            }
        }

    }

    /**
     * 首次刷新
     *
     * @param anim 是否显示加载动画
     * @return
     */
    public RecyclerViewHelper firstRefresh(final boolean anim) {
        if (builder.refreshLayout == null) {
            builder.adapter.notifyLoading();
            if (builder.onFirstRefreshListener != null) {
                builder.onFirstRefreshListener.onFirstRefresh();
            }
        } else {
            builder.refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    if (anim) {
                        builder.refreshLayout.setRefreshing(true);
                    }
                    if (builder.onFirstRefreshListener != null) {
                        builder.onFirstRefreshListener.onFirstRefresh();
                    }
                }
            });
        }
        return this;
    }

    public RecyclerViewHelper firstRefresh() {
        return firstRefresh(true);
    }

    public Builder getBuilder() {
        return builder;
    }

    public static Builder create(RecyclerView recyclerView, BaseEasyAdapter adapter) {
        return create(recyclerView, null, adapter);
    }

    public static Builder create(Activity activity, int recyclerViewResId, BaseEasyAdapter adapter) {
        return create(activity.getWindow().getDecorView(), recyclerViewResId, adapter);
    }

    public static Builder create(Activity activity, int recyclerViewResId, int refreshLayoutResId, BaseEasyAdapter adapter) {
        return create(activity.getWindow().getDecorView(), recyclerViewResId, refreshLayoutResId, adapter);
    }

    public static Builder create(View rootView, int recyclerViewResId, BaseEasyAdapter adapter) {
        return create(rootView, recyclerViewResId, 0, adapter);
    }

    public static Builder create(View rootView, int recyclerViewResId, int refreshLayoutResId, BaseEasyAdapter adapter) {
        RecyclerView recyclerView = rootView.findViewById(recyclerViewResId);
        SwipeRefreshLayout swipeRefreshLayout = refreshLayoutResId > 0 ? (SwipeRefreshLayout) rootView.findViewById(refreshLayoutResId) : null;
        return create(recyclerView, swipeRefreshLayout, adapter);
    }

    public static Builder create(RecyclerView recyclerView, SwipeRefreshLayout refreshLayout, BaseEasyAdapter adapter) {
        Builder builder = new Builder();
        builder.setRecyclerView(recyclerView);
        builder.setAdapter(adapter);
        builder.setRefreshLayout(refreshLayout);
        builder.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        return builder;
    }

    public static class Builder {
        private RecyclerView recyclerView;
        private BaseEasyAdapter adapter;
        private OnFirstRefreshListener onFirstRefreshListener;
        private OnLoadMoreListener onLoadMoreListener;
        private RecyclerView.LayoutManager manager;
        private SwipeRefreshLayout refreshLayout;
        private RecyclerView.ItemDecoration decoration;

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

        public BaseEasyAdapter getAdapter() {
            return adapter;
        }

        public Builder setAdapter(BaseEasyAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public OnFirstRefreshListener getOnFirstRefreshListener() {
            return onFirstRefreshListener;
        }

        public Builder setOnFirstRefreshListener(OnFirstRefreshListener onFirstRefreshListener) {
            this.onFirstRefreshListener = onFirstRefreshListener;
            return this;
        }

        public OnLoadMoreListener getOnLoadMoreListener() {
            return onLoadMoreListener;
        }

        public Builder setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
            return this;
        }

        public RecyclerView.LayoutManager getLayoutManager() {
            return manager;
        }

        public Builder setLayoutManager(final RecyclerView.LayoutManager layoutManager) {
            //适配多布局底部和顶部
            if (layoutManager instanceof GridLayoutManager) {
                ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (adapter.isListItem(position)) return 1;
                        else return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                });
            }
            this.manager = layoutManager;
            return this;
        }

        //禁用手动刷新
        public Builder disableRefreshLayout() {
            if (refreshLayout != null) refreshLayout.setEnabled(false);
            return this;
        }

        public RecyclerView.ItemDecoration getDecoration() {
            return decoration;
        }

        public Builder setDecoration(RecyclerView.ItemDecoration decoration) {
            this.decoration = decoration;
            return this;
        }

        public Builder addSpaceDecoration(int verticalSpace) {
            return addSpaceDecoration(verticalSpace, 0);
        }

        public Builder addSpaceDecoration(int verticalSpace, int horizontalSpace) {
            return addSpaceDecoration(verticalSpace, horizontalSpace, true);
        }

        public Builder addSpaceDecoration(int verticalSpace, int horizontalSpace, boolean dp) {
            int spanCount = 1;
            if (manager instanceof GridLayoutManager) {
                spanCount = ((GridLayoutManager) manager).getSpanCount();
            }
            if (dp) {
                verticalSpace = UnitUtil.dpToPx(recyclerView.getContext(), verticalSpace);
                horizontalSpace = UnitUtil.dpToPx(recyclerView.getContext(), horizontalSpace);
            }
            this.decoration = new SpaceDecoration(verticalSpace, horizontalSpace, spanCount);
            return this;
        }

        public RecyclerViewHelper init() {
            return new RecyclerViewHelper(this);
        }
    }

}
