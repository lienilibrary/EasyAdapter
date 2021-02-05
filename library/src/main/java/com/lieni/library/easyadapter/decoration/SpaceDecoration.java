package com.lieni.library.easyadapter.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lieni.library.easyadapter.BaseEasyAdapter;

public class SpaceDecoration extends RecyclerView.ItemDecoration {
    private int verticalSpace = 0;
    private int horizontalSpace = 0;
    private int spanCount = 0;

    public SpaceDecoration() {
    }

    public SpaceDecoration(int verticalSpace) {
        this(verticalSpace, 0, 1);
    }

    public SpaceDecoration(int verticalSpace, int horizontalSpace, int spanCount) {
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
        if (parent.getAdapter() == null) return;
        int position = parent.getChildAdapterPosition(view);
        int count;
        if (parent.getAdapter() instanceof BaseEasyAdapter) {
            BaseEasyAdapter adapter = (BaseEasyAdapter) parent.getAdapter();
            count = adapter.getListData().size();
            if (!adapter.isListItem(position)) return;//如果不是列表数据不添加分割线
        } else {
            count = parent.getAdapter().getItemCount();
        }
        int lines = (int) Math.ceil((double) count / spanCount);
        int columns = spanCount;
        //行
        if (lines > 1) {
            if (isFirstLine(position)) {
                outRect.bottom = verticalSpace / 2;
            } else if (isLastLine(position, lines)) {
                outRect.top = verticalSpace / 2;
            } else {
                outRect.top = verticalSpace / 2;
                outRect.bottom = verticalSpace / 2;
            }
        }
        //列
        if (columns > 1) {
            if (isFirstColumn(position, columns)) {
                outRect.right = horizontalSpace / 2;
            } else if (isLastColumn(position, columns)) {
                outRect.left = horizontalSpace / 2;
            } else {
                outRect.left = horizontalSpace / 2;
                outRect.right = horizontalSpace / 2;
            }
        }
    }

    private boolean isFirstLine(int position) {
        return position / spanCount == 0;
    }

    private boolean isLastLine(int position, int lines) {
        return position / spanCount == lines - 1;
    }

    private boolean isFirstColumn(int position, int columns) {
        return position % columns == 0;
    }

    private boolean isLastColumn(int position, int columns) {
        return position % columns == columns - 1;
    }

    public SpaceDecoration setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
        return this;
    }

    public SpaceDecoration setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
        return this;
    }
}
