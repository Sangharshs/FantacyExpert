package com.crictone.teamexpert.Fragments.FantacyPredictionFrag;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crictone.teamexpert.Adapters.CommonAdapter;
import com.crictone.teamexpert.Adapters.SliderAdapterExample;
import com.crictone.teamexpert.Adapters.indicatorAdapter;
import com.crictone.teamexpert.Model.GameModel;
import com.crictone.teamexpert.Model.Match_Model;
import com.crictone.teamexpert.Model.SliderItem;
import com.crictone.teamexpert.Model.SliderModel;
import com.crictone.teamexpert.R;
import com.crictone.teamexpert.util.SaveSharedPreference;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.crictone.teamexpert.util.ApiConfig.BANNER_AD_ID;
import static com.crictone.teamexpert.util.ApiConfig.GAMES_;
import static com.crictone.teamexpert.util.ApiConfig.INTERSTITIAL_AD_ID;
import static com.crictone.teamexpert.util.ApiConfig.NATIVE_AD_AFTER;
import static com.crictone.teamexpert.util.ApiConfig.NATIVE_AD_ID;
import static com.crictone.teamexpert.util.Utility.USER_NAME;

public class FantacyPredictionFragment extends Fragment {
    View v;
    public static String PROMOTIONAL_BANNER;
    public static String PROMOTIONAL_BANNER_URL;

    private Match_Model matchModel;
    private NavigationView navigationView;
    private AdView adView;
    private DrawerLayout drawerLayout;
    private indicatorAdapter.callback indicatorCallback;
    public static final String BrodcastStringForAction = "checkinternet";
    private View noInternet, fragmentHome;

    private IntentFilter mIntentFilter;
    private Button retryButton;
    private CircleImageView float_button_icon;
    private CardView cardView;

    public static List<GameModel> gameModelList = new ArrayList<>();
    public static List<Match_Model> matchModelList = new ArrayList<>();

    private ShimmerFrameLayout shimmer0, shimmer1, shimmer2;
    // LottieAnimationView lottieAnimationView;
    private RecyclerView matches_recyclerview, gamecategory_recyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String payment_Method, matches_category;
    private indicatorAdapter indicatorAdapter;
    //    Second Slider
    private SliderAdapterExample adapter;
    private SliderItem sliderItem;
    private List<SliderItem> sliderItemList;
    private SliderView sliderView;
    private EditText pnumberEdittext;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private String username, email, total_earning;
    private TextView userTextV, emailTextV;
    private LinearLayout linearLayout;
    private int selectedId, position;
    private TextView setMinimumAlert;
    private CommonAdapter cricketAdapter;

