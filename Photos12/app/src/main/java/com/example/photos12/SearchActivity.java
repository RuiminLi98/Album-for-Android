package com.example.photos12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import structures.Album;
import structures.Photo;

public class SearchActivity extends AppCompatActivity {


    Context context=this;
    public static final int REQUEST_CODE = 2;
    private RecyclerView.LayoutManager LayoutManager;
    boolean flag=false;


    RadioButton person;
    RadioButton location;
    RadioButton personOrLocation;
    RadioButton personAndLocation;
    RadioGroup RadioGroup2;
    RecyclerView searchedPhoto;
    EditText tagValue;
    EditText tagValue2;
    String currentTag=null;
    Button Search;

    public static ArrayList<Photo> thisSearchResult=new ArrayList<Photo>();
    public static ArrayList<Photo> tempChooseingPhoto=new ArrayList<Photo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Search=findViewById(R.id.Search);
        person=findViewById(R.id.person);
        personAndLocation=findViewById(R.id.personAndLocation);
        personOrLocation=findViewById(R.id.personOrLocation);
        location=findViewById(R.id.location);
        RadioGroup2=findViewById(R.id.RadioGroup2);
        searchedPhoto=findViewById(R.id.searchedPhoto);
        tagValue=findViewById(R.id.tagValue);
        tagValue2=findViewById(R.id.tagValue2);
        RadioGroup2.setOnCheckedChangeListener(new radioGroupListener());
        person.setChecked(true);
        LayoutManager=new GridLayoutManager(this,3);
        searchedPhoto.setHasFixedSize(true);
        searchedPhoto.setLayoutManager(LayoutManager);



    }

    class radioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int id = group.getCheckedRadioButtonId();
            switch (group.getCheckedRadioButtonId()) {
                case R.id.person:
                    currentTag = "person";
                    break;
                case R.id.location:
                    currentTag = "location";
                    break;
                case R.id.personAndLocation:
                    currentTag="personAndLocation";
                    break;
                case R.id.personOrLocation:
                    currentTag="personOrLocation";
                    break;
                default:
                    currentTag = "";
            }
        }
    }
    public void alartTable(String str){
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(context);
        builder1.setTitle("Bad Input");
        builder1.setMessage(str);
        builder1.setCancelable(true);
        builder1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { dialog.cancel(); }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public void searchTapped(View view){
        thisSearchResult.clear();
        tempChooseingPhoto.clear();
        if(currentTag.equals("person")) {
            if(tagValue.getText().toString().equals("")) {
                alartTable("person value cannot be empty");
                tagValue2.setText("");
                return;
            }
            for (int i = 0; i < Album.albums.size(); i++) {
                for (int j = 0; j < Album.albums.get(i).photos.size(); j++) {
                    if (Album.albums.get(i).photos.get(j).tags.containsKey(currentTag)) {
                        ArrayList<String> temp = Album.albums.get(i).photos.get(j).tags.get(currentTag);
                        for (String tempStr : temp) {
                            if (tempStr.startsWith((tagValue.getText().toString()))) {
                                thisSearchResult.add(Album.albums.get(i).photos.get(j));
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(currentTag.equals("location"))
        {
            if(tagValue2.getText().toString().equals("")) {
                tagValue.setText("");
                alartTable("location value cannot be empty");
                return;
            }
            for (int i = 0; i < Album.albums.size(); i++) {
                for (int j = 0; j < Album.albums.get(i).photos.size(); j++) {
                    if (Album.albums.get(i).photos.get(j).tags.containsKey(currentTag)) {
                        ArrayList<String> temp = Album.albums.get(i).photos.get(j).tags.get(currentTag);
                        for (String tempStr : temp) {
                            if (tempStr.startsWith((tagValue2.getText().toString()))) {
                                thisSearchResult.add(Album.albums.get(i).photos.get(j));
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(currentTag.equals("personAndLocation")) {
            if(tagValue.getText().toString().equals("") || tagValue2.getText().toString().equals("")) {
                alartTable("person and location value cannot be empty");
                tagValue2.setText("");
                tagValue.setText("");
                return;
            }
            for (int i = 0; i < Album.albums.size(); i++) {
                for (int j = 0; j < Album.albums.get(i).photos.size(); j++) {
                    flag = false;
                    if (Album.albums.get(i).photos.get(j).tags.containsKey("person")) {
                        ArrayList<String> temp = Album.albums.get(i).photos.get(j).tags.get("person");
                        for (String tempStr : temp) {
                            if (tempStr.startsWith((tagValue.getText().toString())) && !flag) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    if (Album.albums.get(i).photos.get(j).tags.containsKey("location")) {
                        ArrayList<String> temp = Album.albums.get(i).photos.get(j).tags.get("location");
                        for (String tempStr : temp) {
                            if (tempStr.startsWith((tagValue2.getText().toString())) && flag) {
                                thisSearchResult.add(Album.albums.get(i).photos.get(j));
                                break;
                            }
                        }
                    }
                }
            }
            flag=false;
        }
        if(currentTag.equals("personOrLocation"))
        {
            if(tagValue.getText().toString().equals("") || tagValue2.getText().toString().equals("")) {
                alartTable("person and location value cannot be empty");
                tagValue2.setText("");
                tagValue.setText("");
                return;
            }
            for (int i = 0; i < Album.albums.size(); i++) {
                for (int j = 0; j < Album.albums.get(i).photos.size(); j++) {
                    flag=false;
                    if (Album.albums.get(i).photos.get(j).tags.containsKey("person")) {
                        ArrayList<String> temp = Album.albums.get(i).photos.get(j).tags.get("person");
                        for (String tempStr : temp) {
                            if (tempStr.startsWith(tagValue.getText().toString())) {
                                thisSearchResult.add(Album.albums.get(i).photos.get(j));
                                flag=true;
                                break;
                            }
                        }
                    }
                    if (Album.albums.get(i).photos.get(j).tags.containsKey("location")) {
                        ArrayList<String> temp = Album.albums.get(i).photos.get(j).tags.get("location");
                        for (String tempStr : temp) {
                            if (tempStr.startsWith(tagValue2.getText().toString()) && !flag) {
                                thisSearchResult.add(Album.albums.get(i).photos.get(j));
                                break;
                            }
                        }
                    }
                }
            }
            flag=false;
        }
        tempChooseingPhoto.addAll(thisSearchResult);
        thisSearchResult.clear();
        tagValue.setText("");
        tagValue2.setText("");

        refreshPhoto();
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE&&resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                tempChooseingPhoto.add(new Photo(selectedImage));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        refreshPhoto();
    }

    public void refreshPhoto(){
        RecyclerAdapter2 adapter=new RecyclerAdapter2();
        adapter.setOnItemClickListener(new RecyclerAdapter2.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
////                System.out.println(position);
//                Photo.currentPhotoPosition=position;
//                Intent intent = new Intent(context,PhotoDetail.class);
//                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
        searchedPhoto.setAdapter(adapter);
    }
}