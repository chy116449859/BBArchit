package com.bb.base.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bb.base.BaseActivity;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: BaseMvpActivity
 * Author: CY
 * Date: 2019/11/8 0008 13:24
 * Description: 基本的Mvp Activity
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onViewAttched(this, savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPresenter != null) {
            mPresenter.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.release();
    }

    public abstract P createPresenter();
}
