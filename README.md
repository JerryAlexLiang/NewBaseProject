# 【Android Gradle 7.0及以上 使用maven-publish发布组件

## AS其他项目依赖Gitee/GitHub仓库 用git或gitee搭建私人Maven 如何依赖

### 1、在项目Project下的budid.gradle/setting.gradle中依赖你的云端仓库

#### 获取Gitee/GitHub仓库的原始文件URL。
#### Gitee为https://gitee.com/<用户名>/<仓库名-maven_repo>/raw/<分支-master>
#### Github为https://raw.githubusercontent.com/<用户名>/<仓库名-maven_repo>/<分支-master>
#### 配置项目build.gradle，增加maven仓库地址

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



