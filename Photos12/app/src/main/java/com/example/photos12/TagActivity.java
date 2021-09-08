package com.example.photos12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import structures.Album;
import structures.Photo;

public class TagActivity extends AppCompatActivity {
    EditText tagValue;
    ListView TagList;
    RadioButton Person;
    RadioButton Location;
    RadioGroup radioGroup;
    String currentTagType=null;
    String currentTagValue=null;
    Button AddTag;
    Button DeleteTag;
    private void loadList() {
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
        TagList.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        TagList=findViewById(R.id.TagList);
        AddTag=findViewById(R.id.AddTag);
        DeleteTag=findViewById(R.id.DeleteTag);
        tagValue=findViewById((R.id.tagValue));
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        Person=(RadioButton)findViewById(R.id.Person);
        Location=(RadioButton)findViewById(R.id.Location);
        radioGroup.setOnCheckedChangeListener(new radioGroupListener());
        Person.setChecked(true);
        loadList();
    }
        Context context = this;
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

    class radioGroupListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int id=group.getCheckedRadioButtonId();
            switch(group.getCheckedRadioButtonId()){
                case R.id.Person:
                    currentTagType="person";
                    break;
                case R.id.Location:
                    currentTagType="location";
                    break;
                default:
                    currentTagType="";
            }

        }
    }

    /*
        private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
        public void showdialogType(View view)
        {
            AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
            alertdialogbuilder.setMessage("Please choose the tag type");
            alertdialogbuilder.setPositiveButton("Back",click1);
            AlertDialog alertdialog=alertdialogbuilder.create();
            alertdialog.show();
        }
        public void showdialogValue(View view)
        {
            AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
            alertdialogbuilder.setMessage("Please input the tag value");
            alertdialogbuilder.setPositiveButton("Back",click1);
            AlertDialog alertdialog=alertdialogbuilder.create();
            alertdialog.show();
        }

     */
    public void deleteTapped(View view){
        radioGroup.setOnCheckedChangeListener(new radioGroupListener());
        currentTagValue=tagValue.getText().toString();
        if(currentTagType.isEmpty()){
            return;
        }
        if(currentTagValue.isEmpty()){
            alartTable("TagValue cannot be empty");
            return;
        }
        boolean flag=Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.containsKey(currentTagType);
        if(flag)
        {
            ArrayList<String> temp=new ArrayList<String>();
            temp=Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.get(currentTagType);
            flag=false;
            flag=temp.contains(currentTagValue);
            if(flag)
            {
                temp.remove(currentTagValue);
                String tempName=currentTagType;
                Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.remove(currentTagType);
                Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.put(tempName,temp);
            }
            else
                alartTable("This tag do not exist");
        }
        loadList();
        tagValue.setText("");
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
    }
    protected void onStop() {
        super.onStop();
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
    }
    public void addTapped(View view){
        radioGroup.setOnCheckedChangeListener(new radioGroupListener());
        currentTagValue=tagValue.getText().toString();
        if(currentTagType.isEmpty()){
            return;
        }
        if(currentTagValue.isEmpty()){
            alartTable("TagValue cannot be empty");
            return;
        }
        ArrayList<String> temp = new ArrayList<String>();
        if(!(Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.containsKey(currentTagType)))
            temp.add(currentTagValue);
        else {
            temp = Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.get(currentTagType);
        }
        if (!(temp.contains(currentTagValue))) {
            temp.add(currentTagValue);
            Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.remove(currentTagType);
            Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.put(currentTagType, temp);
        } else {
            Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.remove(currentTagType);
            Album.currentAlbum.photos.get(Photo.currentPhotoPosition).tags.put(currentTagType, temp);

        }
        //TagList.removeAllViews();
        loadList();
        MainActivity.getInstance().writeAlbums(context.getApplicationContext());
        tagValue.setText("");

//        System.out.println(currentTagType);
//        System.out.println(currentTagValue);
    }
}
