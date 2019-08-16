package com.infinity.confidant.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.infinity.confidant.Domain.Details;
import com.infinity.confidant.R;

public class SetupPin extends AppCompatActivity {

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
        setContentView(R.layout.setup_pin);
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Toast.makeText(getApplicationContext(),"Enter new PIN!", Toast.LENGTH_SHORT).show();


        PinLockListener mPinLockListener = new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                vibe.vibrate(100);
                Details details = (Details) getIntent().getSerializableExtra("details");
                details.setPin(pin.trim());
                Intent confirm = new Intent(SetupPin.this, SetupPinConfirm.class);
                confirm.putExtra("details", details);
                startActivity(confirm);
            }

            @Override
            public void onEmpty() {
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
                    vibe.vibrate(100);
            }
        };


        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view_setup);
        mPinLockView.setShowDeleteButton(false);

        mPinLockView.setPinLockListener(mPinLockListener);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots_setup);
        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

        mPinLockView.attachIndicatorDots(mIndicatorDots);


    }
}