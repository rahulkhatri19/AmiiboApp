package `in`.khatri.rahul.amiiboapp.kotlin

import `in`.khatri.rahul.amiiboapp.R
import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.ClientInterface
import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.GameRetrofitAdapter
import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.model.GameDetailModel
import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.model.GameModel
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_retrofit)

      //
        ClientInterface.create().getGameData().enqueue(object : Callback<GameModel> {
            override fun onFailure(call: Call<GameModel>, t: Throwable) {
               Log.e("error: ", t.message)
            }

            override fun onResponse(call: Call<GameModel>, response: Response<GameModel>) {

                val list: ArrayList<GameDetailModel> = response.body()?.gameList!!

                recycleView.layoutManager= LinearLayoutManager(this@HomeRetrofitActivity)
                recycleView.adapter= GameRetrofitAdapter( list,this@HomeRetrofitActivity)
            }
        })
    }
}
