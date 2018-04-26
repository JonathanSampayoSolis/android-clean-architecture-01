package com.example.jjsampayo.mvvmsample1.gui.coments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jjsampayo.mvvmsample1.R;
import com.example.jjsampayo.mvvmsample1.data.models.Comment;

import java.util.ArrayList;
import java.util.List;

public class PostDetailView extends AppCompatActivity {

    private int userID = 0;
    private String title = "";
    private String body = "";

    private ProgressBar progressBar;

    private PostDetailAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post_detail);

        if (getIntent().getExtras() != null) {
            userID = getIntent().getExtras().getInt("POST_ID");
            title = getIntent().getExtras().getString("POST_TITLE");
            body = getIntent().getExtras().getString("POST_BODY");
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }


        TextView textViewTitle = findViewById(R.id.fra_post_detail_title);
        TextView textViewBody = findViewById(R.id.fra_post_detail_body);
        RecyclerView recyclerView = findViewById(R.id.fra_posts_detail_recycler);
        progressBar = findViewById(R.id.fra_posts_detail_progress);

        progressBar.setVisibility(View.VISIBLE);

        textViewTitle.setText(title);
        textViewBody.setText(body);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new PostDetailAdapter(new ArrayList<Comment>());

        recyclerView.setAdapter(adapter);

        PostDetailViewModel viewModel = ViewModelProviders.of(this).get(PostDetailViewModel.class);

        viewModel.getCommentsOf(userID).observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> comments) {
                adapter.loadData(comments);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
