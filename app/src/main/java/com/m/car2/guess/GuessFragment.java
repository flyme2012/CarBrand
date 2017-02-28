package com.m.car2.guess;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m.car2.BaseFragment;
import com.m.car2.R;
import com.m.car2.databinding.GuessLayoutBinding;
import com.m.car2.databinding.MineLayoutBinding;

/**
 * Created by lbe on 17-2-28.
 */

public class GuessFragment extends BaseFragment {

    public static GuessFragment newInstance() {
        GuessFragment webFragment = new GuessFragment();
        webFragment.setArguments(new Bundle());
        return webFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GuessLayoutBinding mLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.guess_layout, container, false);
        return mLayoutBinding.getRoot();
    }
}
