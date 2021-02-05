package com.lieni.library.easyadapter;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lieni.library.easyadapter.listener.OnItemChildClickListener;
import com.lieni.library.easyadapter.listener.OnItemChildLongClickListener;
import com.lieni.library.easyadapter.listener.OnItemClickListener;
import com.lieni.library.easyadapter.listener.OnItemLongClickListener;
import com.lieni.library.easyadapter.model.AdapterSetting;

/**
 * @author Jason Ran
 * @date 2019/9/6
 */

public abstract class BaseEasyAdapter<E> extends RecyclerView.Adapter<EasyHolder> {
    private List<E> listData = new ArrayList<>();
    private int footerItemType = ITEM_TYPE_FOOT_DEFAULT;

    private boolean showFooter = true;

    private SparseArray<OnItemChildClickListener> clickListeners;
    private SparseArray<OnItemChildLongClickListener> longClickListeners;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemChildClickListener onTopReloadListener, onBottomReloadListener;
    private int currentState = STATE_DEFAULT;
    private int layoutResId;

    private SwipeRefreshLayout refreshLayout;
    private AdapterSetting setting;

    //1 初始状态 2 等待加载状态 3 加载失败状态 4 完结状态

    private static final int STATE_DEFAULT = 0;
    private static final int STATE_WAIT_FOR_LOADING = 1;
    private static final int STATE_LOADING = 2;
    private static final int STATE_LOADING_FAILED = 3;
    private static final int STATE_END = 4;

    private static final int ITEM_TYPE_LIST = 20;
    private static final int ITEM_TYPE_FOOT_DEFAULT = 29;
    private static final int ITEM_TYPE_FOOT_END = 30;
    private static final int ITEM_TYPE_FOOT_LOADING_BOTTOM = 31;
    private static final int ITEM_TYPE_FOOT_LOADING_TOP = 32;
    private static final int ITEM_TYPE_FOOT_EMPTY = 33;
    private static final int ITEM_TYPE_FOOT_NET_ERROR_BOTTOM = 34;
    private static final int ITEM_TYPE_FOOT_NET_ERROR_TOP = 35;

    public BaseEasyAdapter() {
        this(0);
    }

    public BaseEasyAdapter(int layoutResId) {
        this(layoutResId, new AdapterSetting());
    }

    public BaseEasyAdapter(int layoutResId, AdapterSetting setting) {
        this.layoutResId = layoutResId;
        this.setting = setting;
        clickListeners = new SparseArray<>();
        longClickListeners = new SparseArray<>();
    }

