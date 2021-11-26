package com.crictone.teamexpert.Fragments.cricket_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crictone.teamexpert.Adapters.CommonAdapter;
import com.crictone.teamexpert.Adapters.SliderAdapterExample;
import com.crictone.teamexpert.Adapters.VerticalSpacingItemDecorator;
import com.crictone.teamexpert.Model.Match_Model;
import com.crictone.teamexpert.Model.SliderItem;
import com.crictone.teamexpert.Model.SliderModel;
import com.crictone.teamexpert.R;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.NativeAdsManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.crictone.teamexpert.Adapters.CricketAdapter.AD_DISPLAY_FREQUENCY_LIMIT;
import static com.crictone.teamexpert.util.ApiConfig.GAMES_;
import static com.crictone.teamexpert.MainActivity.PROMOTIONAL_BANNER;
import static com.crictone.teamexpert.MainActivity.PROMOTIONAL_BANNER_URL;



public class CricketFragment extends Fragment {

    ArrayList<Match_Model>  match_list = new ArrayList<>();
    LottieAnimationView lottieAnimationView;
    View view;
    CommonAdapter cricketAdapter;

    RecyclerView cricket_recyclerView;
    LinearLayoutManager linearLayoutManager;
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    NativeAdsManager nativeAdsManager;
//  Second Slider
    SliderAdapterExample adapter;
    SliderItem sliderItem;
    List<SliderItem> sliderItemList;
    SliderView sliderView;
    DotsIndicator dotsIndicator;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cricket, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.app_color);

        cricket_recyclerView = view.findViewById(R.id.cricket_recyclerView);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

        lottieAnimationView = view.findViewById(R.id.animationView);

        shimmerFrameLayout.setAlpha((float) 0.5);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        cricket_recyclerView.setLayoutManager(linearLayoutManager);

        sliderView = view.findViewById(R.id.imageSlider);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cricket_recyclerView.setLayoutManager(layoutManager);

        loadData_with_Volley();
        refresh();

        AudienceNetworkAds.initialize(view.getContext());

        nativeAdsManager = new NativeAdsManager(view.getContext(), getString(R.string.native_ad_fb),AD_DISPLAY_FREQUENCY_LIMIT);
        nativeAdsManager.loadAds();
        if(nativeAdsManager.isLoaded()){
            cricketAdapter.notifyDataSetChanged();
        }


        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        cricket_recyclerView.addItemDecoration(itemDecorator);
        return view;
    }

    private void loadData_with_Volley(){
        shimmerFrameLayout.startShimmer();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,GAMES_+"getcricket.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               match_list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject object = response.getJSONObject(i);
                        Match_Model matchModel = new Match_Model();
                        matchModel.setMatchId(object.getString("id"));
                        matchModel.setPlayer1_profile(object.getString("team1_image"));
                        matchModel.setPlayer2_profile(object.getString("team2_image"));
                        matchModel.setMatch_name(object.getString("match_name"));
                        matchModel.setPlayer1_name(object.getString("team1_name"));
                        matchModel.setPlayer2_name(object.getString("team2_name"));
                        matchModel.setMatch_progress(object.getString("match_status"));
                        matchModel.setTeam_status(object.getString("team_status"));
                        matchModel.setMatch_rating(object.getInt("match_rating"));
                        matchModel.setMatch_time(object.getString("date_time"));
                        match_list.add(matchModel);

                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(match_list.isEmpty()){
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }
                    ArrayList<Match_Model> list= sortArray(match_list);
                    cricketAdapter = new CommonAdapter(list,getContext());
                    cricket_recyclerView.setAdapter(cricketAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadIM();
    }

    private ArrayList<Match_Model>  sortArray(ArrayList<Match_Model> arrayList) {
        if (arrayList != null) {
            Collections.sort(arrayList, new Comparator<Match_Model>() {
                @Override
                public int compare(Match_Model o2,Match_Model o1) {
                    return o2.getMatch_time().compareTo(o1.getMatch_time()); }
            });
        }
        return arrayList;
    }

    private void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData_with_Volley();
                Toast.makeText(view.getContext(), "Refreshing....", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void loadIM() {
        sliderView.setSliderAdapter(new SliderAdapterExample(view.getContext(),sliderItemList));
        sliderItemList = new ArrayList<>();
        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, GAMES_+"cricket_slider.php", null, new Response.Listener<JSONArray>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        SliderModel slideModel = new SliderModel();
                        slideModel.setBanner(object.getString("slider"));
                        slideModel.setUrl(object.getString("url"));

                        sliderItem = new SliderItem();
                        sliderItem.setBanner(object.getString("slider"));
                        sliderItem.setUrl(object.getString("url"));

                        PROMOTIONAL_BANNER = object.getString("slider");
                        PROMOTIONAL_BANNER_URL = object.getString("url");
                        if(PROMOTIONAL_BANNER != null && !PROMOTIONAL_BANNER.isEmpty()) {
                            sliderView.setVisibility(View.VISIBLE);
                          //  Glide.with(view.getContext()).load(IMG+PROMOTIONAL_BANNER).into(imageView);
                        }else{
                            sliderView.setVisibility(View.GONE);
                        }
                        ///////BANNER SLIDER/////
                        sliderItemList.add(sliderItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (sliderItemList.isEmpty()) {
                    sliderView.setVisibility(View.GONE);

                } else {
                    sliderView.setVisibility(View.VISIBLE);
                }

                adapter = new SliderAdapterExample(view.getContext(),sliderItemList);
                sliderView.setSliderAdapter(adapter);
                //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
               // sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                sliderView.setIndicatorEnabled(false);
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.startAutoCycle();
                sliderView.onPageScrollStateChanged(1);
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue1 = Volley.newRequestQueue(view.getContext());
        queue1.add(request1);
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
       //Toast.makeText(context, "Attach", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
       //Toast.makeText(view.getContext(), "Resume", Toast.LENGTH_SHORT).show();
    }
}