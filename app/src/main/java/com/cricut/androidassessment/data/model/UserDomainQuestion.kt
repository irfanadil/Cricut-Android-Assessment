package com.cricut.androidassessment.data.model

// Target hierarchy (e.g., Domain model)
//interface QuestionScore { val maxScore: Int }

sealed class UserDomainQuestion   {
    //abstract override val maxScore: Int
    abstract val id: Int
    abstract val questionText: String
    abstract val questionType: QuestionType
    abstract val maxScore: Int

    data class TrueFalseUserDomain(
        override val id: Int,
        override val questionText: String,
        override val maxScore: Int,
        override val questionType: QuestionType,
        val isCorrect: Boolean,
        var userAnswer: Boolean?=null,
    ) : UserDomainQuestion()

    data class MultipleChoiceUserDomain(
        override val id: Int,
        override val questionText: String,
        override val maxScore: Int,
        override val questionType: QuestionType,
        val choices: List<String>,
        val correctAnswers: List<String>,
        var userAnswer: ArrayList<String> = arrayListOf<String>(),

        ) : UserDomainQuestion()

    // Text Input question
    data class TextInputQuestionUser(
        override val id: Int,
        override val questionText: String,
        override val questionType: QuestionType,
        override val maxScore: Int,
        val correctTextAnswer: String,
        var userAnswer:String?=null,
        val caseSensitive: Boolean = false,
    ) : UserDomainQuestion()
}