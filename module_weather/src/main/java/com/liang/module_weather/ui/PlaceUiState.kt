package com.liang.module_weather.ui

import com.liang.module_weather.logic.model.PlaceResponse

/**
 * - Time: 2024/3/25/0025 14:35
 * - User: Jerry
 * - Description: 密封类
 */
sealed class PlaceUiState {
    object Loading : PlaceUiState()

    data class Success(val result: PlaceResponse) : PlaceUiState()

    data class Error(val message: String) : PlaceUiState()
}
