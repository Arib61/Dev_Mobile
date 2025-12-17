package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    public static List<Note> notes = new ArrayList<>();
    private NoteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        ListView listView = findViewById(R.id.noteListView);
        Button addBtn = findViewById(R.id.addNoteButton);
        Button galleryBtn = findViewById(R.id.btnGallery);

        adapter = new NoteListAdapter(this, notes);
        listView.setAdapter(adapter);

        // Clic sur une note → détail
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Note note = notes.get(position);
            Intent intent = new Intent(NoteListActivity.this, DetailsNoteActivity.class);
            intent.putExtra("nom", note.getNom());
            intent.putExtra("description", note.getDescription());
            intent.putExtra("date", note.getDate());
            intent.putExtra("priorite", note.getPriorite());
            startActivity(intent);
        });

        // Ajouter une note
        addBtn.setOnClickListener(v ->
                startActivity(new Intent(NoteListActivity.this, AddNoteActivity.class))
        );

        // Accéder à la caméra/galerie
        galleryBtn.setOnClickListener(v ->
                startActivity(new Intent(NoteListActivity.this, CameraActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}