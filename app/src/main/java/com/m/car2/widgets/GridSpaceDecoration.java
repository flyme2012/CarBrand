package com.m.car2.widgets;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by zhenyu on 15/12/4.
 */
public class GridSpaceDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int spacingX;
    private int spacingY;
    private boolean includeEdge;

    public GridSpaceDecoration(int spanCount, int spacing, boolean includeEdge) {
        this(spanCount, spacing, spacing, includeEdge);
    }

    public GridSpaceDecoration(int spanCount, int spacingX, int spacingY, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacingX = spacingX;
        this.spacingY = spacingY;
        this.includeEdge = includeEdge;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column
        if (includeEdge) {
            outRect.left = spacingX - column * spacingX / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacingX / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
            Log.i("fzy", "getItemOffsets() pos:" + position + " column:" + column + " spacingX:" + spacingX + " left:" + outRect.left + " right:" + outRect.right);
            if (position < spanCount) { // top edge
                outRect.top = spacingY;
            }
            outRect.bottom = spacingY; // item bottom
        } else {
            outRect.left = column * spacingX / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacingX - (column + 1) * spacingX / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacingY; // item top
            }
        }
    }
}
