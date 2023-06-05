package com.example.version_plugin

//----------- Jetpack----------------
object JetpackVersions {
    // 协程库，因为Flow是构建在Kotlin协程基础之上的，因此协程依赖库必不可少
    const val coroutinesCore = "1.6.1"
    const val coroutinesAndroid = "1.6.1"

    // ViewModelScope
    const val viewModelKtx = "2.4.1"

    // LifecycleScope提供协程作用域的，同样必不可少
    const val lifecycleScope = "2.5.1"

    // liveData
    const val liveDataKtx = "2.4.1"

    // ktx的扩展库，这些倒不是必须的，但是能帮忙我们简化不少代码的书写
    const val activityKtx = "1.4.0"
    const val fragmentKtx = "1.5.3"

    // UnPeek-LiveData解决数据倒灌
    const val unPeekLiveData = "7.8.0"
}

object ComposingJetpack {

    //----------- Jetpack ----------------
    // 协程库，因为Flow是构建在Kotlin协程基础之上的，因此协程依赖库必不可少
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${JetpackVersions.coroutinesCore}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${JetpackVersions.coroutinesAndroid}"

    // ViewModelScope
    const val viewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${JetpackVersions.viewModelKtx}"

    // LifecycleScope提供协程作用域的，同样必不可少
    const val lifecycleScope =
        "androidx.lifecycle:lifecycle-runtime-ktx:${JetpackVersions.lifecycleScope}"

    // liveData
    const val liveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${JetpackVersions.liveDataKtx}"

    // ktx的扩展库，这些倒不是必须的，但是能帮忙我们简化不少代码的书写
    const val activityKtx = "androidx.activity:activity-ktx:${JetpackVersions.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${JetpackVersions.fragmentKtx}"

    // UnPeek-LiveData解决数据倒灌
    const val unPeekLiveData =
        "com.kunminx.arch:unpeek-livedata:${JetpackVersions.unPeekLiveData}"
}