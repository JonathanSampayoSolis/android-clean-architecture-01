package com.example.jjsampayo.mvvmsample1.modules.users;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jjsampayo.mvvmsample1.R;
import com.example.jjsampayo.mvvmsample1.modules.ViewPagerActivity;
import com.example.jjsampayo.mvvmsample1.repositories.models.User;
import com.example.jjsampayo.mvvmsample1.util.anim.SwipeController;
import com.example.jjsampayo.mvvmsample1.util.network.RequestState;

public class ListUsersView extends Fragment {

    private static final String TAG = ListUsersView.class.getSimpleName().toUpperCase();

    private ProgressBar progressBar;

    private ListUserAdapter adapter;
    private ListUsersViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ListUsersView newInstance() {

        Bundle args = new Bundle();

        ListUsersView fragment = new ListUsersView();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable final Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_list_users, container, false);

        final RecyclerView recyclerView = mainView.findViewById(R.id.fra_list_users_recycler);
        progressBar = mainView.findViewById(R.id.fra_list_users_progress);
        swipeRefreshLayout = mainView.findViewById(R.id.fra_list_users_swipe);

        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SwipeController swipeController = new SwipeController();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        viewModel = ViewModelProviders.of(this).get(ListUsersViewModel.class);

        adapter = new ListUserAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: Reload data from WS
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            viewModel.getListUser().observe(getActivity(), new Observer<PagedList<User>>() {
                @Override
                public void onChanged(@Nullable PagedList<User> users) {
                    adapter.submitList(users);
                    progressBar.setVisibility(View.GONE);
                }
            });

            viewModel.getOnClickItemEvent().observe(getActivity(), new Observer() {
                @Override
                public void onChanged(@Nullable Object o) {
                    assert o != null;
                    launchPostFragment((String) o);
                }
            });

            viewModel.getInitialState().observe(getActivity(), new Observer<RequestState>() {
                @Override
                public void onChanged(@Nullable RequestState requestState) {
                    Log.d(TAG, "InitialState changed to: " + requestState.getStatus());
                }
            });

            viewModel.getRequestState().observe(getActivity(), new Observer<RequestState>() {
                @Override
                public void onChanged(@Nullable RequestState requestState) {
                    Log.d(TAG, "RequestState changed to: " + requestState.getStatus());
                }
            });

        }
    }

    private void launchPostFragment(String userID) {
        if (getActivity() != null)
            getActivity().startActivity(new Intent(getActivity(), ViewPagerActivity.class)
                    .putExtra("USER_ID", Integer.parseInt(userID.split(":")[0]))
                    .putExtra("USER_NAME", userID.split(":")[1]));
    }
}