    public FantacyPredictionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fantacy_prediction, container, false);

        shimmer0 = v.findViewById(R.id.gameitemshimmer1);
        shimmer1 = v.findViewById(R.id.shimmer1);
        shimmer2 = v.findViewById(R.id.shimmer2);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);


        sliderView = v.findViewById(R.id.imageSlider);
        cardView = v.findViewById(R.id.icard);
        userTextV = v.findViewById(R.id.username_show);
        emailTextV = v.findViewById(R.id.email_show);
        linearLayout = v.findViewById(R.id.datalayout);


        gamecategory_recyclerview = v.findViewById(R.id.indicatorList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        gamecategory_recyclerview.setLayoutManager(linearLayoutManager);

        matches_recyclerview = v.findViewById(R.id.matchesList);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        matches_recyclerview.setLayoutManager(linearLayoutManager1);

        load_add();
        loadSlider();
        loadMatches();
        refreshLayout();

        AudienceNetworkAds.initialize(getContext());

        loadMatchCategory();

        loadMatchCategory();
        loadMatches();
        loadSlider();
        getData();

        if (matchModelList.size() == 0 && sliderItemList.size() == 0) {
            loadSlider();
            loadMatches();
        }

        return v;
    }

    private void refreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cardView.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shimmer1.setAlpha((float) 0.5);
                        shimmer1.showShimmer(true);
                        //the current activity will get finished.
                    }
                }, 1000);
                if (!sliderItemList.isEmpty() || !matchModelList.isEmpty()) {
                    sliderItemList.clear();
                    matchModelList.clear();
                }
                loadSlider();
                loadMatches();
                //restaurantHolder.setCurrentItem(position);
                //Toast.makeText(MainActivity.this, String.valueOf(matchModelList.size()).toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(), "Refreshing....", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void loadMatchCategory() {
        shimmer0.setVisibility(View.VISIBLE);
        shimmer0.startShimmer();
        shimmer0.setAlpha((float) 0.5);
        shimmer0.startShimmer();
        gameModelList = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GAMES_ + "getgames.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                gameModelList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        JSONObject object = response.getJSONObject(i);
                        GameModel gameModel = new GameModel();
                        gameModel.setId(object.getString("id"));
                        gameModel.setGame_name(object.getString("category"));
                        gameModel.setGameIcon(object.getString("icon"));
                        gameModelList.add(gameModel);
                        indicatorAdapter = new indicatorAdapter(v.getContext(), gameModelList, indicatorCallback);
                        shimmer0.setVisibility(View.GONE);
                        gamecategory_recyclerview.setAdapter(indicatorAdapter);
                        matches_category = gameModelList.get(position).getId();
                        //Toast.makeText(MainActivity.this, matches_category, Toast.LENGTH_SHORT).show();
                        indicatorCallback = new indicatorAdapter.callback() {
                            @Override
                            public void onTitleClicked(int position) {
                                cardView.setVisibility(View.GONE);
                                if (sliderItemList != null || matchModelList != null) {
                                    sliderItemList.clear();
                                    matchModelList.clear();
                                }

                                loadSlider();
                                loadMatches();
                                matches_category = gameModelList.get(position).getId();
                                //slider_category  = sliderItemList.get(position).getSlider_category();
                                indicatorAdapter.setSelectedIndex(position);
                                indicatorAdapter.notifyDataSetChanged();
                                gamecategory_recyclerview.scrollToPosition(position);
                            }
                        };
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                shimmer0.setVisibility(View.GONE);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        queue.add(request);
        queue.getCache().clear();
    }

    private void loadMatches() {
        shimmer2.setVisibility(View.VISIBLE);
        shimmer2.startShimmer();
        shimmer2.setAlpha((float) 0.5);
        shimmer2.startShimmer();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GAMES_ + "getcricket.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                matchModelList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        JSONObject object = response.getJSONObject(i);
                        matchModel = new Match_Model();
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
                        matchModel.setTeam1_fullname(object.getString("team1_full"));
                        matchModel.setTeam2_fullname(object.getString("team2_full"));
                        matchModel.setMatchCategory(object.getString("category"));


//                        if (!gameModelList.isEmpty())
                        if (matchModel.getMatchCategory().equals(matches_category)) {
                            matchModelList.add(matchModel);
                            shimmer2.setVisibility(View.GONE);
                            if (matchModelList.size() == 0) {
                                shimmer2.setVisibility(View.GONE);
                                Toast.makeText(v.getContext(), "Matches Not Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException | IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Match_Model> list = sortArray((ArrayList<Match_Model>) matchModelList);
                    cricketAdapter = new CommonAdapter(list, v.getContext());
//                    FBNativeAdAdapter fbNativeAdAdapter = FBNativeAdAdapter.Builder.with(getResources().getString(R.string.native_ad_fb), cricketAdapter).adItemInterval(NATIVE_AD_AFTER).build();

                    matches_recyclerview.setAdapter(cricketAdapter);
//                    matches_recyclerview.setAdapter(fbNativeAdAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                shimmer2.setVisibility(View.GONE);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        queue.add(request);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                shimmer2.setVisibility(View.GONE);
            }
        });
    }


    //Sort Matches Time//
    private ArrayList<Match_Model> sortArray(ArrayList<Match_Model> arrayList) {
        if (arrayList != null) {
            Collections.sort(arrayList, new Comparator<Match_Model>() {
                @Override
                public int compare(Match_Model o2, Match_Model o1) {
                    return o2.getMatch_time().compareTo(o1.getMatch_time());
                }
            });
        }
        return arrayList;
    }

    public void loadSlider() {
        shimmer1.setAlpha((float) 0.5);
        shimmer1.showShimmer(true);
        shimmer1.setVisibility(View.VISIBLE);
        sliderView.setSliderAdapter(new SliderAdapterExample(v.getContext(), sliderItemList));
        sliderItemList = new ArrayList<>();
        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, GAMES_ + "cricket_slider.php", null, new Response.Listener<JSONArray>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onResponse(JSONArray response) {
                sliderItemList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        SliderModel slideModel = new SliderModel();
                        slideModel.setBanner(object.getString("slider"));
                        slideModel.setUrl(object.getString("url"));
                        String category = object.getString("category");
                        sliderItem = new SliderItem();
                        sliderItem.setBanner(object.getString("slider"));
                        sliderItem.setUrl(object.getString("url"));

                        PROMOTIONAL_BANNER = object.getString("slider");
                        PROMOTIONAL_BANNER_URL = object.getString("url");
                        if (PROMOTIONAL_BANNER != null && !PROMOTIONAL_BANNER.isEmpty()) {
                            if (gameModelList.size() != 0) {

                                if (category.equals(matches_category)) {
                                    cardView.setVisibility(View.VISIBLE);
                                    sliderView.setVisibility(View.VISIBLE);
                                    sliderItemList.add(sliderItem);
                                    sliderView.setVisibility(View.VISIBLE);
                                    if (sliderItemList.size() == 0) {
                                        sliderView.setVisibility(View.GONE);
                                        cardView.setVisibility(View.GONE);
                                    }
                                    shimmer1.showShimmer(false);
                                    shimmer1.setVisibility(View.GONE);
                                }
                            }


                            //  Glide.with(view.getContext()).load(IMG+PROMOTIONAL_BANNER).into(imageView);
                        } else {
                            shimmer1.setVisibility(View.GONE);
                            sliderView.setVisibility(View.GONE);
                            cardView.setVisibility(View.GONE);
                        }
                        ///////BANNER SLIDER/////

                    } catch (JSONException | IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new SliderAdapterExample(v.getContext(), sliderItemList);
                sliderView.setSliderAdapter(adapter);
                adapter.notifyDataSetChanged();
                sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                sliderView.setAutoCycle(true);
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

        RequestQueue queue1 = Volley.newRequestQueue(v.getContext());
        queue1.add(request1);
        queue1.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                shimmer1.setVisibility(View.GONE);
            }
        });

    }
    // Slider Coding End//


    private void load_add() {
        JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.POST, GAMES_ + "ads.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        BANNER_AD_ID = object.getString("banner_ad");
                        INTERSTITIAL_AD_ID = object.getString("interstitial_ad");
                        NATIVE_AD_ID = object.getString("native_ad");

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

        RequestQueue queue3 = Volley.newRequestQueue(v.getContext());
        queue3.add(request3);


        adView = new AdView(v.getContext(), getString(R.string.fb_banner_ad), AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) v.findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();


        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("ADI", String.valueOf(adError.getErrorMessage()));
//                   Toast.makeText(MainActivity.this, adError.getErrorMessage()+"", Toast.LENGTH_SHORT).show();
//                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("ADI", String.valueOf(ad.toString()));
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
    }


    //Earning data coding Start //
    private void getData() {
        SaveSharedPreference.getUserName(getActivity(), username);
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        username = pref.getString(USER_NAME, "");
        JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.POST, GAMES_ + "refer.php?username=" + username, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        username = object.getString("username");
                        email = object.getString("email");
                        total_earning = object.getString("earning");
                        //   Toast.makeText(MainActivity.this, username+email+total_earning, Toast.LENGTH_SHORT).show();
                        if (username != null && email != null && total_earning != null) {
                            linearLayout.setVisibility(View.VISIBLE);
                            userTextV.setText(String.valueOf(username));
                            emailTextV.setText(String.valueOf(email));
                        }
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
        RequestQueue queue3 = Volley.newRequestQueue(v.getContext());
        queue3.add(request3);
    }
}
//Earning data coding End   //}