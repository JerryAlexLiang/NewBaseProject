package com.example.version_plugin

//----------- Android-------------
object AndroidXVersions {
    const val coreKtx = "1.7.0"
    const val appcompat = "1.4.1"
    const val material = "1.6.0"
    const val constraintLayout = "2.1.4"
    const val junit = "4.13.2"
    const val junitExt = "1.1.3"
    const val espresso = "3.4.0"
}

object ComposingAndroidX {
    //----------- Android -------------
    const val coreKtx = "androidx.core:core-ktx:${AndroidXVersions.coreKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${AndroidXVersions.appcompat}"
    const val material = "com.google.android.material:material:${AndroidXVersions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${AndroidXVersions.constraintLayout}"
    const val junit = "junit:junit:${AndroidXVersions.junit}"
    const val junitExt = "androidx.test.ext:junit:${AndroidXVersions.junitExt}"
    const val espresso = "androidx.test.espresso:espresso-core:${AndroidXVersions.espresso}"
}
