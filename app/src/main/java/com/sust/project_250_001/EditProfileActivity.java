package com.sust.project_250_001;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private Button editBtn,cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editBtn = findViewById(R.id.btnEdit);
        cancelBtn = findViewById(R.id.btnCancel);

        editBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnEdit){

        }

        if(v.getId()==R.id.btnCancel){

        }
    }
}
