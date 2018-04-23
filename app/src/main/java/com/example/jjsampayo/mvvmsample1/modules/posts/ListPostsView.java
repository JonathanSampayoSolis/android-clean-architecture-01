package com.example.jjsampayo.mvvmsample1.modules.posts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jjsampayo.mvvmsample1.R;
import com.example.jjsampayo.mvvmsample1.modules.coments.PostDetailView;
import com.example.jjsampayo.mvvmsample1.repositories.models.Post;

import java.util.ArrayList;
import java.util.List;

public class ListPostsView extends Fragment {

    private ProgressBar progressBar;

    private ListPostsAdapter adapter;

    private ListPostsViewModel viewModel;

    private int userID;

    public static ListPostsView newInstance(int user) {

        Bundle args = new Bundle();

        args.putInt("USER_ID", user);

        ListPostsView fragment = new ListPostsView();
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
        View mainView = inflater.inflate(R.layout.fragment_page_posts, container, false);

        RecyclerView recyclerView = mainView.findViewById(R.id.fra_posts_recycler);
        progressBar = mainView.findViewById(R.id.fra_posts_progress);
        final SwipeRefreshLayout swipeRefreshLayout = mainView.findViewById(R.id.fra_posts_swipe);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        progressBar.setVisibility(View.VISIBLE);

        viewModel = ViewModelProviders.of(this).get(ListPostsViewModel.class);

        adapter = new ListPostsAdapter(new ArrayList<Post>(), viewModel);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onActivityCreated(savedInstanceState);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            viewModel.getPosts(userID).observe(getActivity(), new Observer<List<Post>>() {
                @Override
                public void onChanged(@Nullable List<Post> posts) {
                    adapter.loadData(posts);
                    progressBar.setVisibility(View.GONE);
                }
            });

            viewModel.getOnClickItemEvent().observe(getActivity(), new Observer() {
                @Override
                public void onChanged(@Nullable Object o) {
                    assert o != null;
                    launchCommentsFragment((Object[]) o);
                }
            });
        }
    }

    private void launchCommentsFragment(Object[] data) {
        View view = (View) data[0];

        int id = (int) data[1];

        String title = (String) data[2];

        String body = (String) data[3];

        if (getActivity() != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getActivity().startActivity(
                        new Intent(getActivity(), PostDetailView.class)
                            .putExtra("SHARED_CARDVIEW", R.id.item_posts_cardview)
                            .putExtra("POST_ID", id)
                            .putExtra("POST_TITLE", title)
                            .putExtra("POST_BODY", body)
                        , ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()
                            , view.findViewById(R.id.item_posts_cardview)
                            , "sharedElement").toBundle());
            } else {
                getActivity().startActivity(new Intent(getActivity(), PostDetailView.class));
            }
    }

}
