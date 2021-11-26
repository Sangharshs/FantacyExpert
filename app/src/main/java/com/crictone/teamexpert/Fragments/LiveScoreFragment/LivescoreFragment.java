package com.crictone.teamexpert.Fragments.LiveScoreFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crictone.teamexpert.Adapters.LivesFragAdapter;
import com.crictone.teamexpert.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class LivescoreFragment extends Fragment {

    View v;

    public LivescoreFragment(){}
    LivesFragAdapter livesFragAdapter;
    ViewPager viewPager1;
    TabLayout tabLayout1;
    TabItem n_tab1,n_tab2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_livescore, container, false);

        viewPager1 = v.findViewById(R.id.viewPager1);
        tabLayout1 = v.findViewById(R.id.tabLayout1);
        n_tab1      = v.findViewById(R.id.tab1);
        n_tab2      = v.findViewById(R.id.tab2);

        livesFragAdapter = new LivesFragAdapter(getActivity().getSupportFragmentManager(),tabLayout1.getTabCount());
        viewPager1.setAdapter(livesFragAdapter);

        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager1.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0 || tab.getPosition() == 1)
                    livesFragAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));

        return v;
    }
}