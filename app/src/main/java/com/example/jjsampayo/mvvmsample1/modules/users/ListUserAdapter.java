package com.example.jjsampayo.mvvmsample1.modules.users;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsampayo.mvvmsample1.databinding.ItemListUsersBinding;
import com.example.jjsampayo.mvvmsample1.repositories.models.User;

public class ListUserAdapter extends PagedListAdapter<User, ListUserAdapter.ListUserViewHolder> {
    private ListUsersViewModel viewModel;

    public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<User>() {
                @Override
                public boolean areItemsTheSame(@NonNull User oldUser, @NonNull User newUser) {
                    return oldUser.getId() == newUser.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull User oldUser, @NonNull User newUser) {
                    return oldUser.getId() == newUser.getId() && oldUser.getName().equals(newUser.getName());
                }
            };

    ListUserAdapter(ListUsersViewModel viewModel) {
        super(DIFF_CALLBACK);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ListUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListUsersBinding binding = ItemListUsersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ListUserViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ListUserViewHolder holder, int position) {
        holder.binding.setUser(getItem(position));
    }

    class ListUserViewHolder extends RecyclerView.ViewHolder {
        ItemListUsersBinding binding;

        ListUserViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //viewModel.onClickItemEvent(getItem(getAdapterPosition()).getId(), getItem(getAdapterPosition()).getName());
                }
            });
        }
    }

}
