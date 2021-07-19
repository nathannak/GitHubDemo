package com.example.githubdemo.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubdemo.model.comments.CommentsItem
import com.example.githubdemo.model.issues.RepositoryItem
import com.example.githubdemo.repository.RepositoryProvider
import com.example.githubdemo.repository.Resource
import com.example.githubdemo.repository.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/*
ViewModel class for CommentsFragment
Written by Nathan N
*/

class ListFragmentViewModel @ViewModelInject constructor(

    //Inject repository to ViewModel using Hilt
    private val repositoryProvider: RepositoryProvider,
) : ViewModel() {

    //make sure livedata is not exposed

    val liveData : MutableLiveData<List<RepositoryItem>>
        get() = _liveData
    private var _liveData: MutableLiveData<List<RepositoryItem>> = MutableLiveData()

    val loading : MutableLiveData<Boolean>
        get() = _loading
    private var _loading = MutableLiveData<Boolean>().apply { postValue(true) }

    val error : MutableLiveData<Boolean>
        get() = _error
    private var _error   = MutableLiveData<Boolean>().apply { postValue(false) }

    fun getIssuesFromRepository() {
        viewModelScope.launch {

            val response =  repositoryProvider.fetchIssues()

            //delay is needed to handle potential back pressure
            delay(250)

            if (response.value?.status == Status.SUCCESS) {
                _liveData.postValue(response.value?.data as List<RepositoryItem>?)
                _loading.postValue(false)
            } else if (response.value?.status == Status.ERROR) {
                _error.postValue(true)
            }
        }
    }
}

/* Alternatively we could use flow which is newer tech:

    @ExperimentalCoroutinesApi
    fun getIssuesFromRepository() {
        viewModelScope.launch {
            repositoryProvider.fetchIssues()
                .buffer()
                .collect {
                    //handle back pressure
                    delay(1000)

                    if (it.status == Status.SUCCESS) {
                        liveData.postValue(it.data as List<RepositoryItem>?)
                        loading.postValue(false)
                    } else if (it.status == Status.LOADING) {
                        error.postValue(true)
                    }
                }
        }
    }

 */
