package com.m.car2.guess;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.m.car2.BaseFragment;
import com.m.car2.R;
import com.m.car2.databinding.GuessLayoutBinding;
import com.m.car2.databinding.MineLayoutBinding;
import com.m.car2.loader.DataLoader;
import com.m.car2.mode.CarData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lbe on 17-2-28.
 */

public class GuessFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<CarData>> {

    private Loader loader;
    private GuessLayoutBinding mLayoutBinding;
    private List<CarData> dataList;
    private List<CheckBox> checkBoxViews;
    private int rightIndex;

    public static GuessFragment newInstance() {
        GuessFragment webFragment = new GuessFragment();
        webFragment.setArguments(new Bundle());
        return webFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.guess_layout, container, false);
        checkBoxViews = new ArrayList<>();
        checkBoxViews.add(mLayoutBinding.one);
        checkBoxViews.add(mLayoutBinding.two);
        checkBoxViews.add(mLayoutBinding.three);
        checkBoxViews.add(mLayoutBinding.four);

        mLayoutBinding.one.setOnCheckedChangeListener(onCheckedChangeListener);
        mLayoutBinding.two.setOnCheckedChangeListener(onCheckedChangeListener);
        mLayoutBinding.four.setOnCheckedChangeListener(onCheckedChangeListener);
        mLayoutBinding.three.setOnCheckedChangeListener(onCheckedChangeListener);

        initData();
        return mLayoutBinding.getRoot();
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!isChecked){
                return;
            }
            for (int i = 0 ; i < checkBoxViews.size() ; i ++){
                if (checkBoxViews.get(i) == buttonView){
                    checkResult(i);
                }
                checkBoxViews.get(i).setChecked(checkBoxViews.get(i) == buttonView);
            }
        }
    };

    private void initData() {
        loader = new DataLoader(getActivity());
        getActivity().getSupportLoaderManager().initLoader(1003, null, this);
    }

    @Override
    public Loader<List<CarData>> onCreateLoader(int id, Bundle args) {
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<CarData>> loader, List<CarData> data) {
        this.dataList = data;
        fillData();
    }

    @Override
    public void onLoaderReset(Loader<List<CarData>> loader) {

    }

    private void fillData() {
        if (this.dataList != null && dataList.size() > 0) {
            int index = (int) (Math.random() * (dataList.size() - 1));
            CarData carData = dataList.get(index);
            mLayoutBinding.setCarData(carData);

            int one = index;
            while (one == index) {
                one = (int) (Math.random() * (dataList.size() - 1));
            }

            int two = index;
            while (two == index) {
                two = (int) (Math.random() * (dataList.size() - 1));
            }

            int three = index;
            while (three == index) {
                three = (int) (Math.random() * (dataList.size() - 1));
            }

            rightIndex = (int) (Math.random() * 3 + 1);
            for (int i = 0; i < 4; i++) {
                if (i <= 0 && i != rightIndex) {
                    checkBoxViews.get(i).setText(dataList.get(one).getCarName());
                } else if (i <= 1 && i != rightIndex) {
                    checkBoxViews.get(i).setText(dataList.get(two).getCarName());
                } else if (i <= 2 && i != rightIndex) {
                    checkBoxViews.get(i).setText(dataList.get(three).getCarName());
                } else if (i == rightIndex) {
                    checkBoxViews.get(i).setText(dataList.get(index).getCarName());
                } else if (i <= 3) {
                    checkBoxViews.get(i).setText(dataList.get(one).getCarName());
                }
                checkBoxViews.get(i).setChecked(false);
            }
        }
    }

    private void checkResult(int index) {
        if (rightIndex == index){
            Toast.makeText(getContext(),"恭喜回答正确",Toast.LENGTH_SHORT).show();
            mLayoutBinding.getRoot().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fillData();
                }
            },1000);

        }else {
            Toast.makeText(getContext(),"恭喜回答错误",Toast.LENGTH_SHORT).show();
        }
    }

}
