package com.example.githubdemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubdemo.R
import com.example.githubdemo.databinding.FragmentListBinding
import com.example.githubdemo.model.issues.RepositoryItem
import com.example.githubdemo.util.Util
import com.example.githubdemo.viewmodel.ListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/*
List Fragment, first fragment in the app that hosts issues list
Written by Nathan N
*/

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val listFragmentViewModel: ListFragmentViewModel by viewModels()
    private lateinit var fragmentlistBinding: FragmentListBinding

    private val issuesListAdapter =
        IssueListAdapter(arrayListOf(), object : IssueListAdapter.OnItemClickListener {
            override fun onItemClick(repositoryItem: RepositoryItem) {
                    findNavController().navigate(
                            ListFragmentDirections.actionListFragmentToCommentsFragment(
                                repositoryItem.number.toString()
                            )
                    )
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {

        //fetch issues using viewmodel
        if(Util.isConnected(requireContext())) {
            listFragmentViewModel.getIssuesFromRepository()
        }else{
            Toast.makeText(requireContext(),"Device is offline",Toast.LENGTH_LONG).show()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentlistBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )

        setupObservers()

        return fragmentlistBinding.root
    }

    //setup RecyclerView adapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup RecyclerView adapter
        fragmentlistBinding.issueList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = issuesListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }

        //hide nav bar after 3 seconds to create better UI
        Util.hideNavBar(requireActivity() as AppCompatActivity)
    }

    private fun setupObservers() {

        listFragmentViewModel.loading.observe(viewLifecycleOwner, {

            if (it) {
                fragmentlistBinding.progressBar.visibility = View.VISIBLE
            } else {
                fragmentlistBinding.progressBar.visibility = View.INVISIBLE
            }
        })

        listFragmentViewModel.liveData.observe(viewLifecycleOwner, {
            issuesListAdapter.updateIssueList(it as ArrayList<RepositoryItem>)
        })

        listFragmentViewModel.error.observe(viewLifecycleOwner, {
            if(it)
                Toast.makeText(requireContext(),"Error loading data",Toast.LENGTH_LONG).show()
        })
    }

}