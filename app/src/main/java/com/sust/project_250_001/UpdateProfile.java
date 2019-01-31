package com.sust.project_250_001;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class UpdateProfile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String name,pwd,email,address,username;
    private DatabaseReference database;
    private EditText fieldName,fieldPwd,fieldAdress,fieldEmail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Profile").child(LoginActivity.user);

        fieldName = findViewById(R.id.idName);
        fieldEmail = findViewById(R.id.idEmail);
        fieldPwd = findViewById(R.id.idPwd);
        fieldAdress = findViewById(R.id.idAdress);


    }

    public void update(View view) {


        username = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        email = fieldEmail.getText().toString();
        pwd = fieldPwd.getText().toString();
        name = fieldName.getText().toString();
        address = fieldAdress.getText().toString();
        boolean err = false;
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> queries = new ArrayList<>();

        queries.add("Name ");
        if(!name.isEmpty()) {
            database.child("name").setValue(name);
            result.add("was changed!");
        } else {
            result.add("was not changed.");
        }
        queries.add("Email ");
        if(!email.isEmpty()) {
            database.child("email").setValue(email);
            result.add("was changed!");
        } else {
            result.add("was not changed.");
        }
        queries.add("Password ");
        if(!pwd.isEmpty()) {
            if (pwd.length() > 5){
            firebaseAuth.getCurrentUser().updatePassword(pwd);
            result.add("was changed!");}
             else if(pwd.length() < 5){
            err=true;
            Toast.makeText(this,"Unable to update password",Toast.LENGTH_LONG).show();
        } }
        else {
            result.add("was not changed.");
        }

        queries.add("Address ");
        if(!address.isEmpty()) {
            database.child("address").setValue(address);
        result.add("was changed!");
        } else {
            result.add("was not changed.");
        }

        String st="";
        for(int i=0;i<queries.size();++i) {
            st = st.concat (queries.get(i)+result.get(i)+"\n");
        }

        Snackbar.make(view.getRootView(),st,Snackbar.LENGTH_LONG).show();
        if(!err){
//            onBackPressed();
        }
    }
}
