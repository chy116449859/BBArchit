package com.bb.cy.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;

import com.bb.base.mvvm.BaseModel;
import com.bb.base.mvvm.BaseViewModel;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: DetailViewModel
 * Author: CY
 * Date: 2019/11/10 0010 17:19
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class DetailViewModel extends BaseViewModel {

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public DetailViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

}
