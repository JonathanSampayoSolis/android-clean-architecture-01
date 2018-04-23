package com.example.jjsampayo.mvvmsample1.modules.coments;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsampayo.mvvmsample1.databinding.ItemPostDetailBinding;
import com.example.jjsampayo.mvvmsample1.repositories.models.Comment;

import java.util.List;

public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.PostDetailViewHolder> {
    private List<Comment> data;

    PostDetailAdapter(List<Comment> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PostDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostDetailBinding binding = ItemPostDetailBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        return new PostDetailViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull PostDetailViewHolder holder, int position) {
        holder.binding.setComment(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void loadData(List<Comment> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    static class PostDetailViewHolder extends RecyclerView.ViewHolder {
        ItemPostDetailBinding binding;

        PostDetailViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
