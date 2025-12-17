package com.example.mynotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.provider.MediaStore;
import java.io.IOException;
import java.util.List;

public class PhotoAdapter extends BaseAdapter {
    private Context context;
    private List<String> photoPaths;

    public PhotoAdapter(Context context, List<String> photoPaths) {
        this.context = context;
        this.photoPaths = photoPaths;
    }

    @Override
    public int getCount() {
        return photoPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return photoPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        String path = photoPaths.get(position);

        try {
            if (path != null) {
                if (path.startsWith("content://")) {
                    // URI from gallery
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(path));
                    imageView.setImageBitmap(bitmap);
                } else {
                    // File path (from camera)
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageResource(android.R.drawable.ic_menu_gallery);
                    }
                }
            } else {
                imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        } catch (IOException | OutOfMemoryError e) {
            e.printStackTrace();
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        return convertView;
    }
}