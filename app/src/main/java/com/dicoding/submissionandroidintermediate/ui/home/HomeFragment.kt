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

        setupDataHome()
    }

    private fun setupDataHome() {
        storyAdapter = StoryAdapter()
        val layoutManager = LinearLayoutManager(requireActivity())
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvStory.adapter = storyAdapter
        binding.rvStory.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingAdapter{
                storyAdapter.retry()
            }
        )

        homeViewModel.getUserSession().observe(viewLifecycleOwner){user->
            homeViewModel.getAllStory(user.token).observe(viewLifecycleOwner){
                updateDataStory(it)
            }
        }

        binding.rvStory.layoutManager = layoutManager
        binding.rvStory.addItemDecoration(itemDecoration)

        storyAdapter.setOnItemClickCallback(object: StoryAdapter.OnClickCallback{
            override fun onItemClicked(data: StoryEntity) {
                setDataToDetailStory(data)
            }
        })
    }

    private fun updateDataStory(it: PagingData<StoryEntity>) {
        storyAdapter.submitData(lifecycle, it)
    }

    private fun setDataToDetailStory(data: StoryEntity) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.ID_STORY_KEY, data.id)
        startActivity(intent)
    }

    private fun dismissLoading() {
        if(loadingHome.isShowing){
            loadingHome.dismiss()
        }
    }

    private fun showDialog() {
        loadingHome.setContentView(R.layout.bg_loading_auth)
        loadingHome.setCancelable(false)
        loadingHome.setCanceledOnTouchOutside(false)
        loadingHome.show()
    }


    override fun onClick(v: View?) {

    }
}