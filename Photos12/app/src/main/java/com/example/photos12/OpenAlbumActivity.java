package com.example.photos12;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import structures.Album;
import structures.Photo;

public class OpenAlbumActivity extends AppCompatActivity {

    Context context=this;
    TextView AlbumName;
    RecyclerView photosList;
    static OpenAlbumActivity instance;
    private RecyclerView.LayoutManager LayoutManager;
    public static final int REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_open_album);
        AlbumName=findViewById((R.id.AlbumName));
        AlbumName.setText(Album.currentAlbum.name);
        photosList= findViewById(R.id.photosList);
        LayoutManager=new GridLayoutManager(this,3);
        photosList.setHasFixedSize(true);
        photosList.setLayoutManager(LayoutManager);
        refreshPhoto();
    }
    protected void onRestart() {
        super.onRestart();
        refreshPhoto();
    }


    public void AddImage(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        galleryIntent.setType("image/*");
        galleryIntent .addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(galleryIntent, REQUEST_CODE);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE&&resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Album.currentAlbum.photos.add(new Photo(selectedImage));
//                Album.currentAlbum.photos.get(Album.currentAlbum.photos.size()-1).filename=getRealPath(context,selectedImage);
            }catch (Exception e){
                e.printStackTrace();
            }
            MainActivity.getInstance().writeAlbums(context.getApplicationContext());
            refreshPhoto();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
    }


    public void refreshPhoto(){

        RecyclerAdapter adapter=new RecyclerAdapter();

        adapter.setOnItemClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
//                System.out.println(position);
                Photo.currentPhotoPosition=position;
                Intent intent = new Intent(context,PhotoDetail.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {
//                System.out.println(position);
                Photo.currentPhotoPosition=position;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Are you sure to delete this photo?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Album.currentAlbum.photos.remove(Photo.currentPhotoPosition);
                                Photo.currentPhotoPosition=0;
                                MainActivity.getInstance().writeAlbums(context.getApplicationContext());
                                refreshPhoto();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        photosList.setAdapter(adapter);
    }

}
