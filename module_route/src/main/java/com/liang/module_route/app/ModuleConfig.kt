package com.liang.module_route.app

interface ModuleConfig {
    companion object {
        // const val MODULE_PICTURE_SELECTOR = "module_picture_selector"
        const val MODULE_WEATHER = "com.liang.module_weather"

        val MODULE_LIST = arrayOf(
            MODULE_WEATHER,
        )
    }
}