package com.example.githubdemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubdemo.R
import com.example.githubdemo.databinding.FragmentCommentsBinding
import com.example.githubdemo.model.comments.CommentsItem
import com.example.githubdemo.util.Util
import com.example.githubdemo.viewmodel.CommentsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/*
CommentsFragment class that hosts comments of a certain issue
Written by Nathan N
*/

@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private val commentsFragmentViewModel: CommentsFragmentViewModel by viewModels()
    private lateinit var fragmentCommentsBinding: FragmentCommentsBinding
    private val args: CommentsFragmentArgs by navArgs()
    private val commentsListAdapter =
        CommentListAdapter(arrayListOf())

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {

        //fetch issues using ViewModel
        if(Util.isConnected(requireContext())) {
            val id = args.commentsUrl
            commentsFragmentViewModel.getCommentsFromRepository(id)
        }else{
            Toast.makeText(requireContext(),"Device is offline", Toast.LENGTH_LONG).show()
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentCommentsBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_comments,container,false
        )

        setupObservers()

        return fragmentCommentsBinding.root
    }

    //setup RecyclerView adapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentCommentsBinding.commentList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentsListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )

        }
    }

    private fun setupObservers() {

        commentsFragmentViewModel.loading.observe(viewLifecycleOwner, {

            if (it) {
                fragmentCommentsBinding.progressBar2.visibility = View.VISIBLE
            } else {
                fragmentCommentsBinding.progressBar2.visibility = View.INVISIBLE
            }
        })

        commentsFragmentViewModel.liveData.observe(viewLifecycleOwner, {
            commentsListAdapter.updateIssueList(it as ArrayList<CommentsItem>,requireContext())
        })

        commentsFragmentViewModel.error.observe(viewLifecycleOwner, {
            if(it)
                Toast.makeText(requireContext(),"Error loading data",Toast.LENGTH_LONG).show()
        })

    }
}