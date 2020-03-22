package com.bb.base.mvvm;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: IBaseView
 * Author: CY
 * Date: 2019/11/9 0009 16:51
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
