package com.example.githubdemo.model.issues

data class PullRequest(
    val diff_url: String,
    val html_url: String,
    val patch_url: String,
    val url: String
)