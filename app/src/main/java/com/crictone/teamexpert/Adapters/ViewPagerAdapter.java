package com.crictone.teamexpert.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.crictone.teamexpert.R;

import java.util.ArrayList;

import kotlin.jvm.internal.Intrinsics;

public final class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> titlles = new ArrayList<>();

    public int getItemPosition(Object obj) {
        Intrinsics.checkNotNullParameter(obj, "object");
        return -2;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ViewPagerAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager, i);
        Intrinsics.checkNotNullParameter(fragmentManager, "fm");
    }

    public Fragment getItem(int i) {
        Fragment fragment = this.fragments.get(i);
        Intrinsics.checkNotNullExpressionValue(fragment, "fragments[position]");
        return fragment;
    }

    public int getCount() {
        return this.fragments.size();
    }

    public CharSequence getPageTitle(int i) {
        return this.titlles.get(i);
    }

    public final void addFragment(Fragment fragment, String str) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");

        this.fragments.add(fragment);
        this.titlles.add(str);
    }

//    public final void tab1() {
//        ArrayList<Fragment> arrayList = this.fragments;
//        arrayList.removeAll(arrayList);
//        ArrayList<String> arrayList2 = this.titlles;
//        arrayList2.removeAll(arrayList2);
//        String string = getString(R.string.upcoming);
//        Intrinsics.checkNotNullExpressionValue(string, "FantasyKingApp.applicati…String(R.string.upcoming)");
//        addFragment(UpcomingMatchFragment.Companion.newInstance(), string);
//        String string2 = FantasyKingApp.Companion.applicationContext().getString(C1546R.string.live_score);
//        Intrinsics.checkNotNullExpressionValue(string2, "FantasyKingApp.applicati…ring(R.string.live_score)");
//        addFragment(RecentMatchFragment.Companion.newInstance(), string2);
//    }
//
//    public final void tab2() {
//        ArrayList<Fragment> arrayList = this.fragments;
//        arrayList.removeAll(arrayList);
//        ArrayList<String> arrayList2 = this.titlles;
//        arrayList2.removeAll(arrayList2);
//        String string = FantasyKingApp.Companion.applicationContext().getString(C1546R.string.f302cricket);
//        Intrinsics.checkNotNullExpressionValue(string, "FantasyKingApp.applicati…tString(R.string.cricket)");
//        addFragment(CricketFragment.Companion.newInstance(), string);
//        String string2 = FantasyKingApp.Companion.applicationContext().getString(C1546R.string.football);
//        Intrinsics.checkNotNullExpressionValue(string2, "FantasyKingApp.applicati…String(R.string.football)");
//        addFragment(FootballFragment.Companion.newInstance(), string2);
//    }
//
//    public final void tab3() {
//        ArrayList<Fragment> arrayList = this.fragments;
//        arrayList.removeAll(arrayList);
//        ArrayList<String> arrayList2 = this.titlles;
//        arrayList2.removeAll(arrayList2);
//        String string = FantasyKingApp.Companion.applicationContext().getString(C1546R.string.news_india);
//        Intrinsics.checkNotNullExpressionValue(string, "FantasyKingApp.applicati…ring(R.string.news_india)");
//        addFragment(NewsFragment.Companion.newInstance(1), string);
//        String string2 = FantasyKingApp.Companion.applicationContext().getString(C1546R.string.news_world);
//        Intrinsics.checkNotNullExpressionValue(string2, "FantasyKingApp.applicati…ring(R.string.news_world)");
//        addFragment(NewsFragment.Companion.newInstance(2), string2);
//    }
}
