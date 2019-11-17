package in.khatri.rahul.amiiboapp.java.retrofit.utils;

import in.khatri.rahul.amiiboapp.java.model.GameDataList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("amiibo")
    Call<GameDataList> getGameData();
}
