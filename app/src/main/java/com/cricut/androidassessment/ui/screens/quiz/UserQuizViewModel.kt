package com.cricut.androidassessment.ui.screens.quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cricut.androidassessment.data.ApiResponse
import com.cricut.androidassessment.data.model.UserDomainQuestion
import com.cricut.androidassessment.data.repo.QuestionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserQuizViewModel @Inject constructor(val repo: QuestionRepo) : ViewModel() {
    // TODO implement state variables and functions

    private val _currentIndex = mutableIntStateOf(0)
    val currentIndex: State<Int> = _currentIndex

    private val backStack = mutableListOf<Int>()

    val canGoNext: Boolean
        get() = _currentIndex.intValue < questions.lastIndex

    val canGoBack: Boolean get() = _currentIndex.intValue > 0

    var userQuizState = mutableStateOf(UserQuizState())
    var questions:List<UserDomainQuestion> = listOf()

    init {
        userQuizState.value = UserQuizState(loading = true)
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch() {
            repo.getAllUserDomainQuestions()
                .collect { apiResult->
                    when(apiResult){
                        is ApiResponse.Success -> {
                            userQuizState.value = UserQuizState(success = true)
                            questions = apiResult.data
                        }
                        is ApiResponse.Error -> {
                            userQuizState.value = UserQuizState(error = apiResult.message)
                        }
                        ApiResponse.Loading -> {
                            userQuizState.value = UserQuizState(loading = true)
                        }
                    }
                }
        }
    }

    fun nextQuestion() {
        if (canGoNext) {
            backStack.add(_currentIndex.value)
            _currentIndex.value++
        }
    }

    fun previousQuestion() {
        if ( backStack.isNotEmpty()) {
            _currentIndex.value--
            backStack.removeAt(_currentIndex.value)

        }
    }

    fun currentQuestion(): UserDomainQuestion = questions[_currentIndex.value]

}
