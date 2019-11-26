package `in`.khatri.rahul.amiiboapp.kotlin.fastNetworking

import `in`.khatri.rahul.amiiboapp.R
import `in`.khatri.rahul.amiiboapp.java.fastNetworking.utils.WebServiceInterface
import `in`.khatri.rahul.amiiboapp.java.fastNetworking.utils.WebserviceHandler
import `in`.khatri.rahul.amiiboapp.kotlin.adapter.GameAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import org.json.JSONException
import org.json.JSONObject

class HomeFastNetworkingActivity : AppCompatActivity() {

    private var gameAdapter: GameAdapter? = null
    private lateinit var recyclerView:RecyclerView
    private var arrayListGame = ArrayList<GameModel>()
    private val doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_fast_networking)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        if (!isOnline()) checkNetwork()

        gameData()
    }

    private fun gameData() {
        arrayListGame.clear()
        val webserviceHandler = WebserviceHandler(this)
        webserviceHandler.serviceListener = object : WebServiceInterface {
            override fun onResponse(response: String) {
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
                                        /* gameModel.setAmiiboSeries(jsonObject1.getString("amiiboSeries"));
                            gameModel.setCharacter(jsonObject1.getString("character"));
                            gameModel.setGameSeries(jsonObject1.getString("gameSeries"));
                            gameModel.setHead(jsonObject1.getString("head"));
                            gameModel.setImage(jsonObject1.getString("image"));
                            gameModel.setName(jsonObject1.getString("name"));
                            gameModel.setTail(jsonObject1.getString("tail"));
                            gameModel.setType(jsonObject1.getString("type"));

                            gameModel.setAu(jsonObjectRelease.getString("au"));
                            gameModel.setEu(jsonObjectRelease.getString("eu"));
                            gameModel.setJp(jsonObjectRelease.getString("jp"));
                            gameModel.setNa(jsonObjectRelease.getString("na"));*/
                                        //                                            Log.e("Release au", jsonObjectRelease.getString("au"));
                                        //  Log.e("Relase", jsonObject1.getString("release"));
                                        arrayListGame.add(gameModel)

                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }


                                }
                                runOnUiThread {
//                                    llNoData.setVisibility(View.GONE)
                                    recyclerView.visibility = View.VISIBLE
                                    recyclerView.adapter = GameAdapter(arrayListGame, this@HomeFastNetworkingActivity)
                                }
                            } else {
//                                llNoData.setVisibility(View.VISIBLE)
                                recyclerView.visibility = View.GONE
                            }
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
//                    llNoData.setVisibility(View.VISIBLE)
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

    private fun checkNetwork(){

    }
}
