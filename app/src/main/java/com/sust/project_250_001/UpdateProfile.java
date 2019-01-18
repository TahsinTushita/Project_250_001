package com.sust.project_250_001;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateProfile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String name,pwd,email,address,username;
    private DatabaseReference database;
    private EditText fieldName,fieldPwd,fieldAdress,fieldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

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
        if(!email.isEmpty()) {
            database.child("email").setValue(email);
        }
        if(pwd.length() > 5) {
            if (!pwd.isEmpty())
            firebaseAuth.getCurrentUser().updatePassword(pwd);
        } else {
            Toast.makeText(this,"Unable to update password",Toast.LENGTH_LONG).show();
            err = true;
        }

        if(!address.isEmpty()) {
            database.child("address").setValue(address);
        }
        if(!name.isEmpty()) {
            database.child("name").setValue(name);
        }
        if (!err)
        onBackPressed();

    }
}
