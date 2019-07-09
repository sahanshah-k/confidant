package com.example.confidant.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.confidant.MainActivity;
import com.example.confidant.R;
import com.example.confidant.Service.DatabaseHandler;

public class Home extends AppCompatActivity {
    Button verify;
    EditText pin;
    View coordinatorLayoutView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        verify = findViewById(R.id.login);
        pin = findViewById(R.id.pin);
        coordinatorLayoutView = findViewById(R.id.home);

        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        final Intent land = new Intent(Home.this, Land.class);
        final DatabaseHandler db = new DatabaseHandler(this);

        if(db.getDetailsCount() > 0){
//            Intent setupPin = new Intent(Home.this, SetupPin.class);
//            startActivity(setupPin);
            Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_LONG).show();
        }
        else {
            Intent signup = new Intent(Home.this, MainActivity.class);
            startActivity(signup);
        }

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if(db.verifyPin(pin.getText().toString()) > 0)
                    startActivity(land);
                else
                    Snackbar.make(coordinatorLayoutView, "Wrong PIN!", Snackbar.LENGTH_SHORT).show();

            }
        });

    }

}
