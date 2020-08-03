package com.lieni.library.easyadapter.model;

public class AdapterSetting {
    private int emptyLayoutResId;
    private int centerErrorLayoutResId;
    private int centerLoadingLayoutResId;
    private int bottomLoadingLayoutResId;
    private int bottomErrorLayoutResId;
    private int bottomCompleteLayoutResId;
    private int defaultLayoutResId;

    private String emptyText;
    private String noMoreText;

    private int emptyImage;
    private int netErrorImage;

    public AdapterSetting() {
        this.emptyLayoutResId = EasyAdapterSetting.EMPTY_LAYOUT;
        this.centerErrorLayoutResId = EasyAdapterSetting.CENTER_ERROR_LAYOUT;
        this.centerLoadingLayoutResId = EasyAdapterSetting.CENTER_LOADING_LAYOUT;
        this.bottomErrorLayoutResId = EasyAdapterSetting.BOTTOM_ERROR_LAYOUT;
        this.bottomLoadingLayoutResId = EasyAdapterSetting.BOTTOM_LOADING_LAYOUT;
        this.bottomCompleteLayoutResId= EasyAdapterSetting.BOTTOM_COMPLETE_LAYOUT;
        this.defaultLayoutResId= EasyAdapterSetting.DEFAULT_LAYOUT;

        this.emptyText= EasyAdapterSetting.EMPTY_TEXT;
        this.noMoreText= EasyAdapterSetting.NO_MORE_TEXT;

        this.emptyImage= EasyAdapterSetting.EMPTY_IMAGE;
        this.netErrorImage= EasyAdapterSetting.NET_ERROR_IMAGE;
    }

    public int getEmptyLayoutResId() {
        return emptyLayoutResId;
    }

    public AdapterSetting setEmptyLayoutResId(int emptyLayoutResId) {
        this.emptyLayoutResId = emptyLayoutResId;
        return this;
    }

    public int getCenterErrorLayoutResId() {
        return centerErrorLayoutResId;
    }

    public AdapterSetting setCenterErrorLayoutResId(int centerErrorLayoutResId) {
        this.centerErrorLayoutResId = centerErrorLayoutResId;
        return this;
    }

    public int getCenterLoadingLayoutResId() {
        return centerLoadingLayoutResId;
    }

    public AdapterSetting setCenterLoadingLayoutResId(int centerLoadingLayoutResId) {
        this.centerLoadingLayoutResId = centerLoadingLayoutResId;
        return this;
    }

    public int getBottomLoadingLayoutResId() {
        return bottomLoadingLayoutResId;
    }

    public AdapterSetting setBottomLoadingLayoutResId(int bottomLoadingLayoutResId) {
        this.bottomLoadingLayoutResId = bottomLoadingLayoutResId;
        return this;
    }

    public int getBottomErrorLayoutResId() {
        return bottomErrorLayoutResId;
    }

    public AdapterSetting setBottomErrorLayoutResId(int bottomErrorLayoutResId) {
        this.bottomErrorLayoutResId = bottomErrorLayoutResId;
        return this;
    }

    public int getBottomCompleteLayoutResId() {
        return bottomCompleteLayoutResId;
    }

    public AdapterSetting setBottomCompleteLayoutResId(int bottomCompleteLayoutResId) {
        this.bottomCompleteLayoutResId = bottomCompleteLayoutResId;
        return this;
    }

    public int getDefaultLayoutResId() {
        return defaultLayoutResId;
    }

    public AdapterSetting setDefaultLayoutResId(int defaultLayoutResId) {
        this.defaultLayoutResId = defaultLayoutResId;
        return this;
    }

    public String getEmptyText() {
        return emptyText;
    }

    public AdapterSetting setEmptyText(String emptyText) {
        this.emptyText = emptyText;
        return this;
    }

    public String getNoMoreText() {
        return noMoreText;
    }

    public AdapterSetting setNoMoreText(String noMoreText) {
        this.noMoreText = noMoreText;
        return this;
    }

    public int getEmptyImage() {
        return emptyImage;
    }

    public AdapterSetting setEmptyImage(int emptyImage) {
        this.emptyImage = emptyImage;
        return this;
    }

    public int getNetErrorImage() {
        return netErrorImage;
    }

    public AdapterSetting setNetErrorImage(int netErrorImage) {
        this.netErrorImage = netErrorImage;
        return this;
    }
}
