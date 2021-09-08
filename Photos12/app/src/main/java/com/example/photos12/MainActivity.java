package com.example.photos12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.gson.Gson;


import io.realm.Realm;
import structures.Album;
import structures.Photo;

public class MainActivity extends AppCompatActivity {
    public static boolean ex=false;
    Context context;

    ListView albumList;
    static final long serialVersionUID = 1L;
    public static MainActivity ins;
    SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ins = this;
//        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        setContentView(R.layout.activity_main);
        albumList=findViewById(R.id.AlbumPreviewList);
        mPrefs = getPreferences(MODE_PRIVATE);
        context=this.getApplicationContext();
        MainActivity.getInstance().readAlbums(context);
        loadList();

//
//        if(ex){
//            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//            builder1.setTitle("Exit");
//            builder1.setMessage("Are you sure to exit?");
//            builder1.setCancelable(true);
//
//            builder1.setPositiveButton(
//                    "Exit",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            writeAlbums(context.getApplicationContext());
//                            finish();
//                            moveTaskToBack(true);
//                            System.exit(0);
////                            android.os.Process.killProcess(android.os.Process.myPid());
//                            dialog.cancel();
//                        }
//                    });
//
//            builder1.setNegativeButton(
//                    "Cancel",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            writeAlbums(context.getApplicationContext());
//                            ex=false;
//                            dialog.cancel();
//                        }
//                    });
//
//            AlertDialog alert11 = builder1.create();
//            alert11.show();
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.runFinalization();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Restart");
        MainActivity.getInstance().readAlbums(context.getApplicationContext());
        loadList();
    }

    public void writeAlbums(Context context) {


        if(Album.albums==null||Album.albums.size()==0){
            return;
        }
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear();
        for(int i=0;i<Album.albums.size();i++){
            Gson gson = new Gson();
            String json = gson.toJson(Album.albums.get(i));
            prefsEditor.putString(i+"", json);
        }

        prefsEditor.commit();



////        Context context = this;
//        File path = context.getFilesDir();
//        File file = new File(path, "Album.dat");
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( file));
//            oos.writeObject(Album.albums);
//            oos.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
    public void writeAlbums() {


        if(Album.albums==null||Album.albums.size()==0){
            return;
        }
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear();
        for(int i=0;i<Album.albums.size();i++){
            Gson gson = new Gson();
            String json = gson.toJson(Album.albums.get(i));
            prefsEditor.putString(i+"", json);
        }

        prefsEditor.commit();

    }

    public void readAlbums(Context context) {

        int i=0;
        while(true){
            Gson gson = new Gson();
            String json = mPrefs.getString(i+"", "");
            if(json.isEmpty())break;
            if(Album.albums != null&&!json.isEmpty()&&!Album.albums.contains(gson.fromJson(json, Album.class))){
                Album.albums.add(gson.fromJson(json, Album.class));
            }
            i++;
        }



//        File path = context.getFilesDir();
//        File file = new File(path, "Album.dat");
//        try {
////            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( file));
////            oos.writeObject(Album.albums);
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
//            Album.albums = (ArrayList<Album>) ois.readObject();
//            ois.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
    public static MainActivity getInstance() {
        return ins;
    }
    public void AlbumTapped(View view){
        Intent intent = new Intent(context,AlbumPanelActivity.class);
        startActivity(intent);
    }
    public void SearchPageTapped(View view){
        Intent intent = new Intent(context,SearchActivity.class);
        startActivity(intent);
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

    public static void showError(Context context,String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setTitle(title);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    }
