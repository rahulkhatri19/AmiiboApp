package in.khatri.rahul.amiiboapp.java.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.khatri.rahul.amiiboapp.R;
import in.khatri.rahul.amiiboapp.kotlin.HomeRetrofitActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              //  startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                startActivity(new Intent(SplashActivity.this, HomeRetrofitActivity.class));
            }
        }, 1500);
    }
}
