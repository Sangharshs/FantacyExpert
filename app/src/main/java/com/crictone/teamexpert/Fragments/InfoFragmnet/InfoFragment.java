package com.crictone.teamexpert.Fragments.InfoFragmnet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crictone.teamexpert.Model.Match_Model;
import com.crictone.teamexpert.Model.Match_details;
import com.crictone.teamexpert.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.crictone.teamexpert.util.ApiConfig.GAMES_;
import static com.crictone.teamexpert.util.ApiConfig.NATIVE_AD_ID;

public class InfoFragment extends Fragment {

    String ss;
    View view;
    WebView webView;
    Match_Model matchModel;
    ImageView telegram_ImageView;
    NativeAd nativeAd;
    ProgressBar progressBar;
    AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        webView = view.findViewById(R.id.web_view);
        telegram_ImageView = view.findViewById(R.id.telegram_imageView);
        progressBar = view.findViewById(R.id.progressloadinfo);

        AudienceNetworkAds.initialize(view.getContext());

        final Intent intent = getActivity().getIntent();
        ss =  intent.getStringExtra("id");

       // Toast.makeText(view.getContext(), ss, Toast.LENGTH_SHORT).show();
        loadNativeAd();
        showPost();
        matchModel =  new Match_Model();
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);

        adView = new AdView(getContext(),getString(R.string.fb_banner_ad), AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) view.findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();
        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                //   Toast.makeText(MainActivity.this, adError.getErrorMessage()+"", Toast.LENGTH_SHORT).show();
                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {
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



        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    private void loadNativeAd() {
        nativeAd = new NativeAd(view.getContext(),NATIVE_AD_ID);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
          //      Toast.makeText(view.getContext(), adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                inflateAd(nativeAd);

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression

            }
        };
        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    public void showPost(){
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<Match_details> match_details = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,GAMES_+"getmandp.php?id="+ss, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Match_details matchModel = new Match_details();
                        matchModel.setId(object.getString("id"));
                        matchModel.setMatch_details(object.getString("match_details"));
                        match_details.add(matchModel);


                        if(!matchModel.getMatch_details().isEmpty()) {
                            webView.loadDataWithBaseURL((String)
                                    null, "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>"
                                    + matchModel.getMatch_details() +
                                    "</body></html>", "text/html", "UTF-8", (String) null);
                        }else{
                            Toast.makeText(view.getContext(), "No Details Found", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);

    }

    public void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        NativeAdLayout nativeAdLayout = view.findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        NativeAdLayout adView = (NativeAdLayout) inflater.inflate(R.layout.native_ad_fb_ayout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = view.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(view.getContext(), nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        LinearLayout linearLayout = adView.findViewById(R.id.main_LLL);
        linearLayout.setVisibility(View.VISIBLE);
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }
}