package com.example.photos12;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import structures.Album;
import structures.Photo;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {
    private static ClickListener clickListener;
    static boolean getPer=false;
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout,parent,false);
//        view.setOnClickListener(mOnClickListener);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
//        Bitmap bitmap=Album.currentAlbum.photos.get(position).bitmap.getBitmap();
//        holder.image.setImageBitmap(bitmap);

        Uri myUri = Uri.parse(Album.currentAlbum.photos.get(position).uriPath);
        try{
            holder.image.setImageURI(myUri);
        }catch (Exception e){
            Album.albums.clear();
            MainActivity.getInstance().writeAlbums();
            AlbumPanelActivity act= AlbumPanelActivity.instance;
            Intent intent = new Intent(act,AlbumPanelActivity.class);
            act.startActivity(intent);


//            if(!getPer){
//                AlbumPanelActivity act= AlbumPanelActivity.instance;
//                MainActivity.showError(act,"Ask Permission","Please select any image to get the permission and restart from Home Screen");
//
//                Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                // Start the Intent
//                galleryIntent.setType("image/*");
//                galleryIntent .addCategory(Intent.CATEGORY_OPENABLE);
//                act.startActivityForResult(galleryIntent, 2);
//                getPer = true;
//
//            }


        }

    }


    @Override
    public int getItemCount() {
        return Album.currentAlbum.photos.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView image;
        public ImageViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            image=itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerAdapter.clickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

}
