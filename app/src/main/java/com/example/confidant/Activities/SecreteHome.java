package com.example.confidant.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.confidant.Domain.Secrete;
import com.example.confidant.R;
import com.example.confidant.Service.DatabaseHandler;

public class SecreteHome extends AppCompatActivity {

    TextView nameHome, passHome, descHome;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secrete_home);

        nameHome = findViewById(R.id.nameHome);
        passHome = findViewById(R.id.passHome);
        descHome = findViewById(R.id.descHome);
        index = getIntent().getIntExtra("index",-1);
        Toast.makeText(getApplicationContext(),String.valueOf(index),Toast.LENGTH_SHORT).show();
        final DatabaseHandler db = new DatabaseHandler(this);
        Secrete secrete = db.getSecrete(index+1);
        nameHome.setText(secrete.getSecreteName());
        passHome.setText(secrete.getSecreteKey());
        descHome.setText(secrete.getDescription());

    }
}
