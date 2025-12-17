package com.example.mynotes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class NoteListAdapter extends BaseAdapter {
    private Context context;
    private List<Note> notes;

    public NoteListAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        Note note = notes.get(position);
        text1.setText(note.getNom());
        text2.setText(note.getDate());

        // Couleur selon priorité
        String prio = note.getPriorite();
        if ("Haute".equals(prio)) {
            text1.setTextColor(Color.RED);
        } else if ("Moyenne".equals(prio)) {
            text1.setTextColor(Color.BLUE);
        } else {
            text1.setTextColor(Color.GRAY);
        }

        // Optionnel : ajouter une icône de photo
        // (pas obligatoire pour le TP)

        return convertView;
    }
}