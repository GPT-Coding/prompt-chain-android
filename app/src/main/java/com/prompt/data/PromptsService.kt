package com.prompt.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PromptsService {
    fun getPromptData(): Flow<Result<Prompt>> = flow {
        emit(Result.success(Prompt("prompt")))
    }
}
