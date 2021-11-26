package com.crictone.teamexpert.Fragments.PlayerFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crictone.teamexpert.Adapters.TeamPreviewAdapter;
import com.crictone.teamexpert.Model.TeamPreviewModel;
import com.crictone.teamexpert.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.crictone.teamexpert.util.ApiConfig.GAMES_;

public class PlayerFragment extends Fragment {

    View view;
    ProgressBar progressBar;
    AdView adView;
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    List<TeamPreviewModel> teamPreviewModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_player, container, false);

        progressBar = view.findViewById(R.id.progressloadinfo);
        recyclerView = view.findViewById(R.id.teamViewrecycler);
        linearLayout = view.findViewById(R.id.no_team_available_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adView = new AdView(getContext(), getString(R.string.fb_banner_ad), AdSize.BANNER_HEIGHT_50);
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
        loadTeamPreview();
        return view;
    }

    private void loadTeamPreview() {
        final Intent intent = getActivity().getIntent();
        String id =  intent.getStringExtra("id");
        teamPreviewModelList = new ArrayList<>();
        JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.POST, GAMES_ + "team_preview.php?id="+id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                teamPreviewModelList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        TeamPreviewModel teamPreviewModel = new TeamPreviewModel();
                         teamPreviewModel.setTeam_preview_image(object.getString("team_images"));
                        teamPreviewModel.setId(object.getString("id"));
                        teamPreviewModel.setTeam_preview_text(object.getString("team_info"));
                        teamPreviewModelList.add(teamPreviewModel);
                        progressBar.setVisibility(View.GONE);
                        String images = object.getString("team_images");
                         //Toast.makeText(view.getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        if(images.isEmpty()){
                            //Toast.makeText(view.getContext(), "Team Info Not Available.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(view.getContext(),"ErroR", Toast.LENGTH_SHORT).show();
                    }
                    TeamPreviewAdapter adapter = new TeamPreviewAdapter(teamPreviewModelList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue queue3 = Volley.newRequestQueue(view.getContext());
        queue3.add(request3);
    }
}