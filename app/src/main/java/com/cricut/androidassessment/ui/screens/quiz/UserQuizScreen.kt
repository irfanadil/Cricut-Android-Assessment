package com.cricut.androidassessment.ui.screens.quiz

import androidx.compose.ui.graphics.Color
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cricut.androidassessment.data.model.QuestionType
import com.cricut.androidassessment.data.model.UserDomainQuestion
import com.cricut.androidassessment.ui.theme.AndroidAssessmentTheme
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*

@Composable
fun AssessmentScreen(
    modifier: Modifier = Modifier,
    viewModel: UserQuizViewModel = viewModel()
) {
    val userQuizState = viewModel.userQuizState.value

    if(userQuizState.loading){
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if(userQuizState.error.isNotEmpty()){
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                userQuizState.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    if (viewModel.questions.isNotEmpty()) {

        val currentQuestion by remember { derivedStateOf { viewModel.currentQuestion() } }
        val canGoBack by remember { derivedStateOf { viewModel.canGoBack } }
        val canGoNext by remember { derivedStateOf { viewModel.canGoNext } }

        BackHandler(enabled = canGoBack) {
            viewModel.previousQuestion()
        }

        Scaffold(
            topBar = { AppBar(viewModel) },
            bottomBar = { NavigationBar(viewModel, canGoBack, canGoNext) }
        ) { padding ->
            Box(modifier = Modifier.padding(8.dp)) {
                when (currentQuestion.questionType) {
                    QuestionType.TRUE_FALSE -> TrueFalseQuestion(currentQuestion as UserDomainQuestion.TrueFalseUserDomain)
                    QuestionType.MULTIPLE_ANSWER -> MultipleChoiceQuestion(currentQuestion as UserDomainQuestion.MultipleChoiceUserDomain)
                    QuestionType.TEXT_INPUT -> TextInputQuestion(currentQuestion as UserDomainQuestion.TextInputQuestionUser)
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(viewModel: UserQuizViewModel) {
    TopAppBar(
        title = { Text("Question ${viewModel.currentIndex.value + 1}") },
        actions = {
            IconButton(onClick = { /* Handle settings */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Settings")
            }
        }
    )
}

@Composable
private fun NavigationBar(
    viewModel: UserQuizViewModel,
    canGoBack: Boolean,
    canGoNext: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { viewModel.previousQuestion() },
            enabled = canGoBack
        ) {
            Log.e("canGoBack", canGoBack.toString())
            Text("Back")
        }

        Button(
            onClick = { viewModel.nextQuestion() },
            enabled = canGoNext
        ) {
            Text("Next")
        }
    }
}

@Composable
fun TrueFalseQuestion(question: UserDomainQuestion.TrueFalseUserDomain) {

    var trueFalseState: Boolean? by remember { mutableStateOf(null) }
    question.userAnswer?.let {
        trueFalseState = it
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AAAAAAAAAAAAA" + question.questionText,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
        )

        Button(
            onClick = {

                trueFalseState = true
                question.userAnswer = true

            },
            colors= ButtonDefaults.buttonColors(containerColor = if (trueFalseState == true) Color.Blue else Color.Gray)
        )
        {
            Text("True")

        }
        Button(onClick = {
            trueFalseState  =false
            question.userAnswer = false

        },
            colors= ButtonDefaults.buttonColors(containerColor = if (trueFalseState==false) Color.Blue else Color.Gray)

        ) {
            Text("False")

        }
    }
}

@Composable
fun MultipleChoiceQuestion(question: UserDomainQuestion.MultipleChoiceUserDomain) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = "AAAAAAAAAAAAA" + question.questionText,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
        )

        // Add multiple choice selection logic
        question.choices.forEachIndexed { index, option ->
            var checkedState by remember { mutableStateOf(false) }
            if (question.userAnswer.contains(option))
                checkedState = true

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {


                Checkbox(
                    checked = checkedState,
                    onCheckedChange = {
                        checkedState = it
                        if (checkedState)
                            question.userAnswer.add(option)
                        else
                            question.userAnswer.remove(option)
                    }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun TextInputQuestion(question: UserDomainQuestion.TextInputQuestionUser) {
    var answer by remember { mutableStateOf("") }
    question.userAnswer?.let {
        answer = it
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "AAAAAAAAAAAAA" + question.questionText,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
        )

        OutlinedTextField(
            value = answer,
            onValueChange = {
                answer = it
                question.userAnswer = answer
            },
            label = { Text("Your answer") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAssessmentScreen() {
    AndroidAssessmentTheme {
        AssessmentScreen()
    }
}
