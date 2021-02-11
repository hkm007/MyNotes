package com.pingoo.mynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private Context mContext;
    private ArrayList<NotesItem> mNotesList;

    public NotesAdapter(Context mContext, ArrayList<NotesItem> mNotesList) {
        this.mContext = mContext;
        this.mNotesList = mNotesList;
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

        holder.mTitle.setText(title);
        holder.mContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mContent;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.text_view_title);
            mContent = itemView.findViewById(R.id.text_view_content);
        }
    }
}
