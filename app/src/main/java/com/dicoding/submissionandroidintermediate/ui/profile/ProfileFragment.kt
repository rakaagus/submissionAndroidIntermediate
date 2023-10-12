package com.dicoding.submissionandroidintermediate.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.databinding.FragmentProfileBinding
import com.dicoding.submissionandroidintermediate.ui.auth.login.LoginActivity
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getSessionUser().observe(viewLifecycleOwner){data ->
            binding.tvNameUser.text = data.name
        }

        binding.btnLogout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_logout -> {
                AlertDialog.Builder(requireActivity()).apply {
                    setTitle(getString(R.string.text_title_logout))
                    setMessage(getString(R.string.text_desc_logout))
                    setPositiveButton(getString(R.string.text_button_success_login)) { dialog, _ ->
                        lifecycleScope.launch {
                            profileViewModel.clearSession()
                        }
                        startActivity(Intent(context, LoginActivity::class.java))
                        dialog.dismiss()
                        requireActivity().finish()
                    }
                    setNegativeButton(getString(R.string.text_button_cancle)){dialog, which, ->
                        dialog.dismiss()
                        dialog.cancel()
                    }
                    create()
                    show()
                }
            }
        }
    }
}