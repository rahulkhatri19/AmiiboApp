package in.khatri.rahul.amiiboapp.java.retrofit;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import in.khatri.rahul.amiiboapp.R;
import in.khatri.rahul.amiiboapp.java.adapter.GameRetrofitAdapter;
import in.khatri.rahul.amiiboapp.java.model.GameDataList;
import in.khatri.rahul.amiiboapp.java.model.GameRetrofitModel;
import in.khatri.rahul.amiiboapp.java.retrofit.utils.ApiInterface;
import in.khatri.rahul.amiiboapp.java.retrofit.utils.ClientApi;
import in.khatri.rahul.amiiboapp.java.utils.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeRetrofitActivity extends AppCompatActivity {
    EditText etSearch;
    RecyclerView recyclerView;
    LinearLayout llNoData;
    SpotsDialog progressDialog;
    GameRetrofitAdapter gameRetrofitAdapter = null;
    ArrayList<GameRetrofitModel> arrayListGame = new ArrayList<>();
    private ApiInterface apiInterface;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etSearch = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.recycleView);
        llNoData = findViewById(R.id.ll_no_data);
        progressDialog = (SpotsDialog) new SpotsDialog.Builder().setContext(HomeRetrofitActivity.this).build();
        progressDialog.setMessage("Please Wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        llNoData.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (!isOnline()) {
            checkNetwork();
        }
        apiInterface = ClientApi.getClient().create(ApiInterface.class);
        gameData();
        gameRetrofitAdapter = new GameRetrofitAdapter(this, arrayListGame);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("et", "log");
                if (!s.equals("")) {
                    ArrayList<GameRetrofitModel> searchList = new ArrayList();
                    for (int i = 0; i < arrayListGame.size(); i++) {
                        if (arrayListGame.get(i).getName().toLowerCase().contains(s) || arrayListGame.get(i).getGameSeries().toLowerCase().contains(s)) {
                            searchList.add(arrayListGame.get(i));
                        }
                    }
                    try {
                        gameRetrofitAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(new GameRetrofitAdapter(HomeRetrofitActivity.this, searchList));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    gameRetrofitAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(new GameRetrofitAdapter(HomeRetrofitActivity.this, arrayListGame));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void gameData() {
        arrayListGame.clear();

        Call<GameDataList> gameCall = apiInterface.getGameData();
        gameCall.enqueue(new Callback<GameDataList>() {
            @Override
            public void onResponse(Call<GameDataList> call, Response<GameDataList> response) {
                arrayListGame = response.body().getDataList();
                //   getGameData(stResponse);
                //   ArrayList<GameRetrofitModel> list= response.body().getDataList();
                //   Log.e("list", String.valueOf(list.size()));
                llNoData.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(new GameRetrofitAdapter(HomeRetrofitActivity.this, arrayListGame));
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GameDataList> call, Throwable t) {
                Log.e("error", t.getMessage());
                progressDialog.dismiss();

            }
        });

    }

  /*  private void getGameData(String response) {
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

                                *//*    gameModel = new GameModel();
                                    gameModel.setAmiiboSeries(jsonObject1.getString("amiiboSeries"));
                                    gameModel.setCharacter(jsonObject1.getString("character"));
                                    gameModel.setGameSeries(jsonObject1.getString("gameSeries"));
                                    gameModel.setHead(jsonObject1.getString("head"));
                                    gameModel.setImage(jsonObject1.getString("image"));
                                    gameModel.setName(jsonObject1.getString("name"));
                                    gameModel.setTail(jsonObject1.getString("tail"));
                                    gameModel.setType(jsonObject1.getString("type"));
                                    JSONObject jsonObjectRelease = jsonObject1.getJSONObject("release");
                                    gameModel.setAu(jsonObjectRelease.getString("au"));
                                    gameModel.setEu(jsonObjectRelease.getString("eu"));
                                    gameModel.setJp(jsonObjectRelease.getString("jp"));
                                    gameModel.setNa(jsonObjectRelease.getString("na"));
                                    Log.e("Release au", jsonObjectRelease.getString("au"));
                                    //  Log.e("Relase", jsonObject1.getString("release"));
                                    arrayListGame.add(gameModel);*//*

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    llNoData.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(new GameRetrofitAdapter(HomeRetrofitActivity.this, arrayListGame));
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
    }*/

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

    private void checkNetwork() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeRetrofitActivity.this).setMessage("Please Connect to Internet");
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

}
