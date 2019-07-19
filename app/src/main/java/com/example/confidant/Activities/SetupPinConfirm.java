package com.example.confidant.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.confidant.Domain.Details;
import com.example.confidant.R;
import com.example.confidant.Service.DatabaseHandler;

public class SetupPinConfirm extends AppCompatActivity {

    EditText pin;
    Button setup;
    PinLockView mPinLockView;
    IndicatorDots mIndicatorDots;

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
        setContentView(R.layout.setup_pin_confirm);
        final DatabaseHandler db = new DatabaseHandler(this);
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Toast.makeText(getApplicationContext(),"Confirm PIN", Toast.LENGTH_SHORT).show();



/*
        pin = findViewById(R.id.pin);
        setup = findViewById(R.id.setup);
        final DatabaseHandler db = new DatabaseHandler(this);

        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(pin.getText()) || pin.getText().length()!=4)
                    pin.setError("Enter 4 digit PIN!");
                else {
                    Details details = (Details) getIntent().getSerializableExtra("details");
                    details.setPin(Integer.parseInt(pin.getText().toString()));
                    Toast.makeText(getApplicationContext(), details.getName(), Toast.LENGTH_LONG).show();
                    db.addDetails(details);
                }
            }
        });
*/


        PinLockListener mPinLockListener = new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                vibe.vibrate(100);
                Details details = (Details) getIntent().getSerializableExtra("details");
                if(pin.equals(String.valueOf(details.getPin()))){
                    Intent land = new Intent(SetupPinConfirm.this, Land.class);
                    Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                    db.addDetails(details);
                    startActivity(land);
                }
                else{
                    vibe.vibrate(1000);
                    Intent pinPage = new Intent(SetupPinConfirm.this, SetupPin.class);
                    Toast.makeText(getApplicationContext(),"PINs doesn't match!", Toast.LENGTH_SHORT).show();
                    pinPage.putExtra("details", details);
                    startActivity(pinPage);
                }
            }

            @Override
            public void onEmpty() {
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
                vibe.vibrate(100);
            }
        };


        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view_setup_confirm);
        mPinLockView.setShowDeleteButton(false);

        mPinLockView.setPinLockListener(mPinLockListener);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots_setup_confirm);
        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

        mPinLockView.attachIndicatorDots(mIndicatorDots);


    }
}