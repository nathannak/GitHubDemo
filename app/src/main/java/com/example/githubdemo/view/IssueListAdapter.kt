package com.example.githubdemo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubdemo.R
import com.example.githubdemo.databinding.IssueItemBinding
import com.example.githubdemo.model.issues.RepositoryItem
import com.example.githubdemo.util.Util

/*
Recycler view for search fragment, uses DataBinding
Written by Nathan N
*/

class IssueListAdapter(
    var IssueList: ArrayList<RepositoryItem>,
    var listener: OnItemClickListener
) : RecyclerView.Adapter<IssueListAdapter.IssueViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(repositoryItem : RepositoryItem)
    }

    fun updateIssueList(newIssueList: ArrayList<RepositoryItem>) {
        IssueList.clear()
        IssueList.addAll(newIssueList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<IssueItemBinding>(
            inflater,
            R.layout.issue_item,
            parent,
            false
        )
        return IssueViewHolder(view)
    }

    override fun getItemCount() = IssueList.size

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(IssueList[position], listener)
    }

    class IssueViewHolder(var view: IssueItemBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(repositoryItem: RepositoryItem, listener: OnItemClickListener) {
            view.repo = repositoryItem
            view.root.setOnClickListener {
                listener.onItemClick(repositoryItem)
            }

            //load items with fade
            Util.setFadeAnimation(view.root)
        }

    }

}
