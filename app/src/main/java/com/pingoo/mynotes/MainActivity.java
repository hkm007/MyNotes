package com.pingoo.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import static com.pingoo.mynotes.RegistrationActivity.NAME;
import static com.pingoo.mynotes.RegistrationActivity.SHARED_PREFS;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NotesAdapter mNotesAdaptor;
    private DatabaseReference mDatabase;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
    }

    public void addNote(View v) {

        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.new_note, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setView(promptsView);

        final EditText mTitle = promptsView.findViewById(R.id.note_title);
        final EditText mContent = promptsView.findViewById(R.id.note_content);

        alertDialogBuilder
        .setCancelable(false)
        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                String title = mTitle.getText().toString();
                String content = mContent.getText().toString();
                String ID = UUID.randomUUID().toString();

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                String author = sharedPreferences.getString(NAME, "");

                mDatabase.child("notes").child(ID).setValue(new NotesItem(ID, title, content, author));
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void loadData() {
        final TextView textView = findViewById(R.id.no_item_text);
        final ArrayList<NotesItem> mNotesList = new ArrayList<>();

        mDatabase.child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNotesList.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NotesItem note = postSnapshot.getValue(NotesItem.class);

                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    String author = sharedPreferences.getString(NAME, "");

                    if(note.getAuthor().compareTo(author) == 0) mNotesList.add(note);
                }

                mProgressBar.setVisibility(View.GONE);
                if(mNotesList.size() > 0) {
                    textView.setText("");
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mNotesAdaptor = new NotesAdapter(MainActivity.this, mNotesList);
                    mRecyclerView.setAdapter(mNotesAdaptor);
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    textView.setText("You don't have any note!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("err", "Something went wrong!");
                Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT);
                loadData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
}
