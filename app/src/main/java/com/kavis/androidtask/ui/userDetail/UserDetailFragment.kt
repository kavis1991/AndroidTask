package com.kavis.androidtask.ui.userDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.kavis.androidtask.USER_ID
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.databinding.FragmentUserDetailsBinding
import com.kavis.androidtask.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment: Fragment() {

    lateinit var binding: FragmentUserDetailsBinding
    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(USER_ID)?.let {
            viewModel.start(it)
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                NetworkResult.Status.SUCCESS -> {
                    bindCharacter(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.layoutUser.visibility = View.VISIBLE
                }

                NetworkResult.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                NetworkResult.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.layoutUser.visibility = View.GONE
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindCharacter(user: User) {
        binding.name.text = "${user.title} ${user.firstName} ${user.lastName}"
        binding.tvEmail.text = user.email
        binding.tvGender.text = user.gender
        binding.tvDob.text = user.dateOfBirth
        binding.tvPhone.text = user.phone
        Glide.with(binding.root)
            .load(user.picture)
            .transform(CircleCrop())
            .into(binding.image)
    }
}