package in.khatri.rahul.amiiboapp.retrofit.utils;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("amiibo")
    Call<JsonElement> getGameData();
}
