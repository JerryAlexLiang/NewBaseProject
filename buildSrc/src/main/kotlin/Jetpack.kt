//----------- Jetpack----------------
object JetpackVersions {
    // 协程库，因为Flow是构建在Kotlin协程基础之上的，因此协程依赖库必不可少
    const val coroutinesCore = "1.6.1"
    const val coroutinesAndroid = "1.6.1"

    // ViewModelScope
    const val viewModelKtx = "2.4.1"

    // LifecycleScope提供协程作用域的，同样必不可少
    const val lifecycleScope = "2.5.1"

    // lifeCycle.repeatOnLifecycle()
    const val lifecycleRuntimeCompose = "2.6.1"

    // liveData
    const val liveDataKtx = "2.4.1"

    // ktx的扩展库，这些倒不是必须的，但是能帮忙我们简化不少代码的书写
    const val activityKtx = "1.4.0"
    const val fragmentKtx = "1.5.3"

    // UnPeek-LiveData解决数据倒灌
    const val unPeekLiveData = "7.8.0"

    const val koinVersion = "3.4.0"

    /**
     * Room
     */
    const val roomVersion = "2.5.0"

}

object Jetpack {

    //----------- Jetpack ----------------
    // 协程库，因为Flow是构建在Kotlin协程基础之上的，因此协程依赖库必不可少
    // 用于在 Kotlin 中使用协程的主接口
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${JetpackVersions.coroutinesCore}"

    // 在协程中支持 Android 主线程
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${JetpackVersions.coroutinesAndroid}"

    // ViewModelScope
    const val viewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${JetpackVersions.viewModelKtx}"

    // LifecycleScope提供协程作用域的，同样必不可少
    const val lifecycleScope =
        "androidx.lifecycle:lifecycle-runtime-ktx:${JetpackVersions.lifecycleScope}"

    // lifeCycle.repeatOnLifecycle()
    const val lifecycleRuntimeCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:${JetpackVersions.lifecycleRuntimeCompose}"

    // liveData
    const val liveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${JetpackVersions.liveDataKtx}"

    // ktx的扩展库，这些倒不是必须的，但是能帮忙我们简化不少代码的书写
    const val activityKtx = "androidx.activity:activity-ktx:${JetpackVersions.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${JetpackVersions.fragmentKtx}"

    // UnPeek-LiveData解决数据倒灌
    const val unPeekLiveData =
        "com.kunminx.arch:unpeek-livedata:${JetpackVersions.unPeekLiveData}"

    //---------------Koin依赖注入------------------
    // Koin for Kotlin
    const val koinCore = "io.insert-koin:koin-core:${JetpackVersions.koinVersion}"
    const val koinAndroid = "io.insert-koin:koin-android:${JetpackVersions.koinVersion}"

//    const val koinCore = "org.koin:koin-core:${JetpackVersions.koinVersion}"
//    const val koinCoreExt = "org.koin:koin-core-ext:${JetpackVersions.koinVersion}"
//
//    // Koin for AndroidX
//    const val koinAndroidxScope = "org.koin:koin-androidx-scope:${JetpackVersions.koinVersion}"
//    const val koinAndroidxViewModel =
//        "org.koin:koin-androidx-viewmodel:${JetpackVersions.koinVersion}"
//    const val koinAndroidxFragment =
//        "org.koin:koin-androidx-fragment:${JetpackVersions.koinVersion}"
//    const val koinAndroidxExt = "org.koin:koin-androidx-ext:${JetpackVersions.koinVersion}"

    /**
     * Room数据库
     * - apply plugin: 'kotlin-kapt'
     * - implementation "androidx.room:room-runtime:$room_version"
     * - annotationProcessor "androidx.room:room-compiler:$room_version"
     * - kapt "androidx.room:room-compiler:$room_version"  // Kotlin 使用 kapt
     * - ksp "androidx.room:room-compiler:$room_version"   // To use Kotlin Symbol Processing (KSP)
     * - implementation "androidx.room:room-ktx:2.2.5" //Coroutines support for Room 协程操作库
     */
    const val roomRuntime = "androidx.room:room-runtime:${JetpackVersions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${JetpackVersions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${JetpackVersions.roomVersion}"
}