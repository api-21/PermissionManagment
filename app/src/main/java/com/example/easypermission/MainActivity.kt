package com.example.easypermission

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.easypermission.databinding.ActivityMainBinding
import com.example.easypermission.databinding.PermissionBtnBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var btnCamera: PermissionBtnBinding
    lateinit var btnContact: PermissionBtnBinding
    lateinit var btnNetwork: PermissionBtnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        btnCamera = PermissionBtnBinding.inflate(layoutInflater)
        btnContact = PermissionBtnBinding.inflate(layoutInflater)
        btnNetwork = PermissionBtnBinding.inflate(layoutInflater)

        binding.mainlyt.apply {
            addView(btnCamera.btnPermission)
        }

        btnCamera.btnPermission.apply {
            text = "Allow Permission"
            setOnClickListener {
                requestPermission()
            }
        }

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