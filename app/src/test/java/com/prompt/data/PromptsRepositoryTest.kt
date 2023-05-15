package com.prompt.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PromptsRepositoryTest {
    private val mockService: PromptsService = mockk()
    private val repository: PromptsRepository = PromptsRepositoryImpl(mockService)


    @Test
    fun `test getPromptData when service returns success`() = runBlockingTest {
        val mockPrompt = Prompt("mock data")
        val mockFlow = flowOf(Result.success(mockPrompt))
        coEvery { mockService.getPromptData() } returns mockFlow

        val result = repository.getPromptData().first()

        assertEquals(Result.success(mockPrompt), result)
    }

    @Test
    fun `test getPromptData when service returns error`() = runBlockingTest {
        val mockError = Exception("mock error")
        val mockFlow = flowOf(Result.failure<Prompt>(mockError))
        coEvery { mockService.getPromptData() } returns mockFlow

        val result = repository.getPromptData().first()

        assertEquals(Result.failure<Prompt>(mockError), result)
    }
}
