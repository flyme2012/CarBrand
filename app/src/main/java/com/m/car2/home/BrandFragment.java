package com.m.car2.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m.car2.R;
import com.m.car2.databinding.BrandLayoutBinding;

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
        BrandLayoutBinding brandLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.brand_layout,container,false);

        return brandLayoutBinding.getRoot();
    }
}
