package com.example.version_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @Time: 2023/4/3/0003 on 13:11
 * @User: Jerry
 * @Description: Gradle 是一个框架，作为框架，它负责定义流程和规则。
 * 而具体的编译工作则是通过插件的方式来完成的，我们要引入插件，而达到获取插件配置的目的。
 */
class VersionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("VersionPlugin")
    }
}
