package com.example.confidant.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.confidant.Domain.Secrete;
import com.example.confidant.R;
import com.example.confidant.Service.DatabaseHandler;

import java.util.List;

public class Land extends AppCompatActivity {

    FloatingActionButton floatingActionButtonAdd;
    String dataFromOther = null;
    
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.land_page);
        ListView secreteList = findViewById(R.id.listSecrete);
        floatingActionButtonAdd = findViewById(R.id.addNewSecrete);

        final DatabaseHandler db = new DatabaseHandler(this);
        final List<Secrete> secreteListObjects = db.getSecreteListObjects();


        List<String> secreteListTitle = db.getSecreteListTitle();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                secreteListTitle );
        secreteList.setAdapter(arrayAdapter);

        secreteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Land.this,SecreteHome.class);
                intent.putExtra("index",secreteListObjects.get(i).getId());
                //based on item add info to intent
                startActivity(intent);
            }
        });


        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Land.this,SecreteNew.class);
                startActivity(i);
            }
        });

    }
}
