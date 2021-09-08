package com.example.photos12;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import structures.Album;
import structures.Photo;


public class SlideShowActivity extends AppCompatActivity {
    ImageView imageView2;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        imageView2=findViewById(R.id.imageView2);
//        imageView2.setImageBitmap(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).bitmap.getBitmap());
        Uri myUri = Uri.parse(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).uriPath);
        imageView2.setImageURI(myUri);
    }
    public void lastTapped(View view){
        if(Photo.currentPhotoPosition==0){
            return;
        }
        Photo.currentPhotoPosition--;
//        imageView2.setImageBitmap(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).bitmap.getBitmap());
        Uri myUri = Uri.parse(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).uriPath);
        imageView2.setImageURI(myUri);
    }
    public void nextTapped(View view){
        if(Photo.currentPhotoPosition+1== Album.currentAlbum.photos.size()){
            return;
        }
        Photo.currentPhotoPosition++;
//        imageView2.setImageBitmap(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).bitmap.getBitmap());
        Uri myUri = Uri.parse(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).uriPath);
        imageView2.setImageURI(myUri);
    }
}
