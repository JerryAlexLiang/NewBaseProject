package com.liang.newbaseproject.koin

import com.jeremyliao.liveeventbus.core.LiveEvent

data class HomeRecommendBean(
    var avatar: String?,
    var fansNum: String?,
    var isAttention: Boolean?,
    var jokesNum: String?,
    var nickname: String?,
    var userId: Int?
) : LiveEvent