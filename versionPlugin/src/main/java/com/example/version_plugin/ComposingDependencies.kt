package com.example.version_plugin

object DepsVersions {

    //---------------网络相关------------------
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"

    // okhttp3 日志拦截器
    const val loggingInterceptor = "4.8.0"

    // okhttp3 缓存
    const val persistentCookieJar = "v1.0.1"
    const val gson = "2.8.8"

}

object ComposingDependencies {

    //---------------网络相关------------------
    const val retrofit = "com.squareup.retrofit2:retrofit:${DepsVersions.retrofit}"
    const val converterGson =
        "com.squareup.retrofit2:converter-gson:${DepsVersions.converterGson}"

    // okhttp3 日志拦截器
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${DepsVersions.loggingInterceptor}"

    // okhttp3 缓存
    const val persistentCookieJar =
        "com.github.franmontiel:PersistentCookieJar:${DepsVersions.persistentCookieJar}"
    const val gson = "com.google.code.gson:gson:${DepsVersions.gson}"

}