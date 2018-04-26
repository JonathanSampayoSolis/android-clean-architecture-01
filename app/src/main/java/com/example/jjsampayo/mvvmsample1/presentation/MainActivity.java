package com.example.jjsampayo.mvvmsample1.gui;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.jjsampayo.mvvmsample1.R;
import com.example.jjsampayo.mvvmsample1.gui.users.ListUsersView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.act_main_frame_main, ListUsersView.newInstance())
                    .commit();
        }
    }
}
