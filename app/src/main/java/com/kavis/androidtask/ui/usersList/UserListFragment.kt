package com.kavis.androidtask.ui.usersList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kavis.androidtask.R
import com.kavis.androidtask.USER_ID
import com.kavis.androidtask.databinding.FragmentUserListBinding
import com.kavis.androidtask.util.ConnectivityUtil
import com.kavis.androidtask.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment: Fragment(), UserListAdapter.UserItemListener {
    lateinit var binding: FragmentUserListBinding
    private val viewModel: UserListViewModel by viewModels()
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter(this)
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.adapter = adapter
    }

    private fun setupObservers() {

        val data = viewModel.getUsers(ConnectivityUtil.isNetworkConnected)
        data?.networkState?.observe(viewLifecycleOwner, Observer {
                when (it) {
                    NetworkResult.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    NetworkResult.Status.ERROR ->
                        binding.progressBar.visibility = View.GONE

                    NetworkResult.Status.LOADING ->
                        binding.progressBar.visibility = View.VISIBLE
                }
        })

        data?.pagedList?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onUserClicked (userId: String) {
        findNavController().navigate(
            R.id.action_userListFragment_to_userDetailFragment,
            bundleOf(USER_ID to userId)
        )
    }

}