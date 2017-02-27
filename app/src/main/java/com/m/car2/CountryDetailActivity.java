package com.m.car2;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.m.car2.adapter.multitype.MultiTypeAdapter;
import com.m.car2.databinding.CountryLayoutBinding;
import com.m.car2.home.BrandFragment;
import com.m.car2.mode.ItemData;
import com.m.car2.utility.Intents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lbe on 17-2-27.
 */

public class CountryDetailActivity extends BaseActivity {

    public static void launchCountryDetailActivity(Context context, int index) {
        Intent intent = new Intent(context, CountryDetailActivity.class);
        intent.putExtra(Intents.EXTRA_INDEX, index);
        context.startActivity(intent);
    }

    private CountryLayoutBinding mLayoutBinding;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutBinding = DataBindingUtil.setContentView(this, R.layout.country_layout);
        index = getIntent().getIntExtra(Intents.EXTRA_INDEX, 0);
        setSupportActionBar(mLayoutBinding.toolbar);
        initData();
        MultiTypeAdapter adapter = new MultiTypeAdapter(listData, 1);
        adapter.register(ItemData.class, new BrandFragment.BrandItemTemplate());
        mLayoutBinding.brandList.setAdapter(adapter);
    }

    private List<ItemData> listData;

    private void initData() {
        listData = new ArrayList<>();

    }
}
