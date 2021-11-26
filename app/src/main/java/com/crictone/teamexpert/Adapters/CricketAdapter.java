package com.crictone.teamexpert.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crictone.teamexpert.Model.Match_Model;
import com.crictone.teamexpert.R;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdsManager;

import java.util.ArrayList;
import java.util.List;

public class CricketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int AD_DISPLAY_FREQUENCY_LIMIT = 4;
    public static final int POST_TYPE = 0;
    public static final int AD_TYPE = 1;
    List<NativeAd> nativeAdList = new ArrayList<>();

    private NativeAd nativeAd;
    LinearLayout nativeAdContainer;
    Context context;
    NativeAdsManager nativeAdsManager;

    ArrayList<Match_Model> matchModel_list;
    Match_Model matchModel = new Match_Model();
    int p;

    public CricketAdapter(ArrayList<Match_Model> matchModel_list,Context context, NativeAdsManager nativeAdsManager) {
        this.matchModel_list = matchModel_list;
        this.context = context;
        this.nativeAdsManager = nativeAdsManager;
        nativeAdList = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == AD_TYPE) {
            NativeAdLayout nativeAdLayout  = (NativeAdLayout) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.native_ad_fb_ayout, viewGroup,false);
            if(nativeAdLayout == null){
                nativeAdLayout.setVisibility(View.GONE);
            }
            return new UnifiedFbHolder(nativeAdLayout);
        }else
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.match_iteam,viewGroup, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        switch (viewType)
        {
            case AD_TYPE:
                NativeAd ad ;
                if(nativeAdList.size() > i/AD_DISPLAY_FREQUENCY_LIMIT){
                    ad = nativeAdList.get(i/AD_DISPLAY_FREQUENCY_LIMIT);
                }else {
                    ad = nativeAdsManager.nextNativeAd();
                    if (ad != null) {
                        if (!ad.isAdInvalidated()) {
                            nativeAdList.add(ad);
                        }
                    }
                }
                UnifiedFbHolder adHolder = (UnifiedFbHolder)viewHolder;
                adHolder.adChoicesContainer.removeAllViews();

                if(ad != null)
                {
                    adHolder.main_LLL.setVisibility(View.VISIBLE);
                    adHolder.tvAdTitle.setText(ad.getAdvertiserName());
                    adHolder.tvAdBody.setText(ad.getAdBodyText());
                    adHolder.tvAdSocialContext.setText(ad.getAdSocialContext());
                    adHolder.tvAdSponsoredLabel.setText("Sponsored");
                    adHolder.btncallToAction.setText(ad.getAdCallToAction());
                    adHolder.btncallToAction.setVisibility(ad.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);

                    AdOptionsView adOptionsView = new AdOptionsView(context,ad,adHolder.nativeAdLayout);
                    adHolder.adChoicesContainer.addView(adOptionsView,0);

                    List<View> clickableView = new ArrayList<>();
                    clickableView.add(adHolder.ivAdIcon);
                    clickableView.add(adHolder.mvAdMedia);
                    clickableView.add(adHolder.btncallToAction);

                    ad.registerViewForInteraction(
                            adHolder.nativeAdLayout,adHolder.mvAdMedia,adHolder.ivAdIcon,
                            clickableView);
                    }

                break;
            case POST_TYPE:
           try {
                   ((ViewHolder) viewHolder).onBind(matchModel_list.get(i));
               }catch (IndexOutOfBoundsException e){
                   e.printStackTrace();
               }
           break;
        }
    }

    @Override
    public int getItemCount() {
        //+ nativeAdList.size()
            return matchModel_list.size()+ nativeAdList.size() ;
    }
    @Override
    public int getItemViewType(int position) {
        return position % AD_DISPLAY_FREQUENCY_LIMIT == 0 && position != 0 ? AD_TYPE:POST_TYPE;
    }

}
