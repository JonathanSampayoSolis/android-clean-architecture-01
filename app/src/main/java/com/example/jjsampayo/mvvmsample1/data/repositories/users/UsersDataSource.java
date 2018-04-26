package com.example.jjsampayo.mvvmsample1.data.repo_users;

import android.arch.paging.DataSource;

public class UsersDataSource extends DataSource.Factory {

    private UsersRepository usersRepository;

    public UsersDataSource() {
        usersRepository = new UsersRepository();
    }

    @Override
    public DataSource create() {
        return usersRepository;
    }

    public UsersRepository getRepository() {
        return usersRepository;
    }
}
