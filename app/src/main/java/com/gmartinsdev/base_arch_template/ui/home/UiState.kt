package com.gmartinsdev.base_arch_template.ui.home

import com.gmartinsdev.base_arch_template.data.model.User

/**
 *  state class representing the current/latest UI state
 */
sealed class UiState {
    data object Loading : UiState()
    data class Loaded(val data: List<User>) : UiState()
    data class Error(val errorMessage: String) : UiState()
}