package com.cricut.androidassessment.data.model

/*
enum class QuestionType {
    TRUE_FALSE, MULTIPLE_ANSWER, TEXT_INPUT
}

// Sealed class hierarchy for different question types
sealed class Question(
    open val id: Int,
    open val questionText: String,
    open val points: Int,
    open val type: QuestionType
) {
    // True/False question
    data class TrueFalseQuestion(
        override val id: Int,
        override val questionText: String,
        override val points: Int,
        val correctAnswer: Boolean
    ) : Question(Question.id, Question.questionText, Question.points, QuestionType.TRUE_FALSE)

    // Multiple Answer question (multiple correct answers)
    data class MultipleAnswerQuestion(
        override val id: Int,
        override val questionText: String,
        override val points: Int,
        val options: List<String>,
        val correctAnswers: Set<Int> // Set of correct option indices
    ) : Question(
        Question.id, Question.questionText, Question.points, QuestionType.MULTIPLE_ANSWER)

    // Text Input question
    data class TextInputQuestion(
        override val id: Int,
        override val questionText: String,
        override val points: Int,
        val correctTextAnswer: String,
        val caseSensitive: Boolean = false
    ) : Question(Question.id, Question.questionText, Question.points, QuestionType.TEXT_INPUT)
}

 */


enum class QuestionType {
    TRUE_FALSE, MULTIPLE_ANSWER, TEXT_INPUT
}


// Source hierarchy (e.g., API model)
sealed class ApiQuestion   {
    abstract val id: String
    abstract val questionText: String
    abstract val questionType: QuestionType

    data class TrueFalseApi(
        override val id: String,
        override val questionText: String,
        val correctAnswer: Boolean,

    ) : ApiQuestion() {
        override val questionType = QuestionType.TRUE_FALSE
    }

    data class MultipleChoiceApi(
        override val id: String,
        override val questionText: String,
        val options: List<String>,
        val correctAnswer: List<String>
    ) : ApiQuestion() {
        override val questionType = QuestionType.MULTIPLE_ANSWER
    }

    // Text Input question
    data class TextInputApi(
        override val id: String,
        override val questionText: String,
        val correctAnswer: String,
        val caseSensitive: Boolean = false,
    ) : ApiQuestion(){
        override val questionType = QuestionType.TEXT_INPUT
    }
}









