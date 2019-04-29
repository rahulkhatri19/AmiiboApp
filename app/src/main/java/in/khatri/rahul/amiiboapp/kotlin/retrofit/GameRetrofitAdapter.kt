package `in`.khatri.rahul.amiiboapp.kotlin.retrofit

import `in`.khatri.rahul.amiiboapp.R
import `in`.khatri.rahul.amiiboapp.java.activity.GameDetailActivity
import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.model.GameDetailModel
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.game_layout.view.*

class GameRetrofitAdapter(val item: ArrayList<GameDetailModel>, val context: Context) : RecyclerView.Adapter<GameRetrofitAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val list: GameDetailModel = item.get(p1)
        p0.tvName?.text = list.name
        p0.tvGameSeries?.text = list.gameSeries
        Glide.with(context).load(list.image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.ic_placeholder).into(p0.profile)

        p0.llData.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("amSeries", list.amiiboSeries)
            bundle.putString("name", list.name)
            bundle.putString("character", list.character)
            bundle.putString("gameSeries", list.gameSeries)
            bundle.putString("head", list.head)
            bundle.putString("tail", list.tail)
            bundle.putString("type", list.type)
            bundle.putString("profile", list.image)
            bundle.putString("au", list.release?.au)
            bundle.putString("eu", list.release?.eu)
            bundle.putString("jp", list.release?.jp)
            bundle.putString("na", list.release?.na)
            context.startActivity(Intent(context, GameDetailActivity::class.java).putExtras(bundle))

        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.game_layout, p0, false))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.tv_name
        val tvGameSeries = view.tv_game_series
        val profile = view.iv_profile
        val llData= view.ll_data
    }
}