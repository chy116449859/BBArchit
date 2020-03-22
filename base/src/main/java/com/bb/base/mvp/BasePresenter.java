package com.bb.base.mvp;

import android.os.Bundle;

public class BasePresenter<V extends BaseView> {
    protected V view;

    public BasePresenter() {
    }

    public V getView() {
        return view;
    }

    protected void onViewAttched(V view, Bundle saveInstanceState) {
        this.view = view;
    }

    public void onViewDetached() {
        view = null;
    }

    public void release() {

    }

    public void onSaveInstanceState(Bundle outState) {

    }
}
