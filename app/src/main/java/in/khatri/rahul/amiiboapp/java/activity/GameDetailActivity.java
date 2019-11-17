package in.khatri.rahul.amiiboapp.java.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import in.khatri.rahul.amiiboapp.R;

public class GameDetailActivity extends AppCompatActivity {
    TextView tvName, tvCharacter, tvGameSeries, tvHead, tvTail, tvType, tvAu, tvEu, tvJp, tvNa, tvAmSeries;
    ImageView ivProfile, ivArrowUp, ivArrowDown;
    LinearLayout llRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvName = findViewById(R.id.tv_name);
        tvCharacter = findViewById(R.id.tv_character);
        tvGameSeries = findViewById(R.id.tv_game_series);
        tvHead = findViewById(R.id.tv_head);
        tvTail = findViewById(R.id.tv_tail);
        tvType = findViewById(R.id.tv_type);
        tvAu = findViewById(R.id.tv_au);
        tvEu = findViewById(R.id.tv_eu);
        tvJp = findViewById(R.id.tv_jp);
        tvNa = findViewById(R.id.tv_na);
        tvAmSeries = findViewById(R.id.tv_amiibo_series);
        ivProfile = findViewById(R.id.iv_profile);
        ivArrowUp = findViewById(R.id.iv_up_arrow);
        ivArrowDown = findViewById(R.id.iv_down_arrow);
        llRelease = findViewById(R.id.ll_release);
        // = findViewById(R.id.);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvName.setText(getIntent().getStringExtra("name"));
        tvCharacter.setText(getIntent().getStringExtra("character"));
        tvGameSeries.setText(getIntent().getStringExtra("gameSeries"));
        tvHead.setText(getIntent().getStringExtra("head"));
        tvTail.setText(getIntent().getStringExtra("tail"));
        tvType.setText(getIntent().getStringExtra("type"));
        tvAu.setText(getIntent().getStringExtra("au"));
        tvEu.setText(getIntent().getStringExtra("eu"));
        tvJp.setText(getIntent().getStringExtra("jp"));
        tvNa.setText(getIntent().getStringExtra("na"));
        tvAmSeries.setText(getIntent().getStringExtra("amSeries"));
        //tv.setText(getIntent().getStringExtra(""));
        Glide.with(this).load(getIntent().getStringExtra("profile")).into(ivProfile);
        ivArrowDown.setVisibility(View.VISIBLE);
        ivArrowUp.setVisibility(View.GONE);
        llRelease.setVisibility(View.GONE);

        ivArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivArrowDown.setVisibility(View.VISIBLE);
                ivArrowUp.setVisibility(View.GONE);
                llRelease.setVisibility(View.GONE);
            }
        });
        ivArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivArrowDown.setVisibility(View.GONE);
                ivArrowUp.setVisibility(View.VISIBLE);
                llRelease.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar ab = getSupportActionBar();
        if (ab != null)
        {
            ab.setTitle("Game Detail");
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}
