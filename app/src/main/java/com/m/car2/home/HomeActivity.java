package com.m.car2.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.m.car2.BaseActivity;
import com.m.car2.R;
import com.m.car2.databinding.ActivityMainBinding;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    ActivityMainBinding mLayoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mLayoutBinding.setPageIndex(0);

        mLayoutBinding.homePage.setAdapter(new HomePageAdapter(getSupportFragmentManager()));

        mLayoutBinding.homePage.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mLayoutBinding.setPageIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class HomePageAdapter extends FragmentStatePagerAdapter {

        public HomePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = BrandFragment.newInstance();
                    break;
                case 1:
                    fragment = BrandFragment.newInstance();
                    break;
                case 2:
                    fragment = BrandFragment.newInstance();
                    break;
            }
            return fragment;
        }
    }
}
