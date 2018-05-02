package com.example.jjsampayo.mvvmsample1.presentation.users;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jjsampayo.mvvmsample1.R;
import com.example.jjsampayo.mvvmsample1.presentation.ViewPagerActivity;
import com.example.jjsampayo.mvvmsample1.data.models.User;
import com.example.jjsampayo.mvvmsample1.util.anim.SwipeController;
import com.example.jjsampayo.mvvmsample1.util.network.RequestState;
import com.example.jjsampayo.mvvmsample1.util.network.Status;

public class ListUsersView extends Fragment {

    private static final String TAG = ListUsersView.class.getSimpleName().toUpperCase();

    private ProgressBar progressBar;

    private ListUserAdapter adapter;
    private ListUsersViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textViewError;

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
        return inflater.inflate(R.layout.fragment_list_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.fra_list_users_recycler);
        progressBar        = view.findViewById(R.id.fra_list_users_progress);
        swipeRefreshLayout = view.findViewById(R.id.fra_list_users_swipe);
        textViewError      = view.findViewById(R.id.fra_list_users_tv_error);

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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
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
                    if (requestState != null) {
                        progressBar.setVisibility(View.GONE);
                        textViewError.setVisibility(View.GONE);

                        if (requestState.getStatus() == Status.RUNNING)
                            progressBar.setVisibility(View.VISIBLE);
                        else if (requestState.getStatus() == Status.SUCCESS)
                            progressBar.setVisibility(View.GONE);
                        else {
                            progressBar.setVisibility(View.GONE);
                            textViewError.setText(requestState.getMsg());
                            textViewError.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            viewModel.getRequestState().observe(getActivity(), new Observer<RequestState>() {
                @Override
                public void onChanged(@Nullable RequestState requestState) {
                    if (requestState != null)
                        adapter.setRequestState(requestState);
                }
            });

            viewModel.getListUser().observe(getActivity(), new Observer<PagedList<User>>() {
                @Override
                public void onChanged(@Nullable PagedList<User> users) {
                    adapter.submitList(users);
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
