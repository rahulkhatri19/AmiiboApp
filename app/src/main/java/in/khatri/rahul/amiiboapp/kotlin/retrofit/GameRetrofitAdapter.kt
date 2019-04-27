package `in`.khatri.rahul.amiiboapp.kotlin.retrofit

import `in`.khatri.rahul.amiiboapp.R
import `in`.khatri.rahul.amiiboapp.kotlin.retrofit.model.GameDetailModel
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.game_layout.view.*

class GameRetrofitAdapter(val item: ArrayList<GameDetailModel>, val context: Context) : RecyclerView.Adapter<GameRetrofitAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
      return item.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val list: GameDetailModel= item.get(p1)
        p0.tvName?.text=list.name
        p0.tvGameSeries?.text= list.gameSeries
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.game_layout, p0, false))
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvName= view.tv_name
        val tvGameSeries= view.tv_game_series
        val profile= view.iv_profile
    }
}