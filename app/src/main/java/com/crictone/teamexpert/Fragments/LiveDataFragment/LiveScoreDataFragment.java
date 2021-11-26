package com.crictone.teamexpert.Fragments.LiveDataFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crictone.teamexpert.Adapters.DetailPageAdapter;
import com.crictone.teamexpert.Adapters.LivesFragAdapter;
import com.crictone.teamexpert.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class LiveScoreDataFragment extends Fragment {

    public LiveScoreDataFragment() {
        // Required empty public constructor
    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_live_score_data, container, false);

        // Inflate the layout for this fragment
        return v;
    }
}