package com.liang.newbaseproject.citypicker

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.liang.module_base.base.BaseViewModel
import com.liang.module_base.utils.LogUtils
import kotlinx.coroutines.launch

class RepairShopViewModel(
    application: Application,
    private val repairShopRepository: RepairShopRepository
) : BaseViewModel(application) {

    private val _repairShopBeanLiveData: MutableLiveData<RepairShopBean> = MutableLiveData()
    val repairShopBeanLiveData: LiveData<RepairShopBean>
        get() = _repairShopBeanLiveData

    fun getRepairShopList(query: String) {
        viewModelScope.launch {
            try {
                val repairShopList = repairShopRepository.getRepairShopList(query)
                _repairShopBeanLiveData.value = repairShopList

            } catch (exception: Exception) {
                LogUtils.e(tag = "RepairShopViewModel", msg = exception.message.toString())
            }
        }
    }
}