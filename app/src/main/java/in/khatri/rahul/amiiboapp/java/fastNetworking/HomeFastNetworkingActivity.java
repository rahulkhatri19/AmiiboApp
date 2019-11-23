package in.khatri.rahul.amiiboapp.java.fastNetworking;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.khatri.rahul.amiiboapp.R;
import in.khatri.rahul.amiiboapp.java.adapter.GameAdapter;
import in.khatri.rahul.amiiboapp.java.fastNetworking.model.GameModel;
import in.khatri.rahul.amiiboapp.java.fastNetworking.utils.WebServiceInterface;
import in.khatri.rahul.amiiboapp.java.fastNetworking.utils.WebserviceHandler;

public class HomeActivity extends AppCompatActivity {
    EditText etSearch;
    RecyclerView recyclerView;
    LinearLayout llNoData;
    GameModel gameModel;
    GameAdapter gameAdapter = null;
    ArrayList<GameModel> arrayListGame = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        etSearch = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.recycleView);
        llNoData = findViewById(R.id.ll_no_data);

        llNoData.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (!isOnline()) {
            checkNetwork();
        }
        gameData();
        gameAdapter = new GameAdapter(HomeActivity.this, arrayListGame);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    ArrayList<GameModel> searchList = new ArrayList<GameModel>();
                    for (int i = 0; i < arrayListGame.size(); i++) {
                        if (arrayListGame.get(i).getName().toLowerCase().contains(s))  /*|| arrayListGame.get(i).getGameSeries().toLowerCase().contains(s)*/ {
                            searchList.add(arrayListGame.get(i));
                        }
                    }
                    try {
                        gameAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(new GameAdapter(HomeActivity.this, searchList));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    gameAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(new GameAdapter(HomeActivity.this, arrayListGame));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void gameData() {
        arrayListGame.clear();
        WebserviceHandler webserviceHandler = new WebserviceHandler(this);
        webserviceHandler.serviceListener = new WebServiceInterface() {
            @Override
            public void onResponse(final String response) {
                Log.e("Game Res", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    final JSONArray jsonArray = jsonObject.getJSONArray("amiibo");
                    if (this != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        try {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                            JSONObject jsonObjectRelease = jsonObject1.getJSONObject("release");
                                            //                                         studentTreatmentModel.setTicket_id(jsonObjectStudent.getString("ticket_id"));
                                            gameModel = new GameModel(jsonObject1.getString("amiiboSeries"), jsonObject1.getString("character"), jsonObject1.getString("gameSeries"), jsonObject1.getString("head"), jsonObject1.getString("image"), jsonObject1.getString("name"), jsonObject1.getString("tail"), jsonObject1.getString("type"), jsonObjectRelease.getString("au"), jsonObjectRelease.getString("eu"), jsonObjectRelease.getString("jp"), jsonObjectRelease.getString("na"));
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
                                            Log.e("Release au", jsonObjectRelease.getString("au"));
                                            //  Log.e("Relase", jsonObject1.getString("release"));
                                            arrayListGame.add(gameModel);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            llNoData.setVisibility(View.GONE);
                                            recyclerView.setVisibility(View.VISIBLE);
                                            recyclerView.setAdapter(new GameAdapter(HomeActivity.this, arrayListGame));
                                        }
                                    });
                                } else {
                                    llNoData.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    llNoData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        };
        webserviceHandler.getGameData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isOnline()) {
            checkNetwork();
        }
        gameData();
    }

    private void checkNetwork() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this).setMessage("Please Connect to Internet");
        builder1.setCancelable(false).setIcon(R.drawable.ic_internet).setTitle("No Internet Connection").setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                }).setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!isOnline()) {
                    checkNetwork();
                } else {
                    dialog.dismiss();
                    gameData();
                }
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
        //super.onBackPressed();
    }
}
