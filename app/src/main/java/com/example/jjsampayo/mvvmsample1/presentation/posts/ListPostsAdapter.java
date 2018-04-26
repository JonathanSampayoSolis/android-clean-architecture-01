package com.example.jjsampayo.mvvmsample1.presentation.posts;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsampayo.mvvmsample1.databinding.ItemListPostsBinding;
import com.example.jjsampayo.mvvmsample1.data.models.Post;

import java.util.List;

public class ListPostsAdapter extends RecyclerView.Adapter<ListPostsAdapter.ViewHolderImpl> {

    private List<Post> data;
    private ListPostsViewModel viewModel;

    public ListPostsAdapter(List<Post> data, ListPostsViewModel viewModel) {
        this.data = data;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListPostsBinding binding = ItemListPostsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderImpl(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderImpl holder, int position) {
        holder.binding.setPost(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void loadData(List<Post> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    class ViewHolderImpl extends RecyclerView.ViewHolder {
        ItemListPostsBinding binding;

        ViewHolderImpl(final View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = data.get(getAdapterPosition());
                    viewModel.onClickItemEvent(new Object[]{itemView, post.getId(), post.getTitle(), post.getBody()});
                }
            });
        }
    }
}
