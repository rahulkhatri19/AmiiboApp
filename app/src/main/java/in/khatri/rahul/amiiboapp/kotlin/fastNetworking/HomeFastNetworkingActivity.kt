package `in`.khatri.rahul.amiiboapp.kotlin.fastNetworking

import `in`.khatri.rahul.amiiboapp.R
import `in`.khatri.rahul.amiiboapp.kotlin.fastNetworking.adapter.GameAdapter
import `in`.khatri.rahul.amiiboapp.kotlin.fastNetworking.model.GameModel
import `in`.khatri.rahul.amiiboapp.kotlin.fastNetworking.utils.WebServiceInterface
import `in`.khatri.rahul.amiiboapp.kotlin.fastNetworking.utils.WebserviceHandler
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_fast_networking.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class HomeFastNetworkingActivity : AppCompatActivity() {

    private var gameAdapter: GameAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private var arrayListGame = ArrayList<GameModel>()
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_fast_networking)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        if (!isOnline()) checkNetwork()
        shimmer_layout.visibility = View.VISIBLE
        shimmer_layout.startShimmer()
        ll_search.visibility = View.GONE
        recyclerView.visibility = View.GONE
        gameData()
        gameAdapter = GameAdapter(arrayListGame, this@HomeFastNetworkingActivity)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    val searchList = ArrayList<GameModel>()
                    for (i in arrayListGame.indices) {
                        if (arrayListGame[i].name.toLowerCase().contains(s)) {
                            searchList.add(arrayListGame[i])
                        }
                    }
                    try {
                        gameAdapter?.notifyDataSetChanged()
                        recyclerView.adapter = GameAdapter(searchList, this@HomeFastNetworkingActivity)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    gameAdapter?.notifyDataSetChanged()
                    recyclerView.adapter = gameAdapter
                }
            }

            /*     fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                 }

                 fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                     if (s != "") {
                         val searchList = java.util.ArrayList<`in`.khatri.rahul.amiiboapp.java.fastNetworking.model.GameModel>()
                         for (i in arrayListGame.indices) {
                             if (arrayListGame[i].name.toLowerCase().contains(s)) *//*|| arrayListGame.get(i).getGameSeries().toLowerCase().contains(s)*//* {
                            searchList.add(arrayListGame[i])
                        }
                    }
                    try {
                        gameAdapter!!.notifyDataSetChanged()
                        recyclerView.adapter = `in`.khatri.rahul.amiiboapp.java.fastNetworking.adapter.GameAdapter(this@HomeFastNetworkingActivity, searchList)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    gameAdapter!!.notifyDataSetChanged()
                    recyclerView.adapter = gameAdapter
                }
            }*/
        })
    }

    private fun gameData() {
        arrayListGame.clear()
        val webserviceHandler = WebserviceHandler()
        webserviceHandler.serviceListener = object : WebServiceInterface {
            override fun onResponse(response: String?) {
                    //                Log.e("Game Res", response);
                    try {
                        val jsonObject = JSONObject(response)
                        val jsonArray = jsonObject.getJSONArray("amiibo")
                        if (this != null) {
                            runOnUiThread {
                                if (jsonArray.length() > 0) {
                                    for (i in 0 until jsonArray.length()) {
                                        try {
                                            val jsonObject1 = jsonArray.getJSONObject(i)
                                            val jsonObjectRelease = jsonObject1.getJSONObject("release")
                                            //                                         studentTreatmentModel.setTicket_id(jsonObjectStudent.getString("ticket_id"));
                                            val gameModel = GameModel(jsonObject1.getString("amiiboSeries"), jsonObject1.getString("character"), jsonObject1.getString("gameSeries"), jsonObject1.getString("head"), jsonObject1.getString("image"), jsonObject1.getString("name"), jsonObject1.getString("tail"), jsonObject1.getString("type"), jsonObjectRelease.getString("au"), jsonObjectRelease.getString("eu"), jsonObjectRelease.getString("jp"), jsonObjectRelease.getString("na"))
                                            arrayListGame.add(gameModel)

                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }


                                    }
                                    runOnUiThread {
                                        //                                    llNoData.setVisibility(View.GONE)
                                        shimmer_layout.visibility = View.GONE
                                        shimmer_layout.stopShimmer()
                                        ll_search.visibility = View.VISIBLE
                                        recyclerView.visibility = View.VISIBLE
                                        recyclerView.adapter = gameAdapter
                                    }
                                } else {
//                                llNoData.setVisibility(View.VISIBLE)
                                    shimmer_layout.visibility = View.VISIBLE
                                    shimmer_layout.startShimmer()
                                    ll_search.visibility = View.GONE
                                    recyclerView.visibility = View.GONE
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
//                    llNoData.setVisibility(View.VISIBLE)
                        shimmer_layout.visibility = View.VISIBLE
                        shimmer_layout.startShimmer()
                        ll_search.visibility = View.GONE
                        recyclerView.visibility = View.GONE
                    }
            }
        }
        webserviceHandler.getGameData()
    }

    private fun isOnline(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnectedOrConnecting
    }

    private fun checkNetwork() {
        val builder = AlertDialog.Builder(this)
                .setMessage("Please Connect to Internet")
                .setCancelable(false)
                .setIcon(R.drawable.ic_internet)
                .setTitle("No Internet Connection")
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                    finish()
                }).setNegativeButton("Retry", DialogInterface.OnClickListener { dialog, which ->
                    if (!isOnline()) {
                        checkNetwork()
                    } else {
                        dialog.dismiss()
                        gameData()
                    }
                })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onResume() {
        super.onResume()
        shimmer_layout.startShimmer()
        if (!isOnline()) checkNetwork()
        else gameData()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            moveTaskToBack(true)
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        shimmer_layout.stopShimmer()
    }
}
