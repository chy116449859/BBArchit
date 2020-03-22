package com.bb.base.mvvm;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: IModel
 * Author: CY
 * Date: 2019/11/9 0009 09:33
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface IModel {
    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    void onCleared();
}
