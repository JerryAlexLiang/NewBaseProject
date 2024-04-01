package com.liang.module_weather.ui.weather

import com.liang.module_weather.logic.model.WeatherBean

/**
 * - Time: 2024/3/28/0028 16:49
 * - User: Jerry
 * - Description: 密封类
 */
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val result: WeatherBean?) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}
