package com.m.car2.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m.car2.BaseFragment;
import com.m.car2.CountryDetailActivity;
import com.m.car2.R;
import com.m.car2.adapter.databinding.BindingHolder;
import com.m.car2.adapter.databinding.ItemViewBindingTemplate;
import com.m.car2.adapter.multitype.MultiTypeAdapter;
import com.m.car2.databinding.BrandLayoutBinding;
import com.m.car2.databinding.CountryLayoutBinding;
import com.m.car2.mode.ItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubing on 2017/2/19.
 */

public class BrandFragment extends BaseFragment {

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
        adapter.register(ItemData.class, new BrandItemTemplate());
        brandLayoutBinding.brandList.setAdapter(adapter);
        return brandLayoutBinding.getRoot();
    }

    private List<ItemData> listData;

    private void initData() {
        listData = new ArrayList<>();
        listData.add(new ItemData("全部", R.drawable.earth, 0));
        listData.add(new ItemData("中国", R.drawable.china, 1));
        listData.add(new ItemData("德国", R.drawable.germany, 2));
        listData.add(new ItemData("美国", R.drawable.usa, 3));
        listData.add(new ItemData("日本", R.drawable.japan, 4));
        listData.add(new ItemData("英国", R.drawable.english, 5));
        listData.add(new ItemData("法国", R.drawable.france, 6));
        listData.add(new ItemData("意大利", R.drawable.italy, 7));
        listData.add(new ItemData("瑞典", R.drawable.sweden, 8));
        listData.add(new ItemData("韩国", R.drawable.korea, 9));
        listData.add(new ItemData("西班牙", R.drawable.spain, 10));
        listData.add(new ItemData("俄罗斯", R.drawable.russia, 11));
        listData.add(new ItemData("其他", R.drawable.earth, 12));
    }

    public class BrandItemTemplate extends ItemViewBindingTemplate<ItemData, BrandLayoutBinding> {
        @Override
        protected int getItemLayoutId() {
            return R.layout.brand_item;
        }

        @Override
        protected int getVariableId() {
            return com.m.car2.BR.itemData;
        }

        @Override
        protected void onBindViewHolder(BindingHolder<BrandLayoutBinding> holder, final ItemData item) {
            super.onBindViewHolder(holder, item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpToDetail(item);
                }
            });
        }

        private void jumpToDetail(ItemData itemData) {
            CountryDetailActivity.launchCountryDetailActivity(getActivity(),itemData.getId());
        }
    }
}
