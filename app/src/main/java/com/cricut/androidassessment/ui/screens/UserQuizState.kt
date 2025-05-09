package com.cricut.androidassessment.ui.screens

import com.cricut.androidassessment.data.model.UserDomainQuestion

data class UserQuizState(val loading: Boolean= false, val questionList:List<UserDomainQuestion>?=null, val error: String="") {
}