package com.bb.network;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: HttpCallBack
 * Author: CY
 * Date: 2019/11/10 0010 11:56
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface IHttpCallBack<T> {
    void onStart();

    void onSuccess(T t);

    void onFailure(String errorCode, String errorString);

    void onError(Throwable throwable);
}
