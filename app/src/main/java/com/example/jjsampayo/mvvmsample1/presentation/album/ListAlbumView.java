package com.example.jjsampayo.mvvmsample1.gui.album;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.jjsampayo.mvvmsample1.R;
import com.example.jjsampayo.mvvmsample1.data.models.Album;

import java.util.ArrayList;
import java.util.List;

public class ListAlbumView extends Fragment {

    private ProgressBar progressBar;

    private ListAlbumViewModel viewModel;
    private ListAlbumAdapter adapter;

    private int userID;

    public static ListAlbumView newInstance(int user) {

        Bundle args = new Bundle();

        args.putInt("USER_ID", user);

        ListAlbumView fragment = new ListAlbumView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            userID = getArguments().getInt("USER_ID");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_page_album, container, false);

        RecyclerView recyclerView = mainView.findViewById(R.id.fra_album_recycler);
        progressBar = mainView.findViewById(R.id.fra_album_progress);
        final SwipeRefreshLayout swipeRefreshLayout = mainView.findViewById(R.id.fra_album_swap);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressBar.setVisibility(View.VISIBLE);

        viewModel = ViewModelProviders.of(this).get(ListAlbumViewModel.class);

        adapter = new ListAlbumAdapter(new ArrayList<Album>());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            viewModel.getAlbums(userID).observe(getActivity(), new Observer<List<Album>>() {
                @Override
                public void onChanged(@Nullable List<Album> albums) {
                    adapter.loadData(albums);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

}
