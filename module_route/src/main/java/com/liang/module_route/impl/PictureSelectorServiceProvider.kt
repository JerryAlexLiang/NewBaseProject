package com.liang.module_route.impl

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.liang.module_route.route.AppRouter
import com.liang.module_route.service.PictureSelectorService

/**
 * - Time: 2023/8/14/0014 on 16:09
 * - User: Jerry
 * - Description: 描述:通过依赖注入解耦:服务管理
 * - 路由服务管理中心
 */

class PictureSelectorServiceProvider {
    companion object {
        fun startPictureSelectorActivity(context: Context) {
//            val pictureSelectorService: PictureSelectorService =
//                ARouter.getInstance().build(AppRouter.PICTURE_SELECTOR)
//                    .navigation() as PictureSelectorService
//            pictureSelectorService.startPictureSelectorActivity(context)

            ARouter.getInstance().navigation(PictureSelectorService::class.java)
                .startPictureSelectorActivity(context)

        }
    }
}
