package com.cricut.androidassessment.data.repo

import com.cricut.androidassessment.data.ApiResponse
import com.cricut.androidassessment.data.model.ApiQuestion
import com.cricut.androidassessment.data.model.QuestionType
import com.cricut.androidassessment.util.QuestionMapper

// Target hierarchy (e.g., Domain model)
//interface QuestionScore { val maxScore: Int }

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuestionRepo {

     fun getAllUserDomainQuestions()= flow{
         try {
             val apiQuestionList = listOf(
                 ApiQuestion.TrueFalseApi(
                     id = "1",
                     questionText = "The Earth is flat",
                     correctAnswer = false
                 ),
                 ApiQuestion.MultipleChoiceApi(
                     id = "2",
                     questionText = "Which are European capitals?",
                     options = listOf("Paris", "London", "Berlin", "Madrid", "katreni"),
                     correctAnswer = listOf("Paris", "Berlin", "Madrid"),
                 ),
                 ApiQuestion.TextInputApi(
                     id = "3",
                     questionText = "Who developed the theory of relativity?",
                     correctAnswer = "Albert Einstein",
                     caseSensitive = false
                 )
             )
             delay(3000)


             emit(ApiResponse.Success(QuestionMapper.mapAll(apiQuestionList)))
         }
         catch (e: Exception){
             emit(ApiResponse.Error("exception = "+e.message.toString()))
         }
    }.flowOn(Dispatchers.Default)





}