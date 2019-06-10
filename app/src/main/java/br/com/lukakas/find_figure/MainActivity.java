package br.com.lukakas.find_figure;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public static boolean checked;
    public FirebaseDatabase database;
    public DatabaseReference ref;
    public static String text0 = "", text1 = "", text2 = "", text3 = "";
    public static ArrayList<String> listAma = new ArrayList<String>();
    public static ArrayList<String> listBom = new ArrayList<String>();
    public static ArrayList<String> listPago = new ArrayList<String>();
    public static ArrayList<String> listPrecisa = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("texts");
        getCurrentIkigai();
        getSuggestions();
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
                == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            setRequestedOrientation(ActivityInfo.
                    SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.
                    SCREEN_ORIENTATION_PORTRAIT);
        }
        mcolorCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mcolorCheckBox.isChecked()) {
                    //Perform action when you touch on checkbox and it change to selected state
                    checked = true;
                } else {
                    //Perform action when you touch on checkbox and it change to unselected state
                    checked = false;
                }
            }
        });
    }

    public synchronized void getCurrentIkigai() {
        ref.child("ama").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                text0 = (String) ((HashMap) dataSnapshot.getValue()).values().toArray()[0];
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        ref.child("bom").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                text1 = (String) ((HashMap) dataSnapshot.getValue()).values().toArray()[0];
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        ref.child("pago").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                text2 = (String) ((HashMap) dataSnapshot.getValue()).values().toArray()[0];
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        ref.child("precisa").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                text3 = (String) ((HashMap) dataSnapshot.getValue()).values().toArray()[0];
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void getSuggestions() {
        ref.child("ama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Name = (String) ds.getValue();
                    listAma.add(Name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("bom").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Name = (String) ds.getValue();
                    listBom.add(Name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ref.child("pago").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Name = (String) ds.getValue();
                    listPago.add(Name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ref.child("precisa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Name = (String) ds.getValue();
                    listPrecisa.add(Name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
