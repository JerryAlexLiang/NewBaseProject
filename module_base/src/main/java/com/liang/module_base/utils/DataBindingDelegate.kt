package com.liang.module_base.utils

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Activity的[ViewDataBinding]代理类
 * eg: val viewDataBinding: ActivitySearchBinding by ActivityDataBindingDelegate(R.layout.activity_search)
 */
class ActivityDataBindingDelegate<out VD : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    ReadOnlyProperty<Activity, VD> {

    private var binding: VD? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): VD =
        binding ?: DataBindingUtil.setContentView<VD>(thisRef, layoutRes)
            .also { binding = it }

}