package com.example.sumit.goglatlon;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener {
 private GoogleMap mMap;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
 * * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //The below method is provided by the Googlemaps when craeted for the first time
    @Override
    public void onMapReady(GoogleMap googleMap) {
     mMap = googleMap;
  // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(12.9716, 77.5946);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Bangalore"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(this);//calling the  onMapClickListener for this activity
  }
  @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();//clear al the coordinates
        Geocoder geocoder;//Declare Geocoder object
        List<Address> addresses;//Usinf list of address
        geocoder = new Geocoder(this, Locale.getDefault());//Geocoder trying to use locale as the US language
        try {
            //Storing all the location information in the "addresses variable" through the help of lat and lng
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String locationTxt=address + " " + city + " " + country;
                mMap.addMarker(new MarkerOptions()
                        .position(latLng).title(locationTxt)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                        .draggable(true));//We are adding the marker along wih the position, icon to display address and movable dragging marker
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));//Will move the camera and CameraUpdateFactory class will invoke  nerLatlng method to update the camera
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));//animate the camera witeh some graphic effects
                mMap.setOnMarkerDragListener(this);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void onMarkerDragStart(Marker marker) {}
    @Override
    public void onMarkerDrag(Marker marker) {}
    @Override
    public void onMarkerDragEnd(Marker marker) {
      mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}
