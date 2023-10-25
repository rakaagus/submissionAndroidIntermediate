package com.dicoding.submissionandroidintermediate.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.submissionandroidintermediate.data.Result
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.data.local.entity.StoryEntity
import com.dicoding.submissionandroidintermediate.databinding.FragmentHomeBinding
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory
import com.dicoding.submissionandroidintermediate.ui.detail.DetailActivity
import com.dicoding.submissionandroidintermediate.ui.home.adapter.LoadingAdapter
import com.dicoding.submissionandroidintermediate.ui.home.adapter.StoryAdapter
import com.dicoding.submissionandroidintermediate.ui.map.MapsActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var loadingHome: Dialog
    private lateinit var storyAdapter: StoryAdapter
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingHome = Dialog(requireActivity())

        binding.ivMap.setOnClickListener {
            startActivity(Intent(requireActivity(), MapsActivity::class.java))
        }

        binding.swipeRefresh.setOnRefreshListener {
            setupDataHome()
        }

        setupAdapter()
        setupDataHome()
    }

    private fun setupAdapter() {
        storyAdapter = StoryAdapter()
        val layoutManager = LinearLayoutManager(requireActivity())
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvStory.adapter = storyAdapter
        binding.rvStory.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingAdapter{
                storyAdapter.retry()
            }
        )

        binding.rvStory.layoutManager = layoutManager
        binding.rvStory.addItemDecoration(itemDecoration)

        storyAdapter.addLoadStateListener { state->
            binding.swipeRefresh.isRefreshing = state.source.refresh is LoadState.Loading
        }
    }

    private fun setupDataHome() {
        homeViewModel.getUserSession().observe(viewLifecycleOwner){user->
            homeViewModel.getAllStory(user.token).observe(viewLifecycleOwner){
                updateDataStory(it)
            }
        }
    }

    private fun updateDataStory(it: PagingData<StoryEntity>) {
        storyAdapter.submitData(lifecycle, it)
    }

    override fun onClick(v: View?) {

    }
}