package life.league.challenge.kotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.api.Resource
import life.league.challenge.kotlin.databinding.FragmentPostBinding
import life.league.challenge.kotlin.fragments.intent.PostIntent
import life.league.challenge.kotlin.fragments.postAdapter.PostListAdapter
import life.league.challenge.kotlin.states.PostState

@AndroidEntryPoint
class PostFragment : Fragment() {
    private val TAG = "PostFragment"
    private val viewModel: PostViewModel by viewModels()
    private val moviesAdapter = PostListAdapter(emptyList())

    private lateinit var _binding: FragmentPostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater)
        observeViewModel()
        _binding.rvPost.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }
        return _binding.root
    }



    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel?.postIntent.send(PostIntent.LoginIntent("hello", "world"))
            viewModel?.state.collect {
                when (it) {
                    is PostState.Error -> {
                        Log.e(TAG, "observeViewModel Error: $it")
                    }
                    PostState.Idle -> {
                        Log.e(TAG, "observeViewModel Idle: $it")
                    }
                    PostState.Loading -> {
                        Log.e(TAG, "observeViewModel Loading: $it")
                    }
                    is PostState.LoginSuccess -> {
                        Log.e(TAG, "observeViewModel LoginSuccess: $it")
                        viewModel?.postIntent.send(PostIntent.FetchPosts)

                    }
                    is PostState.Posts -> {
                        Log.e(TAG, "observeViewModel Posts: $it")

                    }
                }
            }
        }
    }
}