package com.example.githubdemo.model

import com.example.githubdemo.model.issues.Repository
import com.example.githubdemo.util.Util.Constants.ISSUES_END_POINT
import retrofit2.Response
import retrofit2.http.GET

/*
Interface to be used by Retrofit to get Issues
Written by Nathan N
*/

interface IssueApi {

    @GET(ISSUES_END_POINT)
    suspend fun getRepos(): Response<Repository>?
}
