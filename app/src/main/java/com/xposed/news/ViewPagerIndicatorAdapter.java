package com.xposed.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import java.util.List;

public class ViewPagerIndicatorAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<TextView> mCurrentTitleList;


    public ViewPagerIndicatorAdapter(FragmentManager fm, List<Fragment> fragmentList, List<TextView> currentItemList) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mCurrentTitleList = currentItemList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCurrentTitleList.get(position).getText().toString();
    }
}