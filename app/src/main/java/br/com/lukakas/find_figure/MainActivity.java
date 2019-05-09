package br.com.lukakas.find_figure;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    public static boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox mcolorCheckBox = findViewById(R.id.colorCheckBox);
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
        mcolorCheckBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mcolorCheckBox.isChecked())
                {
                    //Perform action when you touch on checkbox and it change to selected state
                    checked = true;
                }
                else
                {
                    //Perform action when you touch on checkbox and it change to unselected state
                    checked = false;
                }
            }
        });
    }


}
