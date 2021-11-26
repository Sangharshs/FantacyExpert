package com.crictone.teamexpert;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crictone.teamexpert.Adapters.TopFantacyListAdapter;
import com.crictone.teamexpert.Model.TopFantacyAppsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.crictone.teamexpert.util.ApiConfig.GAMES_;

public class TopFantacyAppsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    TopFantacyListAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<TopFantacyAppsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_fantacy_app_list);

        progressBar = findViewById(R.id.progressTopFantacy);
        recyclerView = findViewById(R.id.topFantacyAppsRecyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadTopFantacyAppsList();
    }

    private void loadTopFantacyAppsList() {
        progressBar.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.POST, GAMES_ + "getfantacyapps.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        TopFantacyAppsModel model = new TopFantacyAppsModel();
                        model.setApp_icon(object.getString("appIcon"));
                        model.setInstallUrl(object.getString("app_url"));
                        model.setSignUPbonus(object.getString("signupbonus"));
                        model.setMinimumWithdraw(object.getString("minimum_withdraw"));
                        model.setTxtReferalCode(object.getString("refferal_code"));
                        model.setTxtWithdrawType(object.getString("withdraw_type"));
                        model.setTxtAppName(object.getString("app_name"));
                        list.add(model);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter = new TopFantacyListAdapter(list);
                    recyclerView.setAdapter(adapter);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue2 = Volley.newRequestQueue(getApplicationContext());
        queue2.add(request2);

    }
}