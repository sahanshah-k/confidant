package com.infinity.confidant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.confidant.Domain.Secrete;
import com.infinity.confidant.R;
import com.infinity.confidant.Service.BottomSheetNavigationFragmentLand;
import com.infinity.confidant.Service.DatabaseHandler;
import com.infinity.confidant.Service.MyAdapter;

import java.util.Iterator;
import java.util.List;

public class Land extends AppCompatActivity {

    FloatingActionButton floatingActionButtonAdd;
    TextInputEditText search;
    MyAdapter myAdapter;
    TextView firstHelp, searchNotFound;
    ListView secreteList;
    BottomAppBar bottomAppBar;

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                BottomSheetNavigationFragmentLand bottomSheetNavigationFragment = new BottomSheetNavigationFragmentLand();
                bottomSheetNavigationFragment.show(getSupportFragmentManager(), bottomSheetNavigationFragment.getTag());
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.land_page);


        bottomAppBar = findViewById(R.id.landBottom);

        setSupportActionBar(bottomAppBar);
        secreteList = findViewById(R.id.listSecrete);
        floatingActionButtonAdd = findViewById(R.id.addNewSecrete);
        firstHelp = findViewById(R.id.firstHelp);
        searchNotFound = findViewById(R.id.searchNotFound);

        search = findViewById(R.id.search);

        final DatabaseHandler db = new DatabaseHandler(this);
        final List<Secrete> secreteListObjects = db.getSecreteListObjects();

        if(secreteListObjects.size() < 1) {
            firstHelp.setVisibility(View.VISIBLE);
            search.setVisibility(View.GONE);
        }

        myAdapter = new MyAdapter(getApplicationContext(), secreteListObjects);

        secreteList.setAdapter(myAdapter);

        secreteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Land.this,SecreteHome.class);
                //intent.putExtra("index",myAdapter.getItem(0);
                Secrete secreteItem = (Secrete) myAdapter.getItem(i);
                intent.putExtra("index",secreteItem.getId());
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

                if(!containsName(secreteListObjects,charSequence.toString())){
                    searchNotFound.setVisibility(View.VISIBLE);
                } else {
                    searchNotFound.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                myAdapter.getFilter().filter(editable);
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

    public boolean containsName(final List<Secrete> list, final String name){
        for (Iterator<Secrete> i = list.iterator(); i.hasNext();) {
            if (i.next().getSecreteName().toLowerCase().contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
