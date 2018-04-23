package com.example.jjsampayo.mvvmsample1.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {
}
