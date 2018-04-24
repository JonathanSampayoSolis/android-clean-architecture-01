package com.example.jjsampayo.mvvmsample1.repositories;

import android.arch.paging.DataSource;

public class UsersDataSource extends DataSource.Factory {

    @Override
    public DataSource create() {
        return new UsersRepository();
    }
}
