package com.example.administrator.miniweather;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<android.support.v4.app.Fragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<android.support.v4.app.Fragment> fragmentList){
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}