    /**
     * 更新数据
     *
     * @param data 数据源
     */
    public void updateData(List<E> data) {
        listData.clear();
        if (data != null) {
            listData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void updateData(E item, int position) {
        if (position < listData.size() && position > -1 && item != null) {
            listData.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void addData(E item) {
        addData(item, listData.size());
    }

    public void addData(E item, int position) {
        if (position <= listData.size() && position > -1 && item != null) {
            if (listData.size() == 0) {
                setFooterItemType(ITEM_TYPE_FOOT_END);
            }
            listData.add(position, item);
            notifyItemInserted(position);
        }
    }

    public void addData(List<E> data) {
        if (data != null) {
            listData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public E getItem(int position) {
        return listData.get(position);
    }

    public void removeItem(int position) {
        if (position < listData.size() && position > -1) {
            listData.remove(position);
            notifyItemRemoved(position);
            if (listData.size() == 0) {
                setFooterItemType(ITEM_TYPE_FOOT_EMPTY);
            }
        }
    }

    /**
     * 页脚数据变更
     *
     * @param type 页脚类型
     */
    private void setFooterItemType(int type) {
        if (showFooter) {
            this.footerItemType = type;
            notifyItemChanged(listData.size());
        }
    }

    /**
     * 设置当前状态
     *
     * @param state 状态
     */
    public void setCurrentState(int state) {
        this.currentState = state;
        switch (state) {
            case STATE_LOADING:
            case STATE_WAIT_FOR_LOADING:
                setFooterItemType(listData.size() == 0 ? ITEM_TYPE_FOOT_LOADING_TOP : ITEM_TYPE_FOOT_LOADING_BOTTOM);
                break;
            case STATE_LOADING_FAILED:
                setFooterItemType(listData.size() == 0 ? ITEM_TYPE_FOOT_NET_ERROR_TOP : ITEM_TYPE_FOOT_NET_ERROR_BOTTOM);
                break;
            case STATE_END:
                setFooterItemType(listData.size() == 0 ? ITEM_TYPE_FOOT_EMPTY : ITEM_TYPE_FOOT_END);
                break;
            default:
                break;
        }
    }

    /**
     * 加载失败
     */
    public void notifyLoadingFailed() {
        setCurrentState(STATE_LOADING_FAILED);
        stopRefreshing();
    }

    public void notifyLoadingCompleted(List<E> data) {
        notifyLoadingCompleted(data, true, true);
    }

    /**
     * 加载完成
     * data 数据源
     * clear 是否清空
     * end 是否结束
     */
    public void notifyLoadingCompleted(List<E> data, boolean clear, boolean end) {
        if (clear) {
            updateData(data);
        } else {
            addData(data);
        }
        setCurrentState(end ? STATE_END : STATE_WAIT_FOR_LOADING);
        stopRefreshing();
    }

    /**
     * 加载中
     */
    public void notifyLoading() {
        setCurrentState(STATE_LOADING);
    }


    public boolean isWaitingForLoading() {
        return this.currentState == STATE_WAIT_FOR_LOADING;
    }

    private void stopRefreshing() {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (showFooter) {
            return listData.size() + 1;
        } else {
            return listData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && showFooter) {
            return footerItemType;
        } else {
            return ITEM_TYPE_LIST;
        }
    }

    public boolean isListItem(int position) {
        return getItemViewType(position) == ITEM_TYPE_LIST;
    }


    @NonNull
    @Override
    public EasyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LIST) {
            return new EasyHolder(LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false))
                    .setOnItemClickListener(onItemClickListener)
                    .addOnItemChildClickListeners(clickListeners)
                    .setOnItemLongClickListener(onItemLongClickListener)
                    .addOnItemChildLongClickListeners(longClickListeners);
        } else {
            switch (viewType) {
                case ITEM_TYPE_FOOT_END:
                    return new EasyHolder(LayoutInflater.from(parent.getContext()).inflate(setting.getBottomCompleteLayoutResId(), parent, false));
                case ITEM_TYPE_FOOT_LOADING_TOP:
                    return new EasyHolder(LayoutInflater.from(parent.getContext()).inflate(setting.getCenterLoadingLayoutResId(), parent, false));
                case ITEM_TYPE_FOOT_LOADING_BOTTOM:
                    return new EasyHolder(LayoutInflater.from(parent.getContext()).inflate(setting.getBottomLoadingLayoutResId(), parent, false));
                case ITEM_TYPE_FOOT_EMPTY:
                    return new EasyHolder(LayoutInflater.from(parent.getContext()).inflate(setting.getEmptyLayoutResId(), parent, false));
                case ITEM_TYPE_FOOT_NET_ERROR_TOP:
                    EasyHolder topErrorHolder = new EasyHolder(LayoutInflater.from(parent.getContext()).inflate(setting.getCenterErrorLayoutResId(), parent, false));
                    if (onTopReloadListener != null) {
                        topErrorHolder.addOnItemChildClickListener(R.id.item_footer_error_reload, onTopReloadListener);
                    }
                    return topErrorHolder;
                case ITEM_TYPE_FOOT_NET_ERROR_BOTTOM:
                    EasyHolder bottomErrorHolder = new EasyHolder(LayoutInflater.from(parent.getContext()).inflate(setting.getBottomErrorLayoutResId(), parent, false));
                    if (onBottomReloadListener != null) {
                        bottomErrorHolder.addOnItemChildClickListener(R.id.item_footer_error_reload, onBottomReloadListener);
                    }
                    return bottomErrorHolder;
                default:
                    return new EasyHolder(LayoutInflater.from(parent.getContext()).inflate(setting.getDefaultLayoutResId(), parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull EasyHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case ITEM_TYPE_FOOT_END:
                View completeText = holder.getView(R.id.item_footer_complete_text);
                if (completeText instanceof TextView) {
                    ((TextView) completeText).setText(setting.getNoMoreText() != null ? setting.getNoMoreText() : "");
                    completeText.setVisibility(TextUtils.isEmpty(setting.getNoMoreText()) ? View.GONE : View.VISIBLE);
                }
                break;
            case ITEM_TYPE_FOOT_EMPTY:
                View emptyText = holder.getView(R.id.item_footer_empty_text);
                if (emptyText instanceof TextView) {
                    ((TextView) emptyText).setText(setting.getEmptyText());
                }
                View emptyImage = holder.getView(R.id.item_footer_empty_image);
                if (emptyImage instanceof ImageView) {
                    ((ImageView) emptyImage).setImageResource(setting.getEmptyImage());
                }
                break;
            case ITEM_TYPE_FOOT_NET_ERROR_TOP:
                View errorImage = holder.getView(R.id.item_footer_error_image);
                if (errorImage instanceof ImageView) {
                    ((ImageView) errorImage).setImageResource(setting.getNetErrorImage());
                }
                break;
            case ITEM_TYPE_LIST:
                onBind(holder, position);
                break;
            default:
                break;
        }
    }

    /**
     * 绑定holder
     *
     * @param holder   holder
     * @param position position
     */
    public abstract void onBind(@NonNull EasyHolder holder, int position);

    public List<E> getListData() {
        return listData;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    public BaseEasyAdapter addOnItemChildClickListener(@IdRes int viewId, OnItemChildClickListener listener) {
        clickListeners.put(viewId, listener);
        return this;
    }

    public BaseEasyAdapter addOnItemChildLongClickListener(@IdRes int viewId, OnItemChildLongClickListener listener) {
        longClickListeners.put(viewId, listener);
        return this;
    }

    public BaseEasyAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public BaseEasyAdapter setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        return this;
    }

    public BaseEasyAdapter setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
        return this;
    }

    public BaseEasyAdapter setOnTopReloadListener(OnItemChildClickListener onTopReloadListener) {
        this.onTopReloadListener = onTopReloadListener;
        return this;
    }

    public BaseEasyAdapter setOnBottomReloadListener(OnItemChildClickListener onBottomReloadListener) {
        this.onBottomReloadListener = onBottomReloadListener;
        return this;
    }

    public BaseEasyAdapter setRefreshLayout(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
        return this;
    }

    public BaseEasyAdapter setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
        return this;
    }

    public BaseEasyAdapter setEmptyText(String emptyText) {
        this.setting.setEmptyText(emptyText);
        return this;
    }

    public BaseEasyAdapter setEmptyImage(int emptyImage) {
        this.setting.setEmptyImage(emptyImage);
        return this;
    }

    public BaseEasyAdapter setNoMoreText(String text) {
        this.setting.setNoMoreText(text);
        return this;
    }

}
