package com.cricut.androidassessment.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cricut.androidassessment.data.ApiResponse
import com.cricut.androidassessment.data.model.UserDomainQuestion
import com.cricut.androidassessment.data.repo.QuestionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssessmentViewModel @Inject constructor(val repo: QuestionRepo) : ViewModel() {
    // TODO implement state variables and functions

    // Remove/Modify this
    private val _uiState = MutableStateFlow<String>("Hello World!")
    val uiState: StateFlow<String> = _uiState.asStateFlow()

    var userQuizState = mutableStateOf(UserQuizState())
    var questions:List<UserDomainQuestion> = listOf()


    private val _apiResponse = MutableStateFlow<ApiResponse<List<UserDomainQuestion>>>(ApiResponse.Loading)
    val apiResponse = _apiResponse.asStateFlow()

    init {
        userQuizState.value = UserQuizState(loading = true)
        loadQuestions()
    }



    fun loadQuestions() {
        viewModelScope.launch() {
            repo.getAllUserDomainQuestions()
                .collect { apiResult->
                    _apiResponse.value = apiResult
                    when(apiResult){
                        is ApiResponse.Success -> {
                             questions = apiResult.data
                        }
                        else -> {}
                    }
                }
        }
    }

    private val _currentIndex = mutableIntStateOf(0)
    val currentIndex: State<Int> = _currentIndex

    private val backStack = mutableListOf<Int>()

    val canGoBack: Boolean
        get() = backStack.isNotEmpty()

    val canGoNext: Boolean
        get() = _currentIndex.intValue < questions.lastIndex

    fun nextQuestion() {
        if (canGoNext) {
            backStack.add(_currentIndex.intValue)
            _currentIndex.intValue++
        }
    }

    fun previousQuestion() {
        if (canGoBack) {
             backStack.remove(_currentIndex.intValue)
            _currentIndex.intValue--
        }
    }


    fun currentQuestion(): UserDomainQuestion = questions[_currentIndex.intValue]

}
