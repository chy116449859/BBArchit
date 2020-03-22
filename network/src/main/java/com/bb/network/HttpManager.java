package com.bb.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.bb.network.cookie.CookieJarImpl;
import com.bb.network.cookie.PersistentCookieStore;
import com.bb.network.interceptor.CacheInterceptor;
import com.bb.network.interceptor.HeaderInterceptor;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: HttpManager
 * Author: CY
 * Date: 2019/11/9 0009 19:37
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class HttpManager {

    private static final String TAG = "HttpManager";
    //超时时间
    private static final int DEFAULT_TIMEOUT = 20;
    //缓存大小
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;

    //volatile关键字保证了：① instance实例对于所有线程都是可见的 ② 禁止了instance操作指令重排序。
    private static volatile HttpManager mInstance = null;

    private Cache mCache = null;
    private File mHttpCacheDirectory;
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;

    public static HttpManager getInstance() {
        //第一次校验，防止不必要的同步。
        if (mInstance == null) {
            //synchronized关键字加锁，保证每次只有一个线程执行对象初始化操作
            synchronized (HttpManager.class) {
                //第二次校验，进行判空，如果为空则执行初始化
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    private HttpManager() {
    }


    public void init(Context context) {
        if (mHttpCacheDirectory == null) {
            mHttpCacheDirectory = new File(context.getCacheDir(), "goldze_cache");
        }

        try {
            if (mCache == null) {
                mCache = new Cache(mHttpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            Logger.e("Could not create http cache", e);
        }
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory();
        mOkHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                .cache(mCache)
//                .addInterceptor(new HeaderInterceptor(headers))
                .addInterceptor(new CacheInterceptor(context))
                .sslSocketFactory(sslParams.mSSLSocketFactory, sslParams.mTrustManager)
//                .addInterceptor(new LoggingInterceptor
//                        .Builder()//构建者模式
//                        .loggable(BuildConfig.DEBUG) //是否开启日志打印
//                        .setLevel(Level.BASIC) //打印的等级
//                        .log(Platform.INFO) // 打印类型
//                        .request("Request") // request的Tag
//                        .response("Response")// Response的Tag
//                        .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
//                        .build()
//                )
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HttpUtils.HTTP_BASE_URL_TEST)
                .build();
    }

    /**
     * 线程调度器
     */
    public static ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static ObservableTransformer exceptionTransformer() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
//                        .map(new HandleFuc())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }

    private static class HandleFuc<T> implements Function<BaseResponse<T>, T> {
        @Override
        public T apply(BaseResponse<T> response) {
            if (!response.isOk())
                throw new RuntimeException(!"".equals(response.getCode() + "" + response.getMessage()) ? response.getMessage() : "");
            return response.getResult();
        }
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return mRetrofit.create(service);
    }

    public void addHeaders(Map<String, String> headers) {
        mOkHttpClient.newBuilder().addInterceptor(new HeaderInterceptor(headers));
    }

    @SuppressLint("CheckResult")
    public <T> void requestSubscribe(Observable observable, final IHttpCallBack<T> callBack) {

        observable
                .compose(schedulersTransformer())
                .compose(exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("开始请求");
                        callBack.onStart();
                    }
                })
                .subscribe(new Consumer<BaseResponse<T>>() {
                    @Override
                    public void accept(BaseResponse<T> tBaseResponse) throws Exception {
                        if (200 == tBaseResponse.getCode()) {
                            callBack.onSuccess(tBaseResponse.getResult());
                        } else {
                            callBack.onFailure(tBaseResponse.getCode() + "", "");
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof ResponseThrowable) {
//                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                            callBack.onError(throwable);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}
