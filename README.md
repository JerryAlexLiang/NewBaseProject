# 【Android Gradle 7.0及以上 使用maven-publish发布组件

## 用git或gitee搭建私人Maven 如何依赖

### 1、在app.budid中依赖你的云端仓库

```
buildscript {  
 repositories {  
 	maven{url 'https://gitee.com/haochen12/HowzitsMaven/raw/master'}  
 }

```

> 它是怎么来的: git/gitee仓库地址/仓库名称/raw/分支

### 2、在需要依赖maven_repo的module的build.gradle 中依赖

```
   implementation 'com.howzits.autopermit:autopermit:0.0.1-SNAPSHOT'
```

> 由groupId+artifactId+version组成。
> 【com.howzits.autopermit】:【autopermit】:【0.0.1-SNAPSHOT】



