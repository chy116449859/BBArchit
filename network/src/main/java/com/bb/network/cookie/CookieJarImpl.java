package com.bb.network.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: CookieJarImpl
 * Author: CY
 * Date: 2019/11/9 0009 19:05
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class CookieJarImpl implements CookieJar {

    private ICookieStore cookieStore;

    public CookieJarImpl(ICookieStore cookieStore) {
        if (cookieStore == null) {
            throw new IllegalArgumentException("cookieStore can not be null!");
        }
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.saveCookie(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.loadCookie(url);
    }

    public synchronized ICookieStore getCookieStore() {
        return cookieStore;
    }
}
