package com.liang.newbaseproject.citypicker

class RepairShopRepository(private val repairShopService: RepairShopService) {

    suspend fun getRepairShopList(query: String): RepairShopBean {
        return repairShopService.getRepairShopList(query)
    }
}
