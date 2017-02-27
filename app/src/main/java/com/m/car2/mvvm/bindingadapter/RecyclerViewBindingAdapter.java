package com.m.car2.mvvm.bindingadapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zhenyu on 16/12/15.
 */

public class RecyclerViewBindingAdapter {


    @BindingAdapter({"adapter"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"adapter", "layoutManager"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @BindingAdapter({"adapter", "layoutManager", "itemDecoration"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager, RecyclerView.ItemDecoration decoration) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"layoutManager", "itemDecoration"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, RecyclerView.ItemDecoration decoration) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
    }


    @BindingAdapter({"layoutManager"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    @BindingAdapter("hasFixSize")
    public static void setHasFixSize(RecyclerView recyclerView, boolean hasFixSize) {
        recyclerView.setHasFixedSize(hasFixSize);
    }

    @BindingAdapter({"itemDecoration"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.ItemDecoration decoration) {
        recyclerView.addItemDecoration(decoration);
    }


}
