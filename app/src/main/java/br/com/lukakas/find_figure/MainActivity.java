package br.com.lukakas.find_figure;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =
                findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
        Configuration c = getResources().getConfiguration();
        int screenLayout = c.screenLayout;
        int screenSize = screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize
                == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            setRequestedOrientation(ActivityInfo.
                    SCREEN_ORIENTATION_LANDSCAPE);
        }
        else{
            setRequestedOrientation(ActivityInfo.
                    SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
