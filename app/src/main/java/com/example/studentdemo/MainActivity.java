package com.example.studentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText etName, etNote, etDate;
    Button btnAddData, btnShow;
    Note note;

    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etNote = findViewById(R.id.etNote);


        btnAddData = findViewById(R.id.btnAddData);
        btnShow = findViewById(R.id.btnShow);

        database = FirebaseDatabase.getInstance().getReference().child("Note2");


        Date currentTime = Calendar.getInstance().getTime();
        String formatDate = DateFormat.getDateInstance().format(currentTime);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id ,name, note;

                id = database.push().getKey();
                name = etName.getText().toString();
                note = etNote.getText().toString();
                //date = note.setCreateDate(formatDate);

                if(TextUtils.isEmpty(etName.getText().toString())){
                    Toast.makeText(MainActivity.this, "Name field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(etNote.getText().toString())){
                    Toast.makeText(MainActivity.this, "Description field is empty!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Note notes = new Note(id,name, note, formatDate);
                    database.child(id).setValue(notes);

                    etName.setText("");
                    etNote.setText("");

                    Toast.makeText(getApplicationContext(), "Note Inserted !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ShowActivity.class);
                startActivity(intent);
            }
        });
    }
}