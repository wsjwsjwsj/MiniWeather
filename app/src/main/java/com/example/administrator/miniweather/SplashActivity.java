package com.example.administrator.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/12/22.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();
        if (preferences.getBoolean("firststart", true)) {
            editor.putBoolean("firststart", false);
            editor.commit();
            Intent intent = new Intent(SplashActivity.this, Guide.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
       }
    }

}