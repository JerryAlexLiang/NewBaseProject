package com.liang.newbaseproject.livedata

data class LoginTestRequestBean(
    var username: String?,
    var password: String?,
) {
    constructor() : this(username = "", password = "")
}