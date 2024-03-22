package com.liang.module_route.route

/**
 * - Time: 2023/8/14/0014 on 15:57
 * - User: Jerry
 * - Description: 路由路径
 */
class AppRouter {

    companion object{
        // 在支持路由的页面上添加注解(必选)
        // 这里的路径需要注意的是至少需要有两级，/xx/xx
        // 图片选择器模块
        const val PICTURE_SELECTOR = "/picture/pictureSelector"

        // 在支持路由的界面上添加注解(必选)
        // 这里的路径需要注意的是至少需要两级，/xx/xx
        // 天气模块
        const val WEATHER_APP = "/weather/weatherActivity"
    }

}