package com.example.confidant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.confidant.Activities.SetupPin;
import com.example.confidant.Domain.Details;
import com.example.confidant.Domain.Secrete;
import com.example.confidant.Service.DatabaseHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name, mail;
    Button next;
    View coordinatorLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mailId);
        next = findViewById(R.id.next);

        //List<Secrete> secreteList = db.getAllContacts();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(name.getText()))
//            Snackbar.make(coordinatorLayoutView, "Working good!", Snackbar.LENGTH_SHORT)
//                    .show();
                    name.setError("Name required!");
                else if(TextUtils.isEmpty(mail.getText()))
                    mail.setError("Mail required");
                else {
                    Details details = new Details(mail.getText().toString(),name.getText().toString(),1);
                    Intent setupPin = new Intent(MainActivity.this, SetupPin.class);
                    setupPin.putExtra("details", details);
                    startActivity(setupPin);
                }
            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
