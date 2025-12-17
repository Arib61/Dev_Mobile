package com.example.mynotes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;

    private EditText editNom, editDescription, editDate;
    private Spinner spinnerPriorite;
    private Button btnTakePhoto, btnPickFromGallery, btnSave;
    private ImageView photoPreview;

    private String currentPhotoPath = null; // Stockage temporaire du chemin de la photo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Initialisation des vues
        editNom = findViewById(R.id.editNom);
        editDescription = findViewById(R.id.editDescription);
        editDate = findViewById(R.id.editDate);
        spinnerPriorite = findViewById(R.id.spinnerPriorite);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnPickFromGallery = findViewById(R.id.btnPickFromGallery);
        btnSave = findViewById(R.id.btnSave);
        photoPreview = findViewById(R.id.photoPreview);

        // Remplir le Spinner
        String[] priorites = {"Basse", "Moyenne", "Haute"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priorites);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriorite.setAdapter(adapter);

        // Boutons photo
        btnTakePhoto.setOnClickListener(v -> takePhoto());
        btnPickFromGallery.setOnClickListener(v -> pickFromGallery());

        // Enregistrer la note
        btnSave.setOnClickListener(v -> saveNote());
    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                currentPhotoPath = saveImageToGallery(photo);
                displayPhoto(currentPhotoPath);
            } else if (requestCode == REQUEST_GALLERY) {
                Uri uri = data.getData();
                if (uri != null) {
                    currentPhotoPath = uri.toString();
                    displayPhoto(currentPhotoPath);
                }
            }
        }
    }

    private String saveImageToGallery(Bitmap bitmap) {
        if (bitmap == null) return null;
        String fileName = "note_photo_" + System.currentTimeMillis() + ".jpg";
        return MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, fileName, "Photo de note");
    }

    private void displayPhoto(String path) {
        if (path != null) {
            photoPreview.setVisibility(View.VISIBLE);
            try {
                if (path.startsWith("content://")) {
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(path));
                    photoPreview.setImageBitmap(bm);
                } else {
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    photoPreview.setImageBitmap(bm);
                }
            } catch (Exception e) {
                e.printStackTrace();
                photoPreview.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }
    }

    private void saveNote() {
        String nom = editNom.getText().toString().trim();
        String desc = editDescription.getText().toString().trim();
        String date = editDate.getText().toString().trim();
        String prio = spinnerPriorite.getSelectedItem().toString();

        if (nom.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir le titre et la date.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer la note avec la photo
        Note note = new Note(nom, desc, date, prio, currentPhotoPath);

        // Ajouter à la liste statique
        NoteListActivity.notes.add(note);

        Toast.makeText(this, "Note ajoutée !", Toast.LENGTH_SHORT).show();
        finish(); // Retour à la liste
    }
}