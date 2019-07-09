package com.example.confidant.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.confidant.Domain.Details;
import com.example.confidant.R;
import com.example.confidant.Service.DatabaseHandler;

public class SetupPin extends AppCompatActivity {

    EditText pin;
    Button setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_pin);
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

    }
}