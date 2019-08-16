package com.infinity.confidant.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.infinity.confidant.Domain.Secrete;
import com.infinity.confidant.R;
import com.infinity.confidant.Service.BottomSheetNavigationFragment;
import com.infinity.confidant.Service.DatabaseHandler;

import java.io.UnsupportedEncodingException;

public class SecreteHome extends AppCompatActivity {

    TextInputEditText nameHome, passHome, descHome, usernameHome;
    MaterialButton decrypt;
    FloatingActionButton floatEdit;
    public static int index;
    int decryptFlag = 0;

    BottomAppBar bottomAppBar;

    @Override
    public void onBackPressed(){
        Intent a = new Intent(SecreteHome.this,Land.class);
        startActivity(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                BottomSheetNavigationFragment bottomSheetNavigationFragment = new BottomSheetNavigationFragment();
                bottomSheetNavigationFragment.show(getSupportFragmentManager(), bottomSheetNavigationFragment.getTag());
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secrete_home);
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        bottomAppBar = findViewById(R.id.bottomBar);

        setSupportActionBar(bottomAppBar);

        nameHome = findViewById(R.id.nameHome);
        passHome = findViewById(R.id.passHome);
        passHome.setGravity(View.TEXT_ALIGNMENT_CENTER);
        descHome = findViewById(R.id.descHome);
        floatEdit = findViewById(R.id.floatEdit);
        decrypt = findViewById(R.id.decrypt);
        usernameHome = findViewById(R.id.nameHome_username);

        index = getIntent().getIntExtra("index",-1);
        //Toast.makeText(getApplicationContext(),String.valueOf(index),Toast.LENGTH_SHORT).show();
        final DatabaseHandler db = new DatabaseHandler(this);
        Secrete secrete = db.getSecrete(index);
        nameHome.setText(secrete.getSecreteName());
        passHome.setText(secrete.getSecreteKey());
        descHome.setText(secrete.getDescription());
        usernameHome.setText(secrete.getUsername());

        final Intent i = new Intent(SecreteHome.this,SecreteEdit.class);
        i.putExtra("index",index);

        floatEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });


        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(decryptFlag == 0) {
                    String decryptPass = "";
                    byte[] data = Base64.decode(secrete.getSecreteKey().trim(), Base64.DEFAULT);
                    try {
                        decryptPass = new String(data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    passHome.setText(decryptPass);
                    decryptFlag = 1;
                    vibe.vibrate(100);
                    Snackbar.make(view, "Decrypted!", Snackbar.LENGTH_SHORT).show();
                }
                else
                    Snackbar.make(view, "Already Decrypted!", Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}
