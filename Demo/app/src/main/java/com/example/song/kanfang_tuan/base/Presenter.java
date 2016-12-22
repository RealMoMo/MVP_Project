package com.example.song.kanfang_tuan.base;

public interface Presenter<V> {
    void attachView(V mvpView);
    void detachView();
}
