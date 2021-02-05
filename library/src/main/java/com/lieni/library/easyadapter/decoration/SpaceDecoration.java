package com.lieni.library.easyadapter.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lieni.library.easyadapter.BaseEasyAdapter;

public class SpaceDecoration extends RecyclerView.ItemDecoration {
    private int verticalSpace = 0;
    private int horizontalSpace = 0;
    private int spanCount = 0;
    private boolean horizontal = false;
    private boolean vertical = false;
    private boolean showHeader = false;

    public SpaceDecoration() {
    }

    public SpaceDecoration(int verticalSpace) {
        this(verticalSpace, 0, 1);
    }

    public SpaceDecoration(int verticalSpace, int horizontalSpace, int spanCount) {
        this.vertical = verticalSpace > 0;
        this.horizontal = horizontalSpace > 0;
        this.spanCount = spanCount;
        this.verticalSpace = verticalSpace;
        this.horizontalSpace = horizontalSpace;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //获取列数量
//        if(spanCount==0) {
//            RecyclerView.LayoutManager manager=parent.getLayoutManager();
//            if(manager instanceof GridLayoutManager) spanCount=((GridLayoutManager) manager).getSpanCount();
//            else if(manager instanceof LinearLayoutManager) spanCount=1;
//        }
        int position = parent.getChildAdapterPosition(view);
        //如果不是列表数据不添加分割线
        if (parent.getAdapter() instanceof BaseEasyAdapter) {
            BaseEasyAdapter adapter = (BaseEasyAdapter) parent.getAdapter();
            if (!adapter.isListItem(position)) return;
        }

        if (horizontal && vertical) {
            outRect.left = horizontalSpace;
            outRect.top = verticalSpace;
        } else if (horizontal) outRect.left = horizontalSpace;
        else if (vertical) outRect.top = verticalSpace;

        if (position % spanCount == 0) outRect.left = 0;
        if (position < spanCount && !showHeader) {
            outRect.top = 0;
        }
    }

    public SpaceDecoration setVerticalSpace(int verticalSpace) {
        vertical = true;
        this.verticalSpace = verticalSpace;
        return this;
    }

    public SpaceDecoration setHorizontalSpace(int horizontalSpace) {
        horizontal = true;
        this.horizontalSpace = horizontalSpace;
        return this;
    }

    public SpaceDecoration setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
        return this;
    }
}
