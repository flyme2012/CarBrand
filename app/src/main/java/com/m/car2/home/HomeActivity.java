package com.m.car2.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.m.car2.BaseActivity;
import com.m.car2.R;
import com.m.car2.databinding.ActivityMainBinding;
import com.m.car2.guess.GuessFragment;
import com.m.car2.me.MineFragment;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    ActivityMainBinding mLayoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mLayoutBinding.toolbar);
        mLayoutBinding.setPageIndex(0);
        mLayoutBinding.homePage.setAdapter(new HomePageAdapter(getSupportFragmentManager()));
        mLayoutBinding.homePage.addOnPageChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "market://details?id=" + getPackageName());
            shareIntent.setType("text/plain");
            //设置分享列表的标题，并且每次都显示分享列表
            startActivity(Intent.createChooser(shareIntent, "分享到"));
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            switch (position) {
                case 0:
                    fragment = BrandFragment.newInstance();
                    break;
                case 1:
                    fragment = GuessFragment.newInstance();
                    break;
                case 2:
                    fragment = MineFragment.newInstance();
                    break;
            }
            return fragment;
        }
    }
}
