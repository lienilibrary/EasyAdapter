package com.lieni.library.easyadapter;

import android.util.SparseArray;
import android.view.View;

import com.lieni.library.easyadapter.listener.OnItemChildClickListener;
import com.lieni.library.easyadapter.listener.OnItemChildLongClickListener;
import com.lieni.library.easyadapter.listener.OnItemClickListener;
import com.lieni.library.easyadapter.listener.OnItemLongClickListener;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EasyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private SparseArray<View> views;
    private SparseArray<OnItemChildClickListener> clickListeners;
    private OnItemClickListener onItemClickListener;

    private SparseArray<OnItemChildLongClickListener> longClickListeners;
    private OnItemLongClickListener onItemLongClickListener;
    public EasyHolder(@NonNull View itemView) {
        super(itemView);
        this.views=new SparseArray<>();
        this.clickListeners=new SparseArray<>();
        this.longClickListeners=new SparseArray<>();

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    protected EasyHolder addOnItemChildClickListener(@IdRes int viewId, OnItemChildClickListener listener){
        View view=getView(viewId);
        view.setOnClickListener(this);
        clickListeners.put(viewId,listener);
        return this;
    }
    protected EasyHolder addOnItemChildClickListeners(SparseArray<OnItemChildClickListener> listeners){
        for(int i=0;i<listeners.size();i++){
            int key=listeners.keyAt(i);
            addOnItemChildClickListener(key,listeners.get(key));
        }
        return this;
    }

    protected EasyHolder addOnItemChildLongClickListener(@IdRes int viewId, OnItemChildLongClickListener listener){
        View view=getView(viewId);
        view.setOnLongClickListener(this);
        longClickListeners.put(viewId,listener);
        return this;
    }
    protected EasyHolder addOnItemChildLongClickListeners(SparseArray<OnItemChildLongClickListener> listeners){
        for(int i=0;i<listeners.size();i++){
            int key=listeners.keyAt(i);
            addOnItemChildLongClickListener(key,listeners.get(key));
        }
        return this;
    }

    protected EasyHolder setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public EasyHolder setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        return this;
    }

    @Override
    public void onClick(View v) {
        OnItemChildClickListener onItemChildClickListener=clickListeners.get(v.getId());
        if(onItemChildClickListener!=null) {
            onItemChildClickListener.onClick(v,getAdapterPosition());
        }
        else if(onItemClickListener!=null) {
            onItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        OnItemChildLongClickListener onItemChildLongClickListener=longClickListeners.get(v.getId());
        if(onItemChildLongClickListener!=null){
            onItemChildLongClickListener.onLongClick(v,getAdapterPosition());
            return true;
        }else if(onItemLongClickListener!=null){
            onItemLongClickListener.onLongClick(v,getAdapterPosition());
            return true;
        }

        return false;
    }
}
