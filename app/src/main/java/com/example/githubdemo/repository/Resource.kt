package com.example.githubdemo.repository

import com.example.githubdemo.model.issues.RepositoryItem
import kotlin.reflect.KClass

/**
class that holds a Resource value that maps data to loading status.
Written by Nathan N
 */

data class Resource(val status: Status, val data: Any?, val message: String?) {

    companion object {
        fun success(data: List<RepositoryItem>): Resource {
            return Resource(Status.SUCCESS, data, null)
        }

        fun error(msg: String, data: List<RepositoryItem>?): Resource {
            return Resource(Status.ERROR, null, msg)
        }

        fun loading(): Resource {
            return Resource(Status.LOADING, null, null)
        }
    }
}