package com.example.confidant.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.confidant.Domain.Secrete;
import com.example.confidant.R;
import com.example.confidant.Service.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Land extends AppCompatActivity {

    FloatingActionButton floatingActionButtonAdd;
    TextInputEditText search;
    String dataFromOther = null;
    SimpleAdapter adapter;
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
        search = findViewById(R.id.search);

        final DatabaseHandler db = new DatabaseHandler(this);
        final List<Secrete> secreteListObjects = db.getSecreteListObjects();

        LinkedHashMap<String, String> nameDesc = db.getSecreteList();

        List<HashMap<String, String>> listItems = new ArrayList<>();
        adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.list_text1, R.id.list_text2});


        Iterator it = nameDesc.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        secreteList.setAdapter(adapter);

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



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (Land.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
