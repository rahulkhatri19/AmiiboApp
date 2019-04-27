package `in`.khatri.rahul.amiiboapp.kotlin.retrofit.model

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

class GameModel {
    @SerializedName("amiibo")
    val gameList: ArrayList<GameDetailModel>? = null
}