package com.prompt.domain

import com.prompt.data.PromptsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PromptsUseCase {
    fun invoke(): Flow<Result<PromptModel>>
}

class PromptsUseCaseImpl(private val repository: PromptsRepository) : PromptsUseCase {

    override fun invoke(): Flow<Result<PromptModel>> {
        return repository.getPromptData().map { result ->
            result.map { prompt ->
                PromptModel(
                    helloPrompt = prompt.data?.let { "hello $it" } ?: "empty"
                )
            }
        }
    }
}
