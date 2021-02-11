package com.pingoo.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewNoteActivity extends AppCompatActivity {
    private Button mSubmit;
    private EditText mTitle, mContent;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSubmit = findViewById(R.id.note_submit_button);
        mTitle = findViewById(R.id.note_title);
        mContent = findViewById(R.id.note_content);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                String content = mContent.getText().toString();

                mDatabase.child("notes").child(title).setValue(new NotesItem(title, content));

                Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
