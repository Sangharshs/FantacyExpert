package com.crictone.teamexpert;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crictone.teamexpert.Adapters.DetailPageAdapter;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.crictone.teamexpert.util.ApiConfig.BANNER_AD_ID;
import static com.crictone.teamexpert.util.ApiConfig.GAMES_;
import static com.crictone.teamexpert.util.ApiConfig.INTERSTITIAL_AD_ID;

public class MatchDetailsActivity extends AppCompatActivity {
    DetailPageAdapter detailPageAdapter1;
    ViewPager viewPager1;
    TabLayout tabLayout1;
    TabItem n_tab1,n_tab2;
    InterstitialAd interstitialAd;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        adView = new AdView(this, getString(R.string.fb_banner_ad), AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();


        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("ADI",String.valueOf(adError.getErrorMessage()));
//                   Toast.makeText(MainActivity.this, adError.getErrorMessage()+"", Toast.LENGTH_SHORT).show();
//                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("ADI",String.valueOf(ad.toString()));
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };
        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());

        JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.POST, GAMES_+"ads.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        BANNER_AD_ID = object.getString("banner_ad");
                        INTERSTITIAL_AD_ID = object.getString("interstitial_ad");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue3 = Volley.newRequestQueue(getApplicationContext());
        queue3.add(request3);

        interstitialAd = new InterstitialAd(this, getString(R.string.fb_interstitial_ad));

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

                // Interstitial ad displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("ADINT",ad.toString()+adError.toString());
                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());



        viewPager1 = findViewById(R.id.viewPager1);
        tabLayout1 = findViewById(R.id.tabLayout1);
        n_tab1      = findViewById(R.id.tab1);
        n_tab2      = findViewById(R.id.tab2);

        detailPageAdapter1 = new DetailPageAdapter(getSupportFragmentManager(),tabLayout1.getTabCount());
        viewPager1.setAdapter(detailPageAdapter1);

        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager1.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0 || tab.getPosition() == 1)
                    detailPageAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(interstitialAd != null) {
            interstitialAd.loadAd();
        //    Toast.makeText(this, "Ad Loading Start", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(interstitialAd.isAdLoaded()){
            interstitialAd.show();
        }else {
            super.onBackPressed();
        }
    }
}