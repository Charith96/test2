package com.example.studentdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ShowActivity showActivity;
    private ArrayList<Note> notes;
    MyAdapter myAdapter;
    SwipeRefreshLayout swiperefreshlayout;


    public MyAdapter(Context context,ArrayList<Note> notes){
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Note notes = this.notes.get(position);
        myViewHolder.name.setText(notes.getName());
        myViewHolder.note.setText(notes.getNote());

        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Note2").child(notes.getId());
                databaseReference.removeValue();


                swiperefreshlayout = swiperefreshlayout.findViewById(R.id.swiperefreshlayout);
                swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        databaseReference.removeValue();
                        Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show();
                        myAdapter.notifyDataSetChanged();
                        swiperefreshlayout.setRefreshing(false);
                    }
                });
            }

            private void finish() {
            }
        });

        myViewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,EditNote.class);
                i.putExtra("id",notes.getId());
                i.putExtra("name",notes.getName());
                i.putExtra("note",notes.getNote());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, note;
        ImageButton btnEdit,btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            note = itemView.findViewById(R.id.note_text);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
