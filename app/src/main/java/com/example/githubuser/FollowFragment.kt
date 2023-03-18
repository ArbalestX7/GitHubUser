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
    var username : String? = null

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "section_username"
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
        val position = arguments?.getInt(ARG_POSITION, 0)
        val username = arguments?.getString(ARG_USERNAME)

        Log.d("arguments: position", position.toString())
        Log.d("arguments: username", username.toString())

        detailUserViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                DetailUserViewModel::class.java
            )
        detailUserViewModel.isLoading.observe(requireActivity()){loading ->
            showLoading(loading)
        }

        if (position == 1){
            detailUserViewModel.getFollower(username.toString())
            detailUserViewModel.followers.observe(viewLifecycleOwner){
                binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

                for (user in it){
                    listFollower.add(
                        """
                            ${user.avatarUrl};${user.login}
                        """.trimIndent()
                    )
                }

                for (follower in listFollower) {
                    Log.d("follower", follower)
                }

                val adapter = UserAdapter(listFollower)
                binding.rvFollow.adapter = adapter
            }
        } else {
            detailUserViewModel.getFollowing(username.toString())
            detailUserViewModel.following.observe(viewLifecycleOwner){
                binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

                for (user in it){
                    listFollowing.add(
                        """
                            ${user.avatarUrl};${user.login}
                        """.trimIndent()
                    )
                }

                for (following in listFollowing) {
                    Log.d("following", following)
                }

                val adapter = UserAdapter(listFollowing)
                binding.rvFollow.adapter = adapter
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.followProgressBar.visibility = View.VISIBLE
        } else {
            binding.followProgressBar.visibility = View.GONE
        }
    }
}
