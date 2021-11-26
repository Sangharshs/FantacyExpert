package com.crictone.teamexpert.Retrofit;
import com.crictone.teamexpert.Model.UpcomingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    public String BASE_URL = "";

    @GET("kkjk")
    Call<List<UpcomingModel>> upcomingMatchesList();

}
