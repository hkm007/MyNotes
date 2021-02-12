package com.pingoo.mynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private Context mContext;
    private ArrayList<NotesItem> mNotesList;
    private DatabaseReference mDatabase;

    public NotesAdapter(Context mContext, ArrayList<NotesItem> mNotesList) {
        this.mContext = mContext;
        this.mNotesList = mNotesList;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.notes_item, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NotesItem currentItem = mNotesList.get(position);

        String title = currentItem.getTitle();
        String content = currentItem.getContent();
        final String ID = currentItem.getId();

        holder.mTitle.setText(title);
        holder.mContent.setText(content);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("notes").child(ID).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mContent;
        public ImageView mImageView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.text_view_title);
            mContent = itemView.findViewById(R.id.text_view_content);
            mImageView = itemView.findViewById(R.id.imageView);
        }
    }
}
