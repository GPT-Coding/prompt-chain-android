package com.prompt.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prompt.domain.PromptsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val promptsUseCase: PromptsUseCase) : ViewModel() {

    private val _state = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val state: StateFlow<HomeViewState> = _state

    fun loadPrompts() {
        viewModelScope.launch {
            promptsUseCase.invoke().collect { result ->
                result.fold(
                    onSuccess = { promptModel ->
                        val content = promptModel.helloPrompt
                        _state.value = if (content == "empty") {
                            HomeViewState.Empty
                        } else {
                            HomeViewState.Success(content)
                        }
                    },
                    onFailure = {
                        _state.value = HomeViewState.Error(it.message)
                    }
                )
            }
        }
    }
}
