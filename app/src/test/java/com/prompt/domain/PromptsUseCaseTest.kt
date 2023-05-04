package com.prompt.domain

import com.prompt.data.Prompt
import com.prompt.data.PromptsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PromptsUseCaseTest {

    private val promptsRepository = mockk<PromptsRepository>()
    private lateinit var promptsUseCase: PromptsUseCase

    @Before
    fun setUp() {
        promptsUseCase = PromptsUseCaseImpl(promptsRepository)
    }

    @Test
    fun `getPromptData when repository returns prompt with non-null data`() = runBlocking {
        val prompt = Prompt("world")
        coEvery { promptsRepository.getPromptData() } returns flowOf(Result.success(prompt))

        val expected = PromptModel("hello world")

        val result = promptsUseCase.invoke().single()

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `getPromptData when repository returns prompt with null data`() = runBlocking {
        val prompt = Prompt(null)
        coEvery { promptsRepository.getPromptData() } returns flowOf(Result.success(prompt))

        val expected = PromptModel("empty")

        val result = promptsUseCase.invoke().single()

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `getPromptData when repository returns error`() = runBlocking {
        val error = Throwable("error")
        coEvery { promptsRepository.getPromptData() } returns flowOf(Result.failure(error))

        val result = promptsUseCase.invoke().single()

        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}
