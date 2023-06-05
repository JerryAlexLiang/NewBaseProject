plugins {
    `kotlin-dsl`
}

// 插件注册
// 插件需要注册才能被别的 Module 引入，在插件 Module 的build.gradle.kts中，注册一个id
gradlePlugin {
    plugins.register("versionPlugin") {
        id = "version-plugin"
        implementationClass = "com.example.version_plugin.VersionPlugin"
    }
}