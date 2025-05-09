package com.cricut.androidassessment.ui.screens

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cricut.androidassessment.data.ApiResponse
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

    init {
        userQuizState.value = UserQuizState(loading = true)
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch() {
            repo.getAllUserDomainQuestions()
                .collect { apiResult->
                    when(apiResult){
                        is ApiResponse.Error -> {
                                userQuizState.value = UserQuizState(error = apiResult.message)
                        }
                        is ApiResponse.Success -> {
                            userQuizState.value = UserQuizState(questionList = apiResult.data)
                        }
                        else -> {

                        }
                    }

                }

        }
    }
}
