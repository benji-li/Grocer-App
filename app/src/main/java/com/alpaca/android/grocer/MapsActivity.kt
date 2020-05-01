package com.alpaca.android.grocer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback  {

    private lateinit var map: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val johann = LatLng(49.2637, -123.15)
        map.addMarker(MarkerOptions().position(johann).title("Johann, 2 Milk, $3 tip!"))
        map.moveCamera(CameraUpdateFactory.newLatLng(johann))
        val hunter = LatLng(49.2583, -123.22)
        map.addMarker(MarkerOptions().position(hunter).title("Hunter, 5 Steaks, $2.50 tip!"))
        val other = LatLng(49.219, -123.126)
        map.addMarker(MarkerOptions().position(other).title("Maggie, 6 bananas, $1 tip!"))
        val other1 = LatLng(49.26, -123.249)
        map.addMarker(MarkerOptions().position(other1).title("David, 1 bag curry powder, $1 tip!"))
        val other2 = LatLng(49.243,-123.064)
        map.addMarker(MarkerOptions().position(other2))
        val other3 = LatLng(49.212,-123.101)
        map.addMarker(MarkerOptions().position(other3))
        map.uiSettings.isZoomControlsEnabled = true
        map.moveCamera(CameraUpdateFactory.newLatLng(johann))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(johann, 12.0f))

    }

}
