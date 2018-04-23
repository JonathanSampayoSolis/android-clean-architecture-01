package com.example.jjsampayo.mvvmsample1.modules.album;


import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsampayo.mvvmsample1.databinding.ItemGridAlbumBinding;
import com.example.jjsampayo.mvvmsample1.repositories.models.Album;

import java.util.List;

public class ListAlbumAdapter extends RecyclerView.Adapter<ListAlbumAdapter.LisAlbumViewHolder> {
    private List<Album> data;

    public ListAlbumAdapter(List<Album> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LisAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGridAlbumBinding binding = ItemGridAlbumBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        return new LisAlbumViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull LisAlbumViewHolder holder, int position) {
        holder.binding.setAlbum(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void loadData(List<Album> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public static class LisAlbumViewHolder extends RecyclerView.ViewHolder {
        ItemGridAlbumBinding binding;

        public LisAlbumViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
