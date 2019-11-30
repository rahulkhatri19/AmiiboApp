package `in`.khatri.rahul.amiiboapp.kotlin

import `in`.khatri.rahul.amiiboapp.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_game_detail.*

class GameDetailActivity : AppCompatActivity() {
    var ivArrowUp: ImageView? = null
    var ivArrowDown: ImageView? = null
    var llRelease: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        ivArrowUp = findViewById<ImageView>(R.id.iv_up_arrow)
        ivArrowDown = findViewById<ImageView>(R.id.iv_down_arrow)
        llRelease = findViewById<LinearLayout>(R.id.ll_release)
        Glide.with(this).load(getIntent().getStringExtra("profile")).into(iv_profile);
        tv_name.text = intent.getStringExtra("name")
        tv_character.text = intent.getStringExtra("character")
        tv_game_series.text = intent.getStringExtra("gameSeries")
        tv_head.text = intent.getStringExtra("head")
        tv_tail.text = intent.getStringExtra("tail")
        tv_type.text = intent.getStringExtra("type")
        tv_au.text = intent.getStringExtra("au")
        tv_eu.text = intent.getStringExtra("eu")
        tv_jp.text = intent.getStringExtra("jp")
        tv_na.text = intent.getStringExtra("na")
        tv_amiibo_series.text = intent.getStringExtra("amSeries")

        ivArrowDown?.visibility = View.VISIBLE
        ivArrowUp?.visibility = View.GONE
        llRelease?.visibility = View.GONE

        ivArrowUp?.setOnClickListener {
            ivArrowDown?.visibility = View.VISIBLE
            ivArrowUp?.visibility = View.GONE
            llRelease?.visibility = View.GONE
        }

        ivArrowDown?.setOnClickListener {
            ivArrowDown?.visibility = View.GONE
            ivArrowUp?.visibility = View.VISIBLE
            llRelease?.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        val ab = supportActionBar
        if (ab != null) {
            ab.title = "Game Detail"
            ab.setDisplayHomeAsUpEnabled(true)
        }
    }
}