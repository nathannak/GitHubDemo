package com.example.githubdemo.model

import com.example.githubdemo.model.comments.Comments
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/*
Interface to be used by Retrofit to get comments
Written by Nathan N
*/

interface CommentsApi {

    @GET("repos/ReactiveX/RxJava/issues/{id}/comments")
    suspend fun getComments(@Path("id") id: String,): Response<Comments>?
}
