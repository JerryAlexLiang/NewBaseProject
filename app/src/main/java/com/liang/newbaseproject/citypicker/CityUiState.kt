package com.liang.newbaseproject.citypicker

sealed class CityUiState {

    object Loading : CityUiState()

    data class Success(val result: ArrayList<ProvinceInfo>) : CityUiState()

    data class Error(val message: String) : CityUiState()
}