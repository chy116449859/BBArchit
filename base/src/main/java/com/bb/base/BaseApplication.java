package com.bb.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: BaseApplication
 * Author: CY
 * Date: 2019/11/8 0008 22:34
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class BaseApplication extends Application {

    private static Application sInstance;

    private static Activity mTopActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
    }

    public static synchronized void setApplication(@NonNull Application application) {
        sInstance = application;

        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityManager.getActivityManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mTopActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityManager.getActivityManager().removeActivity(activity);
            }
        });
    }

    public Activity getTopActivity() {
        return mTopActivity;
    }

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }
}
