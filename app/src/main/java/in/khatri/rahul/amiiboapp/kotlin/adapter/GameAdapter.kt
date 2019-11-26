package `in`.khatri.rahul.amiiboapp.kotlin.adapter

import `in`.khatri.rahul.amiiboapp.R
import `in`.khatri.rahul.amiiboapp.java.activity.GameDetailActivity
import `in`.khatri.rahul.amiiboapp.kotlin.fastNetworking.GameModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.game_layout.view.*

class GameAdapter(val item: ArrayList<GameModel>, val context: Context) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val list: GameModel = item.get(p1)
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
            bundle.putString("au", list.au)
            bundle.putString("eu", list.eu)
            bundle.putString("jp", list.jp)
            bundle.putString("na", list.na)
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
        val llData = view.ll_data
    }
}