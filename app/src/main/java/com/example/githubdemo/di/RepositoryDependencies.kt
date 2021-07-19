package com.example.githubdemo.di

import com.example.githubdemo.model.CommentsApi
import com.example.githubdemo.model.IssueApi
import com.example.githubdemo.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/*
Module to provide Retrofit object to our Repository provider
Written by Nathan N
*/

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryDependencies @Inject constructor() {

    @Provides
    @Singleton
    fun provideIssues(): IssueApi {
        return Retrofit.Builder()
            .baseUrl(Util.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IssueApi::class.java)
    }

    @Singleton
    fun provideComments(): CommentsApi {
        return Retrofit.Builder()
            .baseUrl(Util.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentsApi::class.java)
    }

}