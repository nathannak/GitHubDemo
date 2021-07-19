package com.example.githubdemo.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubdemo.di.RepositoryDependencies
import com.example.githubdemo.model.issues.Repository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

/*
Repository in charge of making the network call.
Written by Nathan N
*/

// Inject Retrofit via constructor through Hilt Library
class RepositoryProvider @Inject constructor(
    private val repository: RepositoryDependencies,
    @ApplicationContext val context: Context
) {

    suspend fun fetchIssues(): LiveData<Resource> {

        //make sure we don't crash the app if web call fails
        var response :  Response<Repository>? = null
        try {
            response = repository.provideIssues().getRepos()
        }catch (e:Exception){
            Log.e("ERROR","Fetching issues from Issues API error")
        }

        //delay is needed to handle potential back pressure
        delay(250)

        return if (response == null){
            MutableLiveData<Resource>().apply {
                postValue(Resource(Status.ERROR, null, "EMPTY RESPONSE"))}
        } else {
            if (response.isSuccessful) {
                MutableLiveData<Resource>().apply{
                    postValue(Resource(Status.SUCCESS, response.body(), null))}
            } else {
                MutableLiveData<Resource>().apply{
                    postValue(Resource(Status.ERROR, null, response.message()))}
            }
        }
    }

    suspend fun fetchComments(id: String): LiveData<Resource> {

        //we should wrap this in Try/Catch similar to fetchIssues() function
        val response = repository.provideComments().getComments(id)

        //delay is needed to handle potential back pressure
        delay(250)

        return if (response == null){
            MutableLiveData<Resource>().apply {
                postValue(Resource(Status.ERROR, null, "EMPTY RESPONSE"))}
        } else {
            if (response.isSuccessful) {
                MutableLiveData<Resource>().apply{
                    postValue(Resource(Status.SUCCESS, response.body(), null))}
            } else {
                MutableLiveData<Resource>().apply{
                    postValue(Resource(Status.ERROR, null, response.message()))}
            }
        }
    }

}

/* Alternative method to use flow instead of old fashioned way or returning live data:

//    @ExperimentalCoroutinesApi
//    suspend fun fetchIssues() =
//
//        flow {
//            delay(1000)
//            val response = repository.provideIssues().getRepos()
//
//            if (response == null){
//                emit(Resource(Status.ERROR, null, "EMPTY RESPONSE"))
//            } else {
//                if (response.isSuccessful) {
//                    emit(Resource(Status.SUCCESS, response.body(), null))
//                } else {
//                    emit(Resource(Status.ERROR, null, response.message()))
//                }
//            }
//        }.buffer().flowOn(Dispatchers.Main)}

//    @ExperimentalCoroutinesApi
//    suspend fun fetchComments() =
//
//        flow {
//
//            val response = repository.provideComments().getComments("7241");
//
//            if (response == null){
//                emit(Resource(Status.ERROR, null, "EMPTY RESPONSE"))
//            } else {
//                if (response.isSuccessful) {
//                    emit(Resource(Status.SUCCESS, response.body(), null))
//                } else {
//                    emit(Resource(Status.ERROR, null, response.message()))
//                }
//            }
//        }.flowOn(Dispatchers.Main)

*/