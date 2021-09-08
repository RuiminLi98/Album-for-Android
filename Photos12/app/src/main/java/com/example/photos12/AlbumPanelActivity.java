package com.example.photos12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import structures.Album;

public class AlbumPanelActivity extends AppCompatActivity {
    static AlbumPanelActivity instance;
    EditText AlbumEditText;
    ListView albumList;
    EditText deleteAlbumEditText;
    EditText OldAlbumEditText;
    EditText newAlbumEditText;
    Context context = this;
     ObjectInputStream ois;
     ObjectOutputStream oos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        instance = this;
        AlbumEditText=findViewById(R.id.AlbumEditText);
        albumList=findViewById(R.id.AlbumList);
        deleteAlbumEditText=findViewById((R.id.deleteAlbumEditText));
        OldAlbumEditText=findViewById((R.id.OldAlbumEditText));
        newAlbumEditText=findViewById((R.id.newAlbumEditText));
        MainActivity.getInstance().readAlbums(context.getApplicationContext());

        loadList();
        albumList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position,long id){
                Album item = (Album) adapter.getItemAtPosition(position);
                Album.currentAlbum=item;
                Intent intent = new Intent(context,OpenAlbumActivity.class);
                startActivity(intent);
            }
        });


        }

    protected void onRestart() {
        super.onRestart();
        MainActivity.getInstance().readAlbums(context.getApplicationContext());
        loadList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Album.albums.size();
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
    }

    public void AddbuttonTapped(View view){

        if(!Album.addAlbum(AlbumEditText.getText().toString())){
            //show ERROR
            MainActivity.showError(context,"Illegal Album Name","Please try another name!");
        }
        Album.albums.size();
        AlbumEditText.setText("");
        closeKeyboard(view);
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
        loadList();
    }
    public void deleteTapped(View view){
        String str=deleteAlbumEditText.getText().toString();

        if(!Album.deleteAlbum(str)){
            //show ERROR
            MainActivity.showError(context,"Illegal Album Name","Please try another name!");
        }
        deleteAlbumEditText.setText("");
        closeKeyboard(view);
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
        loadList();
    }

    public void SearchPageTapped(View view){
        Intent intent = new Intent(context,SearchActivity.class);
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
        startActivity(intent);
    }

    public void renameTapped(View view){
        if(!Album.reNameAlbum(OldAlbumEditText.getText().toString(),newAlbumEditText.getText().toString())) {
//            showError("Illegal Album name","Please try again!");
            MainActivity.showError(context,"Illegal Album Name","Please try another name!");
        }
        OldAlbumEditText.setText("");
        newAlbumEditText.setText("");
        closeKeyboard(view);
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
        loadList();
    }


    private void loadList() {
        if(Album.albums == null)return;
        Collections.sort(Album.albums, new Comparator<Album>() {
            @Override
            public int compare(Album o1, Album o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        ArrayAdapter<Album> adapter = new ArrayAdapter<Album>(
                this,
                android.R.layout.simple_list_item_1,
                Album.albums);
        albumList.setAdapter(adapter);
    }
    public void closeKeyboard(View v){
        try {
            InputMethodManager editTextInput = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            editTextInput.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            Log.e("AndroidView", "closeKeyboard: "+e);
        }
    }

}
