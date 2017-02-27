package com.m.car2.adapter.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenyu on 16/12/8.
 */

public abstract class BindingRecyclerAdapter<D> extends RecyclerView.Adapter<BindingHolder> {


    protected LayoutInflater mLayoutInflater;
    private List<D> mList = new ArrayList<>();
    private onRecyclerItemClickListener mOnListItemClickListener;


    public void reload(List<D> data) {
        reload(data, true);
    }

    public void reload(List<D> data, boolean notifyChange) {
        mList.clear();
        append(data, notifyChange);
    }

    public void append(List<D> data, boolean notifyChange) {
        mList.addAll(data);
        if (notifyChange) {
            notifyDataSetChanged();
        }
    }


    public void setOnListItemClickListener(onRecyclerItemClickListener listener) {
        this.mOnListItemClickListener = listener;
    }


    public D getItem(int position) {
        if (position >= 0 && position < mList.size()) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View itemView = mLayoutInflater.inflate(getItemLayoutId(viewType), parent, false);
        ViewDataBinding viewDataBinding = DataBindingUtil.bind(itemView);
        BindingHolder bindingHolder = new BindingHolder(itemView, viewDataBinding);
        return bindingHolder;
    }

    @Override
    public void onBindViewHolder(final BindingHolder holder, final int position) {
        D data = getItem(position);
        holder.getViewDataBinding().setVariable(getVariableId(), data);
        holder.getViewDataBinding().executePendingBindings();
        if (mOnListItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnListItemClickListener.onItemClick(holder, position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public abstract int getItemLayoutId(int viewType);

    public abstract int getVariableId();

    public static interface onRecyclerItemClickListener {
        public void onItemClick(RecyclerView.ViewHolder holder, int position);
    }
}
