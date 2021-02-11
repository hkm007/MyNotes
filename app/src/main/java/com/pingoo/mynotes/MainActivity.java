package com.pingoo.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NotesAdapter mNotesAdaptor;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        final TextView textView = findViewById(R.id.no_item_text);

        final ArrayList<NotesItem> mNotesList = new ArrayList<>();
        mDatabase.child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNotesList.clear();

                //iterating through all the nodes
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NotesItem note = postSnapshot.getValue(NotesItem.class);
                    mNotesList.add(note);
                }

                if(mNotesList.size() > 0) {
                    textView.setText("");
                    mNotesAdaptor = new NotesAdapter(MainActivity.this, mNotesList);
                    mRecyclerView.setAdapter(mNotesAdaptor);
                } else {
                    textView.setText("You don't have any text!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addNote(View v) {
        Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
        startActivity(intent);
    }
}
