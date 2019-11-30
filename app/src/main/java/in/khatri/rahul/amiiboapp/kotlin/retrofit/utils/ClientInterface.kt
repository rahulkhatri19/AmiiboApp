package `in`.khatri.rahul.amiiboapp.kotlin.retrofit.utils


import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.model.GameModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ClientInterface {
@GET("amiibo")
abstract fun getGameData() : Call<GameModel>

    companion object {
        val Base_Url= "https://www.amiiboapi.com/api/"
        fun create(): ClientInterface {
            val retrofit= Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(Base_Url).build()
            return retrofit.create(ClientInterface::class.java)
        }
    }
}