package com.gmartinsdev.base_arch_template.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmartinsdev.base_arch_template.data.remote.Status
import com.gmartinsdev.base_arch_template.domain.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  viewmodel class responsible for handling data from data to view layer
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsers: GetUsers
) : ViewModel() {

    init {
        fetchData("")
    }

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    /**
     * fetching data from repository and parsing it accordingly for view layer consumption
     */
    fun fetchData(title: String) {
        viewModelScope.launch {
            if (title.isEmpty()) {
                getAllUsers()
            } else {
//                getUserByTitle(title)
            }
        }
    }

    /**
     * get all users stored locally
     */
    private suspend fun getAllUsers() {
        getUsers().collect { result ->
            _state.value = when (result.status) {
                Status.SUCCESS -> UiState.Loaded(result.data ?: emptyList())
                Status.ERROR -> UiState.Error(
                    result.error?.message ?: "error while retrieving all users"
                )
            }
        }
    }
}