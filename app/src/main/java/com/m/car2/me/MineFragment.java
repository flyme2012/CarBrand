package com.m.car2.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m.car2.BaseActivity;
import com.m.car2.BaseFragment;
import com.m.car2.R;
import com.m.car2.adapter.multitype.MultiTypeAdapter;
import com.m.car2.databinding.BrandLayoutBinding;
import com.m.car2.home.BrandFragment;
import com.m.car2.mode.ItemData;

/**
 * Created by hubing on 2017/2/27.
 */

public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        MineFragment webFragment = new MineFragment();
        webFragment.setArguments(new Bundle());
        return webFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MineLayoutBinding mLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.mine_layout, container, false);
//        MultiTypeAdapter adapter = new MultiTypeAdapter(listData, 1);
//        adapter.register(ItemData.class, new BrandFragment.BrandItemTemplate());
//        mLayoutBinding.brandList.setAdapter(adapter);
        return mLayoutBinding.getRoot();
    }


}
