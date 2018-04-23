package com.example.jjsampayo.mvvmsample1.util.reactive;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public class ReactiveEvent<T> extends MutableLiveData<T> {
    private static final String TAG = ReactiveEvent.class.getSimpleName();

    private final AtomicBoolean isPending = new AtomicBoolean(false);

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer observer) {

        if (this.hasActiveObservers())
            Log.w(TAG, "Multiple observers registered but only one will be dispatched!");

        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                if (isPending.compareAndSet(true, false))
                    observer.onChanged(t);
            }
        });
    }

    @MainThread
    public void setValue(T value) {
        isPending.set(true);
        super.setValue(value);
    }

    @MainThread
    public void call() {
        setValue(null);
    }

}
