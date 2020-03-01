package apc.edu.ph.apcseminarmi151;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Creates an Intent to start MainActivity
                Intent intent = new Intent(SplashScreenActivity.this, Login.class);
                SplashScreenActivity.this.startActivity(intent);

                // This will destroy SplashScreenActivity
                SplashScreenActivity.this.finish();
            }
        }, 2500);

    }
}
