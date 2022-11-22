package life.league.challenge.kotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.api.Resource
import life.league.challenge.kotlin.databinding.FragmentPostBinding
import life.league.challenge.kotlin.fragments.postAdapter.PostListAdapter

@AndroidEntryPoint
class PostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels()
    private val moviesAdapter = PostListAdapter(emptyList())

    private lateinit var _binding: FragmentPostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater)
       /* getPostList()*/
        callApi()
        _binding.rvPost.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }
        return _binding.root
    }

    fun callApi() {
        viewModel.login("hello", "world");
        viewModel.loginData.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    getPostList()
                }
                Resource.Status.ERROR -> {

                }
            }
        }
    }

    fun getPostList() {
        viewModel.getPersonalDetails()
        viewModel.personalDetailsData.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    moviesAdapter.setData(it.data!!)
                    moviesAdapter.notifyDataSetChanged()
                }
                Resource.Status.ERROR -> {

                }
            }
        }
    }
}