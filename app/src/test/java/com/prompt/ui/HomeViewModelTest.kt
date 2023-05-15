package com.prompt.ui

import com.prompt.domain.PromptModel
import com.prompt.domain.PromptsUseCase
import com.prompt.rules.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val promptsUseCase = mockk<PromptsUseCase>(relaxed = true)
    private val viewModel = HomeViewModel(promptsUseCase)

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `When init then loading state`() = testCoroutineRule.runBlockingTest {
        val promptsUseCase = mockk<PromptsUseCase>()
        val viewModel = HomeViewModel(promptsUseCase)
        testCoroutineRule.testCoroutineDispatcher.scheduler.runCurrent()

        assertEquals(viewModel.state.value, HomeViewState.Loading)
    }

    @Test
    fun `Given success PromptModel, when PromptsUseCase returns Success, then state should be Success`() =
        testCoroutineRule.runBlockingTest {
            val promptModel = PromptModel("hello world")
            val result = flowOf(Result.success(promptModel))
            every { promptsUseCase.invoke() } returns result

            viewModel.loadPrompts()
            testCoroutineRule.testCoroutineDispatcher.scheduler.runCurrent()

            assertEquals(viewModel.state.value, HomeViewState.Success("hello world"))
        }

    @Test
    fun `Given empty PromptModel, when PromptsUseCase returns Success, then state should be Empty`() =
        testCoroutineRule.runBlockingTest {
            val promptsUseCase = mockk<PromptsUseCase>()
            val viewModel = HomeViewModel(promptsUseCase)
            val promptModel = PromptModel("empty")
            val result = flowOf(Result.success(promptModel))
            coEvery { promptsUseCase.invoke() } returns result

            viewModel.loadPrompts()
            testCoroutineRule.testCoroutineDispatcher.scheduler.runCurrent()

            assertEquals(viewModel.state.value, HomeViewState.Empty)
        }

    @Test
    fun `Given PromptsUseCase returns Failure, then state should be Error`() =
        testCoroutineRule.runBlockingTest {
            val promptsUseCase = mockk<PromptsUseCase>()
            val viewModel = HomeViewModel(promptsUseCase)
            val errorMsg = "error message"
            val result = flowOf(Result.failure<PromptModel>(Throwable(errorMsg)))
            coEvery { promptsUseCase.invoke() } returns result

            viewModel.loadPrompts()
            testCoroutineRule.testCoroutineDispatcher.scheduler.runCurrent()

            assertEquals(viewModel.state.value, HomeViewState.Error(errorMsg))
        }
}
