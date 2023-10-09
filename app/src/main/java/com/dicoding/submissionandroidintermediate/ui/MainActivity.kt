package com.dicoding.submissionandroidintermediate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.customeView.BottomCreatePostFragment
import com.dicoding.submissionandroidintermediate.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomCreatePostFragment: BottomCreatePostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        bottomCreatePostFragment = BottomCreatePostFragment()

        binding.fabPost.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.fab_post -> {
                bottomCreatePostFragment.show(supportFragmentManager, "BottomCreatePost")
            }
        }
    }
}