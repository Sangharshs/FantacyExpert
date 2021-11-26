package com.crictone.teamexpert.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.crictone.teamexpert.Fragments.InfoFragmnet.InfoFragment;
import com.crictone.teamexpert.Fragments.PlayerFragment.PlayerFragment;

public class DetailPageAdapter extends FragmentPagerAdapter {
    int count_tab;

    public DetailPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        count_tab = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new InfoFragment();
            case 1: return new PlayerFragment();
            default: return null;
            }
    }

    @Override
    public int getCount() {
        return count_tab;
    }
}
