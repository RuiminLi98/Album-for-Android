package com.example.photos12;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import structures.Album;
import structures.Photo;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ImageViewHolder> {
    private static ClickListener clickListener;

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

        Uri myUri = Uri.parse(SearchActivity.tempChooseingPhoto.get(position).uriPath);
        holder.image.setImageURI(myUri);
//sdfsdfs
    }

    @Override
    public int getItemCount() {
        return SearchActivity.tempChooseingPhoto.size();
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
        RecyclerAdapter2.clickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

}
