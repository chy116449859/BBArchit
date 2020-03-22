package com.bb.network;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: BaseResponse
 * Author: CY
 * Date: 2019/11/9 0009 18:44
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class BaseResponse<T> {
    private int code;
    private String message;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isOk() {
        return code == 0;
    }

    public String getMessage() {
        return message;
    }
}
