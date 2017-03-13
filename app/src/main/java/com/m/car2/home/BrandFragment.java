package com.m.car2.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m.car2.BR;
import com.m.car2.BaseFragment;
import com.m.car2.R;
import com.m.car2.adapter.databinding.BindingHolder;
import com.m.car2.adapter.databinding.ItemViewBindingTemplate;
import com.m.car2.adapter.multitype.MultiTypeAdapter;
import com.m.car2.databinding.BrandLayoutBinding;
import com.m.car2.databinding.CarBrandItemBinding;
import com.m.car2.detail.CarDetailActivity;
import com.m.car2.loader.DataLoader;
import com.m.car2.mode.CarData;
import com.m.car2.utility.Intents;

import java.util.List;

/**
 * Created by hubing on 2017/2/19.
 */

public class BrandFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<CarData>> {

    private Loader loader;
    private BrandLayoutBinding mLayoutBinding;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.brand_layout, container, false);
        initData();
        return mLayoutBinding.getRoot();
    }

    private void initData() {
        loader = new DataLoader(getActivity());
        getActivity().getSupportLoaderManager().initLoader(1001,null,this);
    }

    @Override
    public Loader<List<CarData>> onCreateLoader(int id, Bundle args) {
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<CarData>> loader, List<CarData> data) {
        if (data != null && data.size() > 0 ){
            MultiTypeAdapter adapter = new MultiTypeAdapter(data, 1);
            adapter.register(CarData.class, new BrandItemTemplate());
            mLayoutBinding.brandList.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<CarData>> loader) {

    }

    public class BrandItemTemplate extends ItemViewBindingTemplate<CarData, CarBrandItemBinding> {
        @Override
        protected int getItemLayoutId() {
            return R.layout.car_brand_item;
        }

        @Override
        protected int getVariableId() {
            return BR.carData;
        }

        @Override
        protected void onBindViewHolder(BindingHolder<CarBrandItemBinding> holder, final CarData item) {
            super.onBindViewHolder(holder, item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpToDetail(item);
                }
            });
        }

        private void jumpToDetail(CarData itemData) {
            Intent intent = new Intent(getActivity(), CarDetailActivity.class);
            intent.putExtra(Intents.EXTRA_DESCRIPTION,itemData.getCarDesUrl());
            getActivity().startActivity(intent);
//            CountryDetailActivity.launchCountryDetailActivity(getActivity(),itemData.getCarDesUrl());
        }
    }
}
