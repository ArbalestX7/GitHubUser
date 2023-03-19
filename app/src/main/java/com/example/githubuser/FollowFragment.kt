package com.example.githubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    private var listFollower = ArrayList<String>()
    private var listFollowing = ArrayList<String>()
    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = ""
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = 0
        var username = arguments?.getString(ARG_USERNAME)

        Log.d("arguments: position", position.toString())
        Log.d("arguments: username", username.toString())

        detailUserViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                DetailUserViewModel::class.java
            )
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1){
            showLoadingUser(true)
            username?.let {detailUserViewModel.getFollower(it)}
            detailUserViewModel.followers.observe(viewLifecycleOwner,{
                setFollowData(it)
                showLoadingUser(false)
            })
        } else {
            showLoadingUser(true)
            username.let { detailUserViewModel.getFollowing() }
            detailUserViewModel.following.observe(viewLifecycleOwner,{
                setFollowData(it)
                showLoadingUser(false)
            })
        }
    }
    private fun showLoadingUser(isLoading: Boolean) {
        if (isLoading) {
            binding.followProgressBar.visibility = View.VISIBLE
        } else {
            binding.followProgressBar.visibility = View.GONE
        }
    }
    private fun setFollowData(listUser : List<ItemsItem>) {
        binding.apply {
            binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = UserAdapter(listUser)
            binding.rvFollow.adapter = adapter
        }
    }
}
