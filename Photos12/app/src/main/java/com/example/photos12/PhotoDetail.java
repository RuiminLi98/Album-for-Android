package com.example.photos12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import structures.Album;
import structures.Photo;

public class PhotoDetail extends AppCompatActivity {
    TextView AlbumName;
    TextView fileName;
    ListView tagList;
    ImageView BigImage;
    EditText TargetAlbumEditText;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        fileName=findViewById(R.id.fileName);
        fileName.setText("fileName:"+Album.currentAlbum.photos.get(Photo.currentPhotoPosition).filename);
        tagList=findViewById(R.id.tagList);
        loadList();
        BigImage=findViewById(R.id.bigImage);
        Uri myUri = Uri.parse(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).uriPath);
        BigImage.setImageURI(myUri);
//        BigImage.setImageBitmap(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).bitmap.getBitmap());
        AlbumName=findViewById((R.id.textView4));
        AlbumName.setText(Album.currentAlbum.name);
        TargetAlbumEditText=findViewById((R.id.TargetAlbumEditText));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Uri myUri = Uri.parse(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).uriPath);
        BigImage.setImageURI(myUri);
        loadList();
    }

    public void loadList()
    {
        ArrayList<String> listTemp=new ArrayList<String>();
        for(Map.Entry<String,ArrayList<String>> temp2: Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.entrySet())
        {
            ArrayList<String> a=temp2.getValue();
            for(int i=0;i<a.size();i++)
            {
                String temp1=temp2.getKey()+":"+a.get(i);
                listTemp.add(temp1);
            }
        }
        Collections.sort(listTemp);
        String[] listContent=new String[listTemp.size()];
        for(int i=0;i<listTemp.size();i++)
        {
            listContent[i]=(String)listTemp.get(i);
        }
        ArrayAdapter adapter =new ArrayAdapter(this, android.R.layout.simple_list_item_1,listContent);
        tagList.setAdapter(adapter);
    }
    public void DeletePhotoTapped(View view){
        Album.currentAlbum.photos.remove(Photo.currentPhotoPosition);
        Photo.currentPhotoPosition=0;
        finish();
    }

    public void slideShowtapped(View view){
        Intent intent = new Intent(context,SlideShowActivity.class);
        startActivity(intent);
    }
    public void MoveTapped(View view){
        String target=TargetAlbumEditText.getText().toString().trim();
        TargetAlbumEditText.setText("");
        if(!Album.albums.contains(new Album(target))){
            // show error
            MainActivity.showError(context,"Illegal Album Name","Please try another name!");
        }else{
            for(Album temp:Album.albums){
                if(temp.equals(new Album(target))){
                    temp.photos.add(new Photo(Album.currentAlbum.photos.get(Photo.currentPhotoPosition)));
                    DeletePhotoTapped(view);
                    break;
                }
            }
            MainActivity.getInstance().writeAlbums(context.getApplicationContext());
        }
    }
    public void CopyTapped(View view){
        String target=TargetAlbumEditText.getText().toString().trim();
        TargetAlbumEditText.setText("");
        if(!Album.albums.contains(new Album(target))){
            // show error
            MainActivity.showError(context,"Illegal Album Name","Please try another name!");
        }else{
            for(Album temp:Album.albums){
                if(temp.equals(new Album(target))){
                    temp.photos.add(new Photo(Album.currentAlbum.photos.get(Photo.currentPhotoPosition)));
                    break;
                }
            }
            MainActivity.getInstance().writeAlbums(context.getApplicationContext());
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        Album.albums.size();
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
    }
    public void TagPageTapped(View view){
        Intent intent = new Intent(this,TagActivity.class);
        startActivity(intent);
    }
}
