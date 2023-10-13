package com.dicoding.submissionandroidintermediate.ui.post

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.data.Result
import com.dicoding.submissionandroidintermediate.databinding.ActivityAddPostBinding
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory
import com.dicoding.submissionandroidintermediate.ui.auth.login.LoginActivity
import com.dicoding.submissionandroidintermediate.utils.getImageUri
import com.dicoding.submissionandroidintermediate.utils.reduceFileImage
import com.dicoding.submissionandroidintermediate.utils.uriToFile
import kotlinx.coroutines.launch

class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding
    private var currentImageUri: Uri? = null
    private lateinit var loadingUpload: Dialog
    private val addPostViewModel by viewModels<AddPostViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUEST_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri: Uri?->
        if(uri != null){
            currentImageUri = uri
            showImage()
        }else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startGallery(){
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launchIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){ isSuccess->
        if(isSuccess){
            showImage()
        }
    }

    private fun startCamera(){
        currentImageUri = getImageUri(this)
        launchIntentCamera.launch(currentImageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUpload = Dialog(this)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUEST_PERMISSION)
        }

        setEnableButton()

        binding.edtDescription.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEnableButton()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        val typePost = intent.getStringExtra(TYPE_POST)
        if(typePost?.lowercase().equals("camera")){
            binding.previewImageView.setImageDrawable(getDrawable(R.drawable.bg_image_camera))
        }else if(typePost?.lowercase().equals("gallery")){
            binding.previewImageView.setImageDrawable(getDrawable(R.drawable.bg_image_galery))
        }

        binding.previewImageView.setOnClickListener {
            if(typePost?.lowercase().equals("camera")){
                startCamera()
            }else if(typePost?.lowercase().equals("gallery")){
                startGallery()
            }
        }

        binding.ivBack.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.text_title_logout))
                setMessage(getString(R.string.text_warn_back_story))
                setPositiveButton(getString(R.string.text_button_success_login)) { _, _ ->
                    finish()
                }
                setNegativeButton(getString(R.string.text_button_cancle)){dialog, which, ->
                    dialog.dismiss()
                    dialog.cancel()
                }
                create()
                show()
            }
        }

        binding.btnUpload.setOnClickListener { uploadImage() }
    }

    private fun setEnableButton() {
        val result = binding.edtDescription.text
        Log.d(TAG, "setEnableButton: $currentImageUri $result")
        binding.btnUpload.isEnabled = result.isNotEmpty() && currentImageUri != null
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image Uri", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let {uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val desc = binding.edtDescription.text.toString()

            addPostViewModel.getUserSession().observe(this){ user->
                addPostViewModel.uploadStory(user.token, imageFile, desc).observe(this){result->
                    if(result != null){
                        when(result){
                            is Result.Loading -> {
                                showDialog()
                            }

                            is Result.Success -> {
                                dismissLoading()
                                AlertDialog.Builder(this).apply {
                                    setTitle(getString(R.string.text_title_success_login))
                                    setMessage(getString(R.string.text_upload_success))
                                    setPositiveButton(getString(R.string.text_back, "Home")) { _, _ ->
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            is Result.Error -> {
                                dismissLoading()
                            }

                            else -> {

                            }
                        }
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun dismissLoading() {
        if(loadingUpload.isShowing){
            loadingUpload.dismiss()
        }
    }

    private fun showDialog() {
        loadingUpload.setContentView(R.layout.bg_loading_auth)
        loadingUpload.setCancelable(false)
        loadingUpload.setCanceledOnTouchOutside(false)
        loadingUpload.show()
    }

    companion object{
        const val TYPE_POST = "type_post"
        private const val TAG = "addPostActivity"

        private const val REQUEST_PERMISSION = Manifest.permission.CAMERA
    }
}