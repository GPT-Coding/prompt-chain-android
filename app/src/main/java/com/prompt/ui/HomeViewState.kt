package com.prompt.ui

sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Success(val content: String) : HomeViewState()
    object Empty : HomeViewState()
    data class Error(val message: String?) : HomeViewState()
}
