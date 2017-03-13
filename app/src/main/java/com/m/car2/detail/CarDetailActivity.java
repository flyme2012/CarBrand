package com.m.car2.detail;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.databinding.repacked.google.common.base.Utf8;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Html;

import com.m.car2.BaseActivity;
import com.m.car2.R;
import com.m.car2.databinding.CarDetailBinding;
import com.m.car2.mode.CarDetail;
import com.m.car2.utility.Intents;

/**
 * Created by hubing on 2017/3/11.
 */

public class CarDetailActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<CarDetail> {

    private CarDetailBinding mLayoutBinding;
    private Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutBinding = DataBindingUtil.setContentView(this, R.layout.car_detail);
        setSupportActionBar(mLayoutBinding.toolbar);

        String url = getIntent().getStringExtra(Intents.EXTRA_DESCRIPTION);
        loader = new DesLoader(this,url);
        getSupportLoaderManager().initLoader(1002,null,this);

    }

    @Override
    public Loader<CarDetail> onCreateLoader(int id, Bundle args) {
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<CarDetail> loader, CarDetail data) {
        if (data != null){
            mLayoutBinding.setTitle(data.getTitle());
            mLayoutBinding.des.setText(Html.fromHtml(data.getDes()));
        }

    }

    @Override
    public void onLoaderReset(Loader<CarDetail> loader) {

    }

}
