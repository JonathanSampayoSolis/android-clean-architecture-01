package com.example.jjsampayo.mvvmsample1.presentation.users;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.jjsampayo.mvvmsample1.databinding.ItemListUsersBinding;
import com.example.jjsampayo.mvvmsample1.data.models.User;

import com.example.jjsampayo.mvvmsample1.R;
import com.example.jjsampayo.mvvmsample1.util.network.RequestState;
import com.example.jjsampayo.mvvmsample1.util.network.Status;

public class ListUserAdapter extends PagedListAdapter<User, RecyclerView.ViewHolder> {
    private static final String TAG = ListUserAdapter.class.getSimpleName().toUpperCase();

    private ListUsersViewModel viewModel;

    private RequestState requestState;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemListUsersBinding binding = ItemListUsersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        RecyclerView.ViewHolder viewHolder;

        if (viewType == R.layout.item_list_users) {
            viewHolder = new ListUserViewHolder(binding.getRoot());
        } else if (viewType == R.layout.item_state_network) {
            View view = layoutInflater.inflate(R.layout.item_state_network, parent, false);
            viewHolder = new StateRequestViewHolder(view);
        } else
            throw new IllegalArgumentException("View bind unsupported!");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_list_users:
                ((ListUserViewHolder) holder).binding.setUser(getItem(position));
                break;
            case R.layout.item_state_network:
                ((StateRequestViewHolder) holder).bind(requestState);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return  (isLoading() && position == getItemCount() - 1) ?
                    R.layout.item_state_network : R.layout.item_list_users;
    }

    private boolean isLoading() {
    return (requestState != null && requestState.getStatus() != Status.SUCCESS);
    }

    public void setRequestState(RequestState requestState) {
        RequestState lastState = this.requestState;
        boolean wasLoading = isLoading();
        this.requestState = requestState;
        boolean isLoading = isLoading();
        if (wasLoading != isLoading) {
            if (wasLoading) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (isLoading && lastState != requestState)
            notifyItemChanged(getItemCount() - 1);
    }

    static class ListUserViewHolder extends RecyclerView.ViewHolder {
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

    static class StateRequestViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;
        private Button button;

        StateRequestViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.item_state_network_progressbar);
            button = itemView.findViewById(R.id.item_state_network_retry);
        }

        public void bind(RequestState requestState) {
            progressBar.setVisibility(View.GONE);
            button.setVisibility(View.GONE);

            switch (requestState.getStatus()) {
                case RUNNING:
                    Log.d(TAG, "is Running");
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    Log.d(TAG, "is Success");
                    progressBar.setVisibility(View.GONE);
                    break;
                case FAILED:
                    Log.d(TAG, "is Failed");
                    progressBar.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    break;
                default:
                    progressBar.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    break;
            }
        }
    }

}
