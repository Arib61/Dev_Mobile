package com.example.mynotes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class DetailsNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);

        TextView txtNom = findViewById(R.id.txtNom);
        TextView txtDate = findViewById(R.id.txtDate);
        TextView txtPriorite = findViewById(R.id.txtPriorite);
        TextView txtDescription = findViewById(R.id.txtDescription);
        ImageView photoView = findViewById(R.id.photoView);
        Button btnRetour = findViewById(R.id.btnRetour);

        String nom = getIntent().getStringExtra("nom");
        String desc = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");
        String prio = getIntent().getStringExtra("priorite");
        String photoPath = getIntent().getStringExtra("photoPath"); // Nouveau

        txtNom.setText(nom);
        txtDate.setText(date);
        txtPriorite.setText("Priorité : " + prio);
        txtDescription.setText(desc);

        // Afficher la photo si elle existe
        if (photoPath != null && !photoPath.isEmpty()) {
            photoView.setVisibility(View.VISIBLE);
            try {
                if (photoPath.startsWith("content://")) {
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(photoPath));
                    photoView.setImageBitmap(bm);
                } else {
                    Bitmap bm = BitmapFactory.decodeFile(photoPath);
                    photoView.setImageBitmap(bm);
                }
            } catch (IOException e) {
                e.printStackTrace();
                photoView.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        } else {
            photoView.setVisibility(View.GONE);
        }

        btnRetour.setOnClickListener(v -> finish());
    }
}