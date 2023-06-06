package com.liang.newbaseproject.livedata

import com.google.gson.annotations.SerializedName

data class WanArticleBean(
    @SerializedName(value = "adminAdd")
    var hasAdminAdd: Boolean?,
    var apkLink: String?,
    var audit: Int?,
    var author: String?,
    var canEdit: Boolean?,
    var chapterId: Int?,
    var chapterName: String?,
    var collect: Boolean?,
    var courseId: Int?,
    var desc: String?,
    var descMd: String?,
    var envelopePic: String?,
    var fresh: Boolean?,
    var host: String?,
    var id: Int?,
    var isAdminAdd: Boolean?,
    var link: String?,
    var niceDate: String?,
    var niceShareDate: String?,
    var origin: String?,
    var prefix: String?,
    var projectLink: String?,
    var publishTime: Long?,
    var realSuperChapterId: Int?,
    var route: Boolean?,
    var selfVisible: Int?,
    var shareDate: Long?,
    var shareUser: String?,
    var superChapterId: Int?,
    var superChapterName: String?,
    var tags: List<Tag?>?,
    var title: String?,
    var type: Int?,
    var userId: Int?,
    var visible: Int?,
    var zan: Int?
)