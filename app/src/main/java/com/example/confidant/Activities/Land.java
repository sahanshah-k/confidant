package com.example.confidant.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Land extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.land_page);
        ListView secreteList = findViewById(R.id.listSecrete);

       Secrete secrete = new Secrete("Canara22","testest","hjhkhk");


        final DatabaseHandler db = new DatabaseHandler(this);
        //db.addSecrete(secrete);
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
                intent.putExtra("index",i);
                //based on item add info to intent
                startActivity(intent);
            }
        });

    }
}
