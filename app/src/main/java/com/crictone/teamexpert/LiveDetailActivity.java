package com.crictone.teamexpert;

import android.os.Bundle;
import android.util.Log;

import kotlin.jvm.internal.Intrinsics;


public final class LiveDetailActivity extends MainActivity {
    private final String TAG = "LiveDetailActivity";
    public String key;

    public void _$_clearFindViewByIdCache() {
    }

    public final String getKey() {
        String str = this.key;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("key");
        return null;
    }

    public final void setKey(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.key = str;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(R.style.Theme_IAPTheme);
        setContentView((int) R.layout.activity_live_detail);
        String stringExtra = getIntent().getStringExtra("liveMatchKeyData");
        Intrinsics.checkNotNull(stringExtra);
        Intrinsics.checkNotNullExpressionValue(stringExtra, "intent.getStringExtra(\"liveMatchKeyData\")!!");
        setKey(stringExtra);
        Log.d(this.TAG, Intrinsics.stringPlus("onCreate: ", getKey()));
     //   LiveMatchViewModel liveMatchViewModel = (LiveMatchViewModel) getDefaultViewModelProviderFactory().create(LiveMatchViewModel.class);

    }


//    private final void initComponent() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple_500));
//        }
//        ViewPager viewPager = (ViewPager) findViewById(R.id.sc_viewPager);
//        Intrinsics.checkNotNullExpressionValue(viewPager, "sc_viewPager");
//        setupViewpager(viewPager);
//        ((TabLayout) findViewById(R.id.sc_tabLayout)).setupWithViewPager((ViewPager) findViewById(C1546R.C1549id.sc_viewPager));
//    }
//
//    private final void setupViewpager(ViewPager viewPager) {
//        FragmentManager supportFragmentManager = getSupportFragmentManager();
//        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "supportFragmentManager");
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(supportFragmentManager, 0);
//        this.adapter = viewPagerAdapter;
//        String string = getString(R.string.info);
//        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.info)");
//        viewPagerAdapter.addFragment(InfoFragment.Companion.newInstance(getKey()), string);
//        ViewPagerAdapter viewPagerAdapter2 = this.adapter;
//        ViewPagerAdapter viewPagerAdapter3 = null;
//        if (viewPagerAdapter2 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("adapter");
//            viewPagerAdapter2 = null;
//        }
//        String string2 = getString(C1546R.string.score);
//        Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.score)");
//        viewPagerAdapter2.addFragment(ScoreCardFragment.Companion.newInstance(getKey()), string2);
//        ViewPagerAdapter viewPagerAdapter4 = this.adapter;
//        if (viewPagerAdapter4 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("adapter");
//        } else {
//            viewPagerAdapter3 = viewPagerAdapter4;
//        }
//        viewPager.setAdapter(viewPagerAdapter3);
//    }
}
