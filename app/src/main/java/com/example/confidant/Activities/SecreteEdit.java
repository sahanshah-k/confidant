package com.example.confidant.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.confidant.Domain.Secrete;
import com.example.confidant.MainActivity;
import com.example.confidant.R;
import com.example.confidant.Service.DatabaseHandler;

import java.io.UnsupportedEncodingException;

public class SecreteEdit extends AppCompatActivity {
    TextInputEditText nameHome, passHome, descHome, confPass;
    Editable name, pass, conf, desc;
    FloatingActionButton editButton;

    int index;

    @Override
    public void onBackPressed(){
        Intent a = new Intent(SecreteEdit.this,Land.class);
        startActivity(a);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secrete_edit);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        index = getIntent().getIntExtra("index",-1);
       // Toast.makeText(getApplicationContext(),String.valueOf(index),Toast.LENGTH_SHORT).show();


        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_Dialog_Alert);
        AlertDialog.Builder builderConfirm = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_Dialog_Alert);
        builder.setTitle("Error");
        builder.setMessage("Pass-phrases don't match!");
        builder.setCancelable(false);
        builder.setNeutralButton("Okay",null);

        final AlertDialog dialog = builder.create();


        nameHome = findViewById(R.id.editName);
        passHome = findViewById(R.id.editPass);
        descHome = findViewById(R.id.editDesc);
        confPass = findViewById(R.id.editConfPass);



        editButton = findViewById(R.id.floatEditSet);
        final DatabaseHandler db = new DatabaseHandler(this);

        Secrete secreteFetch = db.getSecrete(index);
        nameHome.setText(secreteFetch.getSecreteName());
        descHome.setText(secreteFetch.getDescription());

        name = nameHome.getText();
        pass = passHome.getText();
        conf = confPass.getText();
        desc = descHome.getText();

        final Intent i = new Intent(SecreteEdit.this,Land.class);

        builderConfirm.setTitle("Update Confirm")
                .setMessage("Press \"Yes\" to add!")
                .setCancelable(false)
                .setNeutralButton("No",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        name = nameHome.getText();
                        pass = passHome.getText();
                        desc = descHome.getText();
                        byte[] data = new byte[0];
                        try {
                            data = pass.toString().getBytes("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String base64pass = Base64.encodeToString(data, Base64.DEFAULT);
                        Secrete secrete = new Secrete(name.toString(), base64pass, desc.toString());
                        db.updateSecrete(secrete,index);
                        Toast.makeText(getApplicationContext(),"Updated Secrete!",Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                });

        final AlertDialog confirmDialog = builderConfirm.create();
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(name))
                    nameHome.setError("Name Required!");
                else if(TextUtils.isEmpty(pass))
                    passHome.setError("Secrete Required");
                else if(TextUtils.isEmpty(desc))
                    descHome.setError("Description Required");
                else if(TextUtils.isEmpty(conf))
                    confPass.setError("Required");
                else {
                    if(!pass.toString().equals(conf.toString())){
                        dialog.show();
                    }
                    else {
                        confirmDialog.show();
                    }
                }
            }
        });

    }
}
