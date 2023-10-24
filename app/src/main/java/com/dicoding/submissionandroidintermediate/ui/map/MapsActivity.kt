package com.dicoding.submissionandroidintermediate.ui.map

import android.app.Dialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.data.Result
import com.dicoding.submissionandroidintermediate.data.local.entity.StoryEntity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.dicoding.submissionandroidintermediate.databinding.ActivityMapsBinding
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var loadingMap: Dialog
    private val mapsViewModel by viewModels<MapsActivityViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted: Boolean->
        if (isGranted) {
            getMyLocation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        loadingMap = Dialog(this)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()

        mapsViewModel.getUserSession().observe(this){user->
            mapsViewModel.getAllStoriesWithLocation(user.token).observe(this){result->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showDialog()
                        }

                        is Result.Success -> {
                            dismissLoading()
                            setUpDataMarker(result.data)
                        }

                        is Result.Error -> {
                            dismissLoading()
                            Log.d("MapsActivity", "onViewCreated: ${result.error}")
                        }
                    }
                }
            }
        }
    }

    private fun setUpDataMarker(data: List<StoryEntity>) {
        data.forEach { data->
            if(data.lat !=null && data.lon !=null){
                val latLng = LatLng(data.lat!!, data.lon!!)
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(data.name)
                        .snippet(data.description)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                )
            }
        }
    }


    private fun dismissLoading() {
        if(loadingMap.isShowing){
            loadingMap.dismiss()
        }
    }

    private fun showDialog() {
        loadingMap.setContentView(R.layout.bg_loading_auth)
        loadingMap.setCancelable(false)
        loadingMap.setCanceledOnTouchOutside(false)
        loadingMap.show()
    }


    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}