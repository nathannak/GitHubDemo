package com.example.githubdemo.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubdemo.model.comments.CommentsItem
import com.example.githubdemo.model.issues.RepositoryItem
import com.example.githubdemo.repository.RepositoryProvider
import com.example.githubdemo.repository.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/*
ViewModel class for CommentsFragment
Written by Nathan N
*/

class CommentsFragmentViewModel @ViewModelInject constructor(

    //Inject repository to ViewModel using Hilt
    private val repositoryProvider: RepositoryProvider,
) : ViewModel() {

    //make sure livedata is not exposed

    val liveData : MutableLiveData<List<CommentsItem>>
        get() = _liveData
    private var _liveData: MutableLiveData<List<CommentsItem>> = MutableLiveData()

    val loading : MutableLiveData<Boolean>
        get() = _loading
    private var _loading = MutableLiveData<Boolean>().apply { postValue(true) }

    val error : MutableLiveData<Boolean>
    get() = _error
    private var _error   = MutableLiveData<Boolean>().apply { postValue(false) }

    @ExperimentalCoroutinesApi
    fun getCommentsFromRepository(id: String) {
        viewModelScope.launch {

            val response =  repositoryProvider.fetchComments(id)
            delay(500)
            if (response.value?.status == Status.SUCCESS) {
                _liveData.postValue(response.value?.data as List<CommentsItem>?)
                _loading.postValue(false)
            } else if (response.value?.status == Status.LOADING) {
                _error.postValue(true)
            }
        }
    }

    //like mentioned in ListFragmentViewModel, we could also get this data via flow and collect
}