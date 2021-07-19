package com.example.githubdemo.model.issues

data class Label(
    val color: String,
    val default: Boolean,
    val description: Any,
//    val id: Int,
    val id: Long,
    val name: String,
    val node_id: String,
    val url: String
)