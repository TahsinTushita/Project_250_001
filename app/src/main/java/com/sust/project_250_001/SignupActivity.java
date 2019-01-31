package com.sust.project_250_001;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText userName,passWord,userAddress,userEmail,personName;
    private Button regBtn;
    private FirebaseAuth firebaseAuth;
    String username,password,address,email,username1,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        userName = findViewById(R.id.signUpUserNameID);
        passWord = findViewById(R.id.signUpPassWordID);
        userAddress = findViewById(R.id.signUpAddressID);
        userEmail = findViewById(R.id.signUpEmailID);
        regBtn = findViewById(R.id.btnReg);
        personName = findViewById(R.id.signUpNameID);


        firebaseAuth = FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = userName.getText().toString().trim();
                username1 = username;
                username = username+"@sust.com";
                password = passWord.getText().toString().trim();
                address =  userAddress.getText().toString().trim();
                email = userEmail.getText().toString().trim();
                name = personName.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(SignupActivity.this,"Enter username",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignupActivity.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<6){
                    Toast.makeText(SignupActivity.this,"Password is too short",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignupActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(address)){
                    Toast.makeText(SignupActivity.this,"Enter address",Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(username,password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(SignupActivity.this,"Sign up failed",Toast.LENGTH_SHORT).show();
                                }

                                else{


                                    DatabaseReference profileDatabase;
                                    profileDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child(username1);
                                    profileDatabase.child("username").setValue(username1);
                                    profileDatabase.child("address").setValue(address);
                                    profileDatabase.child("email").setValue(email);
                                    profileDatabase.child("name").setValue(name);

                                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
