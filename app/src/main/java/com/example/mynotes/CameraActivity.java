package com.example.mynotes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private List<String> photoPaths = new ArrayList<>();
    private GridView galleryGridView;
    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        galleryGridView = findViewById(R.id.galleryGridView);
        Button btnTakePhoto = findViewById(R.id.btnTakePhoto);
        Button btnPickFromGallery = findViewById(R.id.btnPickFromGallery);

        adapter = new PhotoAdapter(this, photoPaths);
        galleryGridView.setAdapter(adapter);

        btnTakePhoto.setOnClickListener(v -> takePhoto());
        btnPickFromGallery.setOnClickListener(v -> pickFromGallery());
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
            String imagePath = null;

            if (requestCode == REQUEST_CAMERA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imagePath = saveImageToGallery(photo);
            } else if (requestCode == REQUEST_GALLERY) {
                Uri uri = data.getData();
                if (uri != null) {
                    imagePath = uri.toString();
                }
            }

            if (imagePath != null) {
                photoPaths.add(imagePath);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Photo ajoutée à la galerie", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String saveImageToGallery(Bitmap bitmap) {
        if (bitmap == null) return null;
        String fileName = "photo_" + System.currentTimeMillis() + ".jpg";
        return MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, fileName, "Photo prise via MyNotes");
    }
}