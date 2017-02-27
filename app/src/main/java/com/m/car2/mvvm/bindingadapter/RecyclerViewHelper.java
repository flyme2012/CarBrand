package com.m.car2.mvvm.bindingadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.m.car2.R;
import com.m.car2.utility.EscapeProguard;
import com.m.car2.widgets.DividerItemDecoration;
import com.m.car2.widgets.GridSpaceDecoration;

/**
 * Created by zhenyu on 16/12/16.
 */

public class RecyclerViewHelper implements EscapeProguard {

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    public static RecyclerView.LayoutManager linearLayoutManager(Context context, int orientation) {
        return new LinearLayoutManager(context, orientation, false);
    }


    public static RecyclerView.LayoutManager gridLayoutManager(Context context, int spanCount, int orientation) {
        return new GridLayoutManager(context, spanCount, orientation, false);
    }


    public static RecyclerView.LayoutManager staggeredGridLayoutManager(int orientation, int spanCount) {
        return new StaggeredGridLayoutManager(spanCount, orientation);
    }

    public static RecyclerView.ItemDecoration gridSpaceDecoration(int spanCount, int spacing, boolean includeEdge) {
        return new GridSpaceDecoration(spanCount, spacing, includeEdge);
    }

    public static RecyclerView.ItemDecoration gridSpaceDecoration(int spanCount, int spacingX, int spacingY, boolean includeEdge) {
        return new GridSpaceDecoration(spanCount, spacingX, spacingY, includeEdge);
    }

    public static RecyclerView.ItemDecoration listItemDividerDecoration(Context context) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.list_item_padding_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drawable, false, false);
        return dividerItemDecoration;
    }


    public static RecyclerView.ItemDecoration listItemDividerDecoration(Context context, boolean showFirst, boolean showLast) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.list_item_padding_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drawable, showFirst, showLast);
        return dividerItemDecoration;
    }

    public static RecyclerView.ItemDecoration trackListItemDividerDecoration(Context context, boolean showFirst, boolean showLast) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.track_list_item_padding_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drawable, showFirst, showLast);
        return dividerItemDecoration;
    }

    public static RecyclerView.ItemDecoration listSectionDividerDecoration(Context context, boolean showFirst, boolean showLast) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.list_item_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drawable, showFirst, showLast);
        return dividerItemDecoration;
    }

}
