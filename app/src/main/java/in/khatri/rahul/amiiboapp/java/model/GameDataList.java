package in.khatri.rahul.amiiboapp.java.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameDataList {
    @SerializedName("amiibo")
    private ArrayList<GameRetrofitModel> dataList;

    public GameDataList(ArrayList<GameRetrofitModel> dataList) {
        this.dataList = dataList;
    }

    public ArrayList<GameRetrofitModel> getDataList() {
        return dataList;
    }
}
