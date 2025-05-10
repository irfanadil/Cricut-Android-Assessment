package com.cricut.androidassessment.di


import com.cricut.androidassessment.data.repo.QuestionRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule() {

    @Provides
    @Singleton
    fun provideQuestionRepo(): QuestionRepo {
        return QuestionRepo()
    }
}