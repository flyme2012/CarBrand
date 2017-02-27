package com.m.car2.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m.car2.R;
import com.m.car2.adapter.databinding.BindingHolder;
import com.m.car2.adapter.databinding.ItemViewBindingTemplate;
import com.m.car2.adapter.multitype.MultiTypeAdapter;
import com.m.car2.databinding.BrandLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubing on 2017/2/19.
 */

public class BrandFragment extends Fragment {

    public static BrandFragment newInstance() {
        BrandFragment webFragment = new BrandFragment();
        webFragment.setArguments(new Bundle());
        return webFragment;
    }

    public static BrandFragment newInstance(Bundle bundle) {
        BrandFragment webFragment = new BrandFragment();
        webFragment.setArguments(bundle);
        return webFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BrandLayoutBinding brandLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.brand_layout, container, false);
        initData();
        MultiTypeAdapter adapter = new MultiTypeAdapter(listData, 1);
        adapter.register(String.class, new BrandItemTemplate());
        brandLayoutBinding.brandList.setAdapter(adapter);
        return brandLayoutBinding.getRoot();
    }

    private List<String> listData;

    private void initData() {
        listData = new ArrayList<>();
        listData.add("全部");
        listData.add("中国");
        listData.add("德国");
        listData.add("美国");
        listData.add("日本");
        listData.add("英国");
        listData.add("法国");
        listData.add("意大利");
        listData.add("瑞典");
        listData.add("韩国");
        listData.add("西班牙");
        listData.add("俄罗斯");
        listData.add("其他");
    }

    public static class BrandItemTemplate extends ItemViewBindingTemplate<String,BrandLayoutBinding> {

        @Override
        protected int getItemLayoutId() {
            return R.layout.brand_item;
        }

        @Override
        protected int getVariableId() {
            return com.m.car2.BR.name;
        }

        @Override
        protected void onBindViewHolder(BindingHolder<BrandLayoutBinding> holder, String item) {
            super.onBindViewHolder(holder, item);
        }
    }
}
