package com.bb.cy;

import android.content.Context;

import com.bb.base.BaseApplication;
import com.bb.network.HttpManager;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: BBApplication
 * Author: CY
 * Date: 2019/11/9 0009 09:01
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class BBApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        xcrash.XCrash.init(this);//
        HttpManager.getInstance().init(this);
    }
}
