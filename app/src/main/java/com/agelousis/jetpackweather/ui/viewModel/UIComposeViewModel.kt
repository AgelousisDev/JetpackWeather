package com.agelousis.jetpackweather.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class UIComposeViewModel: ViewModel() {

    val loaderStateMutableStateFlow = MutableStateFlow(value = false)
    val loaderStateStateFlow = loaderStateMutableStateFlow.asStateFlow()

    val networkErrorMutableStateFlow = MutableStateFlow(value = false)
    val networkErrorStateFlow = networkErrorMutableStateFlow.asStateFlow()

    val showDialogMutableStateFlow = MutableStateFlow(value = false)
    val showDialogStateFlow = showDialogMutableStateFlow.asStateFlow()

    val requestLocationMutableState = MutableStateFlow(value = false)
    val requestLocationState: StateFlow<Boolean> = requestLocationMutableState.asStateFlow()

    var alertPair by mutableStateOf<Pair<String?, String?>>(value = null to null)

    fun showDialog() {
        showDialogMutableStateFlow.value = true
    }

    val swipeRefreshMutableStateFlow = MutableStateFlow(value = false)
    val swipeRefreshStateFlow: StateFlow<Boolean> = swipeRefreshMutableStateFlow.asStateFlow()

    var isRefreshing: Boolean = false
        set(value) {
            field = value
            swipeRefreshMutableStateFlow.value = value
        }

    fun isRefreshing() {
        isRefreshing = true
    }

}