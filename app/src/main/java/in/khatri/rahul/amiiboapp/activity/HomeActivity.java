package in.khatri.rahul.amiiboapp.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.khatri.rahul.amiiboapp.R;
import in.khatri.rahul.amiiboapp.model.GameModel;
import in.khatri.rahul.amiiboapp.utils.WebServiceInterface;
import in.khatri.rahul.amiiboapp.utils.WebserviceHandler;

public class HomeActivity extends AppCompatActivity {
    EditText etSearch;
    RecyclerView recyclerView;
    LinearLayout llNoData;
    GameModel gameModel;
    GameAdapter gameAdapter= null;
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
        gameAdapter= new GameAdapter(HomeActivity.this, arrayListGame);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    ArrayList<GameModel> searchList = new ArrayList();
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
                                            //                                         studentTreatmentModel.setTicket_id(jsonObjectStudent.getString("ticket_id"));
                                            gameModel = new GameModel();
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

    public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
        private ArrayList<GameModel> gameModelArrayList;
        private Context mContext;

        public GameAdapter(Context context, ArrayList<GameModel> arrayList) {
            this.mContext = context;
            this.gameModelArrayList = arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_layout, null);
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int width = windowManager.getDefaultDisplay().getWidth();
            view.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new GameAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

            final GameModel dataModel = gameModelArrayList.get(i);
            viewHolder.setIsRecyclable(false);

            viewHolder.tvName.setTag(i);
            viewHolder.tvGameSeries.setTag(i);
            viewHolder.llData.setTag(i);
            //   viewHolder.ivProfile.setTag(i);

            viewHolder.tvName.setText(dataModel.getName());
            viewHolder.tvGameSeries.setText(dataModel.getGameSeries());
            //  Glide.with(mContext).load(dataModel.getImage()).placeholder().into(viewHolder.ivProfile);
            Glide.with(mContext).load(dataModel.getImage()).into(viewHolder.ivProfile);

            viewHolder.llData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    Toast.makeText(mContext, "It works", Toast.LENGTH_SHORT).show();
                    /*
                    *  Bundle bundle=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bundle= ActivityOptions.makeSceneTransitionAnimation(SharedElement.this,imageView,imageView.getTransitionName()).toBundle();
            startActivity(intent,bundle);
        }*/
                    Bundle bundle = new Bundle();
                    bundle.putString("amSeries", dataModel.getAmiiboSeries());
                    bundle.putString("name", dataModel.getName());
                    bundle.putString("character", dataModel.getCharacter());
                    bundle.putString("gameSeries", dataModel.getGameSeries());
                    bundle.putString("au", dataModel.getAu());
                    bundle.putString("eu", dataModel.getEu());
                    bundle.putString("jp", dataModel.getJp());
                    bundle.putString("na", dataModel.getNa());
                    bundle.putString("head", dataModel.getHead());
                    bundle.putString("tail", dataModel.getTail());
                    bundle.putString("type", dataModel.getType());
                    bundle.putString("profile", dataModel.getImage());
                   // Bundle aniBundle= null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, viewHolder.ivProfile, "profileImage");
                        Intent intent = new Intent(mContext, GameDetailActivity.class).putExtras(bundle);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(new Intent(mContext, GameDetailActivity.class).putExtras(bundle));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return (null != gameModelArrayList ? gameModelArrayList.size() : 0);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvGameSeries;
            LinearLayout llData;
            ImageView ivProfile;

            public ViewHolder(View view) {
                super(view);
                this.tvName = view.findViewById(R.id.tv_name);
                this.tvGameSeries = view.findViewById(R.id.tv_game_series);
                this.ivProfile = view.findViewById(R.id.iv_profile);
                this.llData = view.findViewById(R.id.ll_data);

            }
        }
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
