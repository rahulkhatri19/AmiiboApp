package in.khatri.rahul.amiiboapp.java.retrofit.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import in.khatri.rahul.amiiboapp.R;
import in.khatri.rahul.amiiboapp.java.activity.GameDetailActivity;
import in.khatri.rahul.amiiboapp.java.retrofit.model.GameRetrofitModel;
import in.khatri.rahul.amiiboapp.java.retrofit.model.*;

public class GameRetrofitAdapter extends RecyclerView.Adapter<GameRetrofitAdapter.ViewHolder> {
    private ArrayList<GameRetrofitModel> gameModelArrayList;
    private Context mContext;

    public GameRetrofitAdapter(Context context, ArrayList<GameRetrofitModel> arrayList) {
        this.mContext = context;
        this.gameModelArrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_layout, null);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //  DisplayMetrics displayMetrics= new DisplayMetrics();
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        int width = point.x;
        view.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final GameRetrofitModel dataModel = gameModelArrayList.get(i);
        final Release release = dataModel.getRelease();
        viewHolder.setIsRecyclable(false);

        viewHolder.tvName.setTag(i);
        viewHolder.tvGameSeries.setTag(i);
        viewHolder.llData.setTag(i);

        viewHolder.tvName.setText(dataModel.getName());
        viewHolder.tvGameSeries.setText(dataModel.getGameSeries());
        Glide.with(mContext).load(dataModel.getImage()).placeholder(R.drawable.ic_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.ivProfile);
        // Glide.with(mContext).load(dataModel.getImage()).into(viewHolder.ivProfile);

        viewHolder.llData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("amSeries", dataModel.getAmiiboSeries());
                bundle.putString("name", dataModel.getName());
                bundle.putString("character", dataModel.getCharacter());
                bundle.putString("gameSeries", dataModel.getGameSeries());
                bundle.putString("au", release.getAu());
                bundle.putString("eu", release.getEu());
                bundle.putString("jp", release.getJp());
                bundle.putString("na", release.getNa());
                bundle.putString("head", dataModel.getHead());
                bundle.putString("tail", dataModel.getTail());
                bundle.putString("type", dataModel.getType());
                bundle.putString("profile", dataModel.getImage());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, viewHolder.ivProfile, "profileImage");
                    Intent intent = new Intent(mContext, GameDetailActivity.class).putExtras(bundle);
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(new Intent(mContext, GameDetailActivity.class).putExtras(bundle));
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
