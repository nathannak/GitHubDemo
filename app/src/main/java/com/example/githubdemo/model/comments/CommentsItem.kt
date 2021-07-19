package com.example.githubdemo.model.comments

data class CommentsItem(
    val author_association: String,
    val body: String,
    val created_at: String,
    val html_url: String,
    val id: Int,
    val issue_url: String,
    val node_id: String,
    val performed_via_github_app: Any,
    val updated_at: String,
    val url: String,
    val user: User
)