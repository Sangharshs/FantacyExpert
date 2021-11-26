package com.crictone.teamexpert.Fragments.News;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crictone.teamexpert.Adapters.NewsAdapter;
import com.crictone.teamexpert.Model.NewsModel;
import com.crictone.teamexpert.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.crictone.teamexpert.util.ApiConfig.GAMES_;

public class NewsFragment extends Fragment {

    public NewsFragment() {
        // Required empty public constructor
    }

    NewsAdapter newsAdapter;
    RecyclerView news_recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<NewsModel> news_list;
    ShimmerFrameLayout shimmerFrameLayout;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        news_recyclerView = view.findViewById(R.id.news_recyclerView);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.setAlpha((float) 0.5);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        news_recyclerView.setLayoutManager(linearLayoutManager);

       swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
       swipeRefreshLayout.setColorSchemeResources(R.color.app_color);
       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               news_list.clear();
               getActivity().getSupportFragmentManager().beginTransaction().detach(NewsFragment.this).attach(NewsFragment.this).commit();
               swipeRefreshLayout.setRefreshing(false);
           }
       });

      load_news_data();

        return view;
    }

    private void load_news_data() {
        news_list = new ArrayList<>();
        shimmerFrameLayout.startShimmer();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,  GAMES_+"getnews.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                news_list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        //   Toast.makeText(getContext(), response+"", Toast.LENGTH_SHORT).show();
                        JSONObject object = response.getJSONObject(i);
                        NewsModel newsModel = new NewsModel();

                        newsModel.setNewsHead(object.getString("heading"));
                        newsModel.setNewsImg(object.getString("image"));
                        newsModel.setNewsContent(object.getString("content"));
                        news_list.add(newsModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.reverse(news_list);
                // Collections.sort(list, NewsModel.myName);
                newsAdapter = new NewsAdapter(news_list);
                news_recyclerView.setAdapter(newsAdapter);
                news_recyclerView.setHasFixedSize(true);
                shimmerFrameLayout.setVisibility(View.GONE);
                news_recyclerView.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        queue.add(request);
    }
}