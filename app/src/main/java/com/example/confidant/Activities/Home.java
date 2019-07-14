package com.example.confidant.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.confidant.MainActivity;
import com.example.confidant.R;
import com.example.confidant.Service.DatabaseHandler;

public class Home extends AppCompatActivity {
    Button verify;
    EditText pin;
    View coordinatorLayoutView;
    PinLockView mPinLockView;
    IndicatorDots mIndicatorDots;
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

/*        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if(db.verifyPin(pin.getText().toString()) > 0)
                    startActivity(land);
                else
                    Snackbar.make(coordinatorLayoutView, "Wrong PIN!", Snackbar.LENGTH_SHORT).show();

            }
        });*/


        PinLockListener mPinLockListener = new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                if(db.verifyPin(pin) > 0)
                    startActivity(land);
                else
                    Snackbar.make(coordinatorLayoutView, "Wrong PIN!", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onEmpty() {
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
            }
        };


        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setShowDeleteButton(false);

        mPinLockView.setPinLockListener(mPinLockListener);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

        mPinLockView.attachIndicatorDots(mIndicatorDots);

    }

}
