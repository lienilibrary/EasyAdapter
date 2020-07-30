package com.lieni.library.easyadapter.model;


import com.lieni.library.easyadapter.R;

public class AdapterSetting {
    private int emptyLayoutResId;
    private int topErrorLayoutResId;
    private int bottomErrorLayoutResId;
    private int topLoadingLayoutResId;
    private int bottomLoadingLayoutResId;
    private int endLayoutResId;
    private int defaultLayoutResId;



    public AdapterSetting() {
        this.emptyLayoutResId = R.layout.item_footer_empty;
        this.topErrorLayoutResId = R.layout.item_footer_net_error_top;
        this.bottomErrorLayoutResId = R.layout.item_footer_net_error_bottom;
        this.topLoadingLayoutResId = R.layout.item_footer_loading_top;
        this.bottomLoadingLayoutResId = R.layout.item_footer_loading_bottom;
        this.endLayoutResId=R.layout.item_footer_end;
        this.defaultLayoutResId=R.layout.item_footer_default;

    }

    public int getEmptyLayoutResId() {
        return emptyLayoutResId;
    }

    public void setEmptyLayoutResId(int emptyLayoutResId) {
        this.emptyLayoutResId = emptyLayoutResId;
    }

    public int getTopErrorLayoutResId() {
        return topErrorLayoutResId;
    }

    public void setTopErrorLayoutResId(int topErrorLayoutResId) {
        this.topErrorLayoutResId = topErrorLayoutResId;
    }

    public int getBottomErrorLayoutResId() {
        return bottomErrorLayoutResId;
    }

    public void setBottomErrorLayoutResId(int bottomErrorLayoutResId) {
        this.bottomErrorLayoutResId = bottomErrorLayoutResId;
    }

    public int getTopLoadingLayoutResId() {
        return topLoadingLayoutResId;
    }

    public void setTopLoadingLayoutResId(int topLoadingLayoutResId) {
        this.topLoadingLayoutResId = topLoadingLayoutResId;
    }

    public int getBottomLoadingLayoutResId() {
        return bottomLoadingLayoutResId;
    }

    public void setBottomLoadingLayoutResId(int bottomLoadingLayoutResId) {
        this.bottomLoadingLayoutResId = bottomLoadingLayoutResId;
    }

    public int getEndLayoutResId() {
        return endLayoutResId;
    }

    public void setEndLayoutResId(int endLayoutResId) {
        this.endLayoutResId = endLayoutResId;
    }

    public int getDefaultLayoutResId() {
        return defaultLayoutResId;
    }

    public void setDefaultLayoutResId(int defaultLayoutResId) {
        this.defaultLayoutResId = defaultLayoutResId;
    }
}
