package com.example.githubdemo.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubdemo.R
import com.example.githubdemo.databinding.CommentItemBinding
import com.example.githubdemo.model.comments.CommentsItem
import com.example.githubdemo.util.Util

/*
Recycler view for comments fragment, uses DataBinding
Written by Nathan N
*/

class CommentListAdapter(
    private var commentsList: ArrayList<CommentsItem>,
) : RecyclerView.Adapter<CommentListAdapter.CommentsViewHolder>() {

    fun updateIssueList(newIssueList: ArrayList<CommentsItem>, context : Context) {
        commentsList.clear()
        commentsList.addAll(newIssueList)
        notifyDataSetChanged()

        //notify user why screen is blank
        if(newIssueList.size==0){
            Toast.makeText(context,"No comments on the issue",Toast.LENGTH_LONG).show();
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CommentItemBinding>(
            inflater,
            R.layout.comment_item,
            parent,
            false
        )
        return CommentsViewHolder(view)
    }

    override fun getItemCount() = commentsList.size

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    class CommentsViewHolder(var view: CommentItemBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(commentsItem: CommentsItem) {
            view.comment = commentsItem.body

            //load items with fade
            Util.setFadeAnimation(view.root)
        }

    }

}
