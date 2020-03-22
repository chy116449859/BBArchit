package com.bb.base.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bb.base.BaseFragment;


public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onViewAttched(this, savedInstanceState);
        }
    }

    public abstract P createPresenter();
}
