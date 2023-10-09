package com.dicoding.submissionandroidintermediate.customeView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.ui.Post.AddPostActivity
import com.dicoding.submissionandroidintermediate.ui.SplashScreen.SplashScreen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomCreatePostFragment: BottomSheetDialogFragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_pop_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<LinearLayout>(R.id.linear_post_camera).setOnClickListener(this)
        view.findViewById<LinearLayout>(R.id.linear_post_gallery).setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.linear_post_camera -> {
                val intent = Intent(context, AddPostActivity::class.java)
                intent.putExtra(AddPostActivity.TYPE_POST, "Camera")
                dismiss()
                startActivity(intent)
            }
            R.id.linear_post_gallery -> {
                val intent = Intent(context, AddPostActivity::class.java)
                intent.putExtra(AddPostActivity.TYPE_POST, "Gallery")
                dismiss()
                startActivity(intent)
            }
        }
    }



}