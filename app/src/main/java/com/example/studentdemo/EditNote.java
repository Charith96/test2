package com.example.studentdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditNote extends AppCompatActivity {

    EditText etName,etDec;
    Button upBtn;
    String userId,name,dec;
    private  MyAdapter myAdapter;
    SwipeRefreshLayout swiperefreshlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Date currentTime = Calendar.getInstance().getTime();
        String formatDate = DateFormat.getDateInstance().format(currentTime);

        etName = findViewById(R.id.updateName);
        etDec =  findViewById(R.id.updateDsc);
        upBtn = findViewById(R.id.editBtn2);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        dec = intent.getStringExtra("note");

        etName.setText(name);
        etDec.setText(dec);


        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Note2").child(userId);
                String uName,uDec;
                uName = etName.getText().toString();
                uDec = etDec.getText().toString();
                Note note = new Note(userId,uName,uDec, formatDate);
                databaseReference.setValue(note);
                Toast.makeText(EditNote.this,"Updated",Toast.LENGTH_SHORT).show();

                swiperefreshlayout = findViewById(R.id.swiperefreshlayout);
                swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        databaseReference.setValue(note);
                        myAdapter.notifyDataSetChanged();
                        swiperefreshlayout.setRefreshing(false);
                    }
                });
            }
        });
    }
}