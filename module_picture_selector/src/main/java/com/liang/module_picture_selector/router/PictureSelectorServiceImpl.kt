package com.liang.module_picture_selector.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.liang.module_picture_selector.TestActivity
import com.liang.module_route.route.AppRouter
import com.liang.module_route.service.PictureSelectorService

/**
 * - Time: 2023/8/14/0014 on 16:22
 * - User: Jerry
 * - Description: 通过依赖注入解耦:服务管理 实现接口
 */
@Route(path = AppRouter.PICTURE_SELECTOR)
class PictureSelectorServiceImpl : PictureSelectorService{
    override fun startPictureSelectorActivity(context: Context) {
        TestActivity.actionStart(context)
    }

    override fun init(context: Context?) {

    }
}