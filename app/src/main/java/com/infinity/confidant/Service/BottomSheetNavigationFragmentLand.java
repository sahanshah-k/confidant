package com.infinity.confidant.Service;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.confidant.Activities.Land;
import com.infinity.confidant.Activities.SecreteHome;
import com.infinity.confidant.Activities.SetupPin;
import com.infinity.confidant.Domain.Details;
import com.infinity.confidant.MainActivity;
import com.infinity.confidant.R;

public class BottomSheetNavigationFragmentLand extends BottomSheetDialogFragment {

    TextView user_name, user_email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_land,container,false);
        NavigationView navigationView = view.findViewById(R.id.navigation_view_land);

        user_name = view.findViewById(R.id.user_name);
        user_email = view.findViewById(R.id.user_email);

        final DatabaseHandler db = new DatabaseHandler(view.getContext());

        Details details = db.getDetails();

        user_name.setText(details.getName());
        user_email.setText(details.getMail());

        final Intent i = new Intent(view.getContext(), SetupPin.class);
        AlertDialog.Builder builderConfirm = new AlertDialog.Builder(view.getContext(), R.style.Theme_MaterialComponents_Dialog_Alert);
        builderConfirm.setTitle("Change PIN")
                .setMessage("Press \"Yes\" to continue!")
                .setCancelable(false)
                .setNeutralButton("No",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        Details details = new Details("set",5);
                        i.putExtra("details", details);
                        startActivity(i);
                    }
                });

        final AlertDialog confirmDialog = builderConfirm.create();

        navigationView.setNavigationItemSelectedListener((menuItem) -> {
            switch (menuItem.getItemId()){
                case R.id.changePin:
                //Toast.makeText(getContext(),"testdelete",Toast.LENGTH_LONG).show();
                    builderConfirm.show();
            }

            return true;
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}