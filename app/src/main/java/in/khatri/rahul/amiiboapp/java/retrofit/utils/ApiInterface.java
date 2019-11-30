package in.khatri.rahul.amiiboapp.java.retrofit.utils;

import com.google.gson.JsonElement;

import java.util.ArrayList;

import in.khatri.rahul.amiiboapp.java.retrofit.model.GameDataList;
import in.khatri.rahul.amiiboapp.java.retrofit.model.GameRetrofitModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("amiibo")
    Call<GameDataList> getGameData();
}
