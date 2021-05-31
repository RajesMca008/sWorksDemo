package com.raj.mymapdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.raj.mymapdemo.databinding.ActivityMapsBinding
import com.raj.mymapdemo.model.MapsActivityModel
import com.raj.mymapdemo.model.Order


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val PHONE_REMISSION_REQ_CODE= 1232

    companion object{
        var isPhonePermissionGranted =false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val viewModel = ViewModelProvider(this).get(MapsActivityModel::class.java)
        viewModel.getOrdersList(this)
        viewModel.orders.observe(this, Observer { orders ->
            updateLocations(orders)

        })
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CALL_PHONE),
                PHONE_REMISSION_REQ_CODE
            )
        } else {
            isPhonePermissionGranted= true
        }
    }


    private fun updateLocations(orders: List<Order>?) {

        if (this::mMap.isInitialized) {
            for (item in orders!!) {
                // Add a marker in Sydney and move the camera
                item.location?.let {
                    val location = it.long?.let { it1 -> it.lat?.let { it2 -> LatLng(it2, it1) } }
                    mMap.addMarker(MarkerOptions().position(location).title(item.name))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
                }
            }
            //Update on List
            binding.recyclerView.adapter = OrderAdapter(orders,applicationContext)

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PHONE_REMISSION_REQ_CODE -> isPhonePermissionGranted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            else -> {
            }
        }
    }
}