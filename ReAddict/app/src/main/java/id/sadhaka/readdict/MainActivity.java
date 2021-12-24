package id.sadhaka.readdict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    //konstanta untuk splash screen selama 3 detik
    private static int SPLASH_SCREEN_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //menghilangkan toolbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //jeda antara splash screen dengan landing page
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //intent untuk mengarahkan MainActivity ke class LandingPage
                Intent intent = new Intent(MainActivity.this,LandingPage.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        }, SPLASH_SCREEN_TIME);
    }
}