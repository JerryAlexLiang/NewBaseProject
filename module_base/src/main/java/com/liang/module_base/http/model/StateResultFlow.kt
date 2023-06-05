package com.liang.module_base.http.model

import kotlinx.coroutines.flow.MutableStateFlow

abstract class StateResultFlow<T> : MutableStateFlow<BaseResult<T>> {
}