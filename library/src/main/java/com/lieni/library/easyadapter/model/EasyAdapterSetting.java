package com.lieni.library.easyadapter.model;

import com.lieni.library.easyadapter.R;

public class EasyAdapterSetting {
    public static int EMPTY_LAYOUT= R.layout.item_footer_empty;
    public static int CENTER_ERROR_LAYOUT= R.layout.item_footer_net_error_top;
    public static int CENTER_LOADING_LAYOUT= R.layout.item_footer_loading_top;
    public static int BOTTOM_ERROR_LAYOUT=R.layout.item_footer_net_error_bottom;
    public static int BOTTOM_LOADING_LAYOUT=R.layout.item_footer_loading_bottom;
    public static int BOTTOM_COMPLETE_LAYOUT=R.layout.item_footer_end;
    public static int DEFAULT_LAYOUT=R.layout.item_footer_default;

    public static String EMPTY_TEXT="暂时还没有数据";
    public static String NO_MORE_TEXT="没有更多数据了";
    public static int EMPTY_IMAGE=R.drawable.ic_empty;
    public static int NET_ERROR_IMAGE=R.drawable.ic_net_error;

    public static void initLayout(int emptyLayout,int centerErrorLayout,int centerLoadingLayout,
                            int bottomErrorLayout,int bottomLoadingLayout,int bottomCompleteLayout){
        EMPTY_LAYOUT=emptyLayout;
        CENTER_ERROR_LAYOUT=centerErrorLayout;
        CENTER_LOADING_LAYOUT=centerLoadingLayout;
        BOTTOM_ERROR_LAYOUT=bottomErrorLayout;
        BOTTOM_LOADING_LAYOUT=bottomLoadingLayout;
        BOTTOM_COMPLETE_LAYOUT=bottomCompleteLayout;
    }
    public static void initText(String emptyText,String noMoreText){
        EMPTY_TEXT=emptyText;
        NO_MORE_TEXT=noMoreText;
    }
    public static void initImage(int emptyImage,int netErrorImage){
        EMPTY_IMAGE=emptyImage;
        NET_ERROR_IMAGE=netErrorImage;
    }
}
