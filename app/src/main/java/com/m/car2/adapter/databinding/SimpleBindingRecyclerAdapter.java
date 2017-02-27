package com.m.car2.adapter.databinding;

import java.util.List;

/**
 * Created by zhenyu on 16/12/9.
 */

public class SimpleBindingRecyclerAdapter<D> extends BindingRecyclerAdapter {


    private int mItemLayoutId;
    private int mVariableId;

    public SimpleBindingRecyclerAdapter(int itemLayoutId, int variableId, List<D> data) {
        this.mItemLayoutId = itemLayoutId;
        this.mVariableId = variableId;
        reload(data, false);
    }


    @Override
    public int getItemLayoutId(int viewType) {
        return this.mItemLayoutId;
    }

    @Override
    public int getVariableId() {
        return mVariableId;
    }


}
