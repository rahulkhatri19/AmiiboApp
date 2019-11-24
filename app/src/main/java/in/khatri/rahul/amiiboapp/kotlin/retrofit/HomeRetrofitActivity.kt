package `in`.khatri.rahul.amiiboapp.kotlin.retrofit

import `in`.khatri.rahul.amiiboapp.R
import `in`.khatri.rahul.amiiboapp.kotlin.adapter.GameRetrofitAdapter
import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.model.GameDetailModel
import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.model.GameModel
import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.utils.ClientInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_home.recycleView
import kotlinx.android.synthetic.main.activity_home_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_retrofit)
        shimmer_layout.visibility = View.VISIBLE
        recycleView.visibility = View.GONE
        shimmer_layout.startShimmer()
        getData()
      //
    }

    private fun getData() {
        ClientInterface.create().getGameData().enqueue(object : Callback<GameModel> {
            override fun onFailure(call: Call<GameModel>, t: Throwable) {
                Log.e("error: ", t.message)
            }

            override fun onResponse(call: Call<GameModel>, response: Response<GameModel>) {

                val list: ArrayList<GameDetailModel> = response.body()?.gameList!!

                recycleView.layoutManager= LinearLayoutManager(this@HomeRetrofitActivity)
                recycleView.adapter= GameRetrofitAdapter(list, this@HomeRetrofitActivity)
                shimmer_layout.stopShimmer()
                shimmer_layout.visibility = View.GONE
                recycleView.visibility = View.VISIBLE
            }
        })
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        shimmer_layout.startShimmer()
    }

    override fun onPause() {
        shimmer_layout.stopShimmer()
        super.onPause()
    }
}
