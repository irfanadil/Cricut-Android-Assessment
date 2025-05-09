package com.cricut.androidassessment.util

import com.cricut.androidassessment.data.model.ApiQuestion
import com.cricut.androidassessment.data.model.UserDomainQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn

object QuestionMapper {
    fun map(apiQuestion: ApiQuestion): UserDomainQuestion {
        return when (apiQuestion) {
            is ApiQuestion.TrueFalseApi -> mapTrueFalse(apiQuestion)
            is ApiQuestion.MultipleChoiceApi -> mapMultipleChoice(apiQuestion)
            is ApiQuestion.TextInputApi -> mapInputBased(apiQuestion)
        }
    }

    fun mapAll(apiQuestions: List<ApiQuestion>): List<UserDomainQuestion> {

        return apiQuestions.map { apiQuestion->
            when (apiQuestion) {
                is ApiQuestion.TrueFalseApi -> mapTrueFalse(apiQuestion)
                is ApiQuestion.MultipleChoiceApi -> mapMultipleChoice(apiQuestion)
                is ApiQuestion.TextInputApi -> mapInputBased(apiQuestion)
            }
        }


    }


    private fun mapTrueFalse(question: ApiQuestion.TrueFalseApi) =
        UserDomainQuestion.TrueFalseUserDomain(
            id = question.id.toInt(),
            questionText = sanitizeText(question.questionText),
            isCorrect = question.correctAnswer,
            questionType = question.questionType,
            maxScore = 10
        )

    private fun mapMultipleChoice(question: ApiQuestion.MultipleChoiceApi) =
        UserDomainQuestion.MultipleChoiceUserDomain(
            id = question.id.toInt(),
            questionText = sanitizeText(question.questionText),
            choices = question.options.map { sanitizeOption(it) },
            correctAnswers = question.correctAnswer,
            maxScore = 20,
            questionType = question.questionType
        )

    private fun mapInputBased(question: ApiQuestion.TextInputApi) =
        UserDomainQuestion.TextInputQuestionUser(
            id = question.id.toInt(),
            questionText = sanitizeText(question.questionText),
            correctTextAnswer = question.correctAnswer,
            questionType = question.questionType,
            maxScore = 15
        )

    private fun sanitizeText(text: String) = text.trim()
    private fun sanitizeOption(option: String) = option.replaceFirst(" ", ". ")
}