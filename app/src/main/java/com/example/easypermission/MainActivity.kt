package com.example.easypermission

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.easypermission.databinding.ActivityMainBinding
import com.example.easypermission.databinding.PermissionBtnBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var btnCamera: PermissionBtnBinding
    lateinit var btnContact: PermissionBtnBinding
    lateinit var btnNetwork: PermissionBtnBinding
    lateinit var btnGetLocation: PermissionBtnBinding

    private var locationManager:FusedLocationProviderClient?=null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        btnCamera = PermissionBtnBinding.inflate(layoutInflater)
        btnContact = PermissionBtnBinding.inflate(layoutInflater)
        btnNetwork = PermissionBtnBinding.inflate(layoutInflater)
        btnGetLocation = PermissionBtnBinding.inflate(layoutInflater)


        binding.mainlyt.addView(btnGetLocation.root)

        locationManager = LocationServices.getFusedLocationProviderClient(this)

        btnGetLocation.btnPermission.apply {
            text ="Get Location"
            setOnClickListener {
                locationManager!!.lastLocation.addOnSuccessListener {
                    it?.let {

                Toast.makeText(this@MainActivity, "${it.longitude}, ${it.latitude}", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        requestPermission()

        binding.mainlyt.apply {
            addView(btnCamera.btnPermission)
        }

        btnCamera.btnPermission.apply {


            text = "Allow Permission"
            setOnClickListener {
//                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0L,0f,locationListener)
                requestPermission()
            }
        }

    }

    val locationListener : LocationListener = LocationListener {

        Toast.makeText(this, " Latitude: ${it.latitude}, Longitude: ${it.longitude}", Toast.LENGTH_SHORT).show()

      //  Log.e("UserLocation", ":", )
    }

    private fun onCameraPermissionGranted() = ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun onReadExternalStoragePermissionGrant() = ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun onLocationPermissionGranted() = ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    fun hasACCESS_COARSE_LOCATIONPermission() = ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    fun hasACCESS_FINE_LOCATIONPermission() = ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED


    private fun requestPermission(){
        val permissionToRequest = mutableListOf<String>()
        if (!onCameraPermissionGranted()){
            permissionToRequest.add(android.Manifest.permission.CAMERA)
        }
        if (!onReadExternalStoragePermissionGrant()){
            permissionToRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!onLocationPermissionGranted()){
            permissionToRequest.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (!hasACCESS_COARSE_LOCATIONPermission()) permissionToRequest.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (!hasACCESS_FINE_LOCATIONPermission()) permissionToRequest.add(android.Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permissionToRequest.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "$permissions", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}