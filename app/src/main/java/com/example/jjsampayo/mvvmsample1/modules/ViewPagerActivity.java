package com.example.jjsampayo.mvvmsample1.modules;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsampayo.mvvmsample1.R;
import com.example.jjsampayo.mvvmsample1.modules.album.ListAlbumView;
import com.example.jjsampayo.mvvmsample1.modules.posts.ListPostsView;

public class ViewPagerActivity extends AppCompatActivity {

    private int userID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detail);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.fra_detail_viewpager);
        TabLayout tabLayout = findViewById(R.id.fra_detail_tablayout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        String name = "";
        if (getIntent().getExtras() != null) {
            userID = getIntent().getExtras().getInt("USER_ID");
            name = getIntent().getExtras().getString("USER_NAME");
        }

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(name);

        viewPagerAdapter.addFragment(ListPostsView.newInstance(userID), getString(R.string.posts));
        viewPagerAdapter.addFragment(ListAlbumView.newInstance(userID), getString(R.string.album));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
