package com.prompt.data

import kotlinx.coroutines.flow.Flow

interface PromptsRepository {
    fun getPromptData(): Flow<Result<Prompt>>
}

class PromptsRepositoryImpl(
    private val promptsService: PromptsService
) : PromptsRepository {
    override fun getPromptData(): Flow<Result<Prompt>> {
        return promptsService.getPromptData()
    }
}
