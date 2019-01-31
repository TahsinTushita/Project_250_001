package com.sust.project_250_001;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;


public class UpdateProfile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String name,pwd,email,address,username;
    private DatabaseReference database;
    private EditText fieldName,fieldPwd,fieldAdress,fieldEmail;
    private Toolbar toolbar;
    private ImageButton photoBtn;
    private static final int GALLERY_REQUEST = 1;

    private ProgressDialog progressDialog;
    private StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Profile").child(LoginActivity.user);

        fieldName = findViewById(R.id.idName);
        fieldEmail = findViewById(R.id.idEmail);
        fieldPwd = findViewById(R.id.idPwd);
        fieldAdress = findViewById(R.id.idAdress);
        photoBtn = findViewById(R.id.profilePhoto);

        progressDialog = new ProgressDialog(this);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

    }

    private Uri imageUri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            photoBtn.setImageURI(imageUri);
        }
    }


    public void update(View view) {

        progressDialog.setMessage("Updating!!");
        progressDialog.show();
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

        if(imageUri!=null) {
            final StorageReference filepath = mStorageReference.child("Profile_Pictures").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Uri downloadUri = task.getResult();
                        database.child("profilephoto").setValue(downloadUri.toString());
                    }
                }
            });
        }else {
            if (progressDialog.isShowing()) progressDialog.dismiss();
        }
        if(!err){
//            onBackPressed();
        }
    }
}