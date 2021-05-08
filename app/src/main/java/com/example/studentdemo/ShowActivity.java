package com.example.studentdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    SwipeRefreshLayout swiperefreshlayout;
    private  MyAdapter myAdapter;
    DatabaseReference dbRef;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notes = new  ArrayList<Note>();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Note2");
        dbRef.addListenerForSingleValueEvent(valueEventListener);

        swiperefreshlayout = findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myAdapter.notifyDataSetChanged();
                swiperefreshlayout.setRefreshing(false);
            }
        });
    }



    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot snapshot1: snapshot.getChildren()){
                Note uData = snapshot1.getValue(Note.class);
                notes.add(uData);
            }
            myAdapter= new MyAdapter(ShowActivity.this,notes);
            recyclerView.setAdapter(myAdapter);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}