package com.bb.network;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: ExceptionHandle
 * Author: CY
 * Date: 2019/11/9 0009 18:47
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class ResponseThrowable extends Exception {
    public int code;
    public String message;

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
