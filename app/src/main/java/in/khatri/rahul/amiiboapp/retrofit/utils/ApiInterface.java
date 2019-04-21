package in.khatri.rahul.amiiboapp.retrofit.utils;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import in.khatri.rahul.amiiboapp.model.GameDataList;
import in.khatri.rahul.amiiboapp.model.GameRetrofitModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("amiibo")
    Call<JsonElement> getGameData();
}
