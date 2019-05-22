package rk.rabbitt.ridi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rk.rabbitt.ridi.preference.PrefsManager;

public class SplashScreen extends AppCompatActivity {

    public static final String LOG_TAG = "splash";

    private Handler splash = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splash.postDelayed(() -> {
            try {
                PrefsManager prefManager = new PrefsManager(getApplicationContext());
                if (prefManager.isFirstTimeLaunch()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception ex) {
                Log.i(LOG_TAG, ex.getMessage());
                ex.printStackTrace();
            }
        }, 2000);
    }
}
