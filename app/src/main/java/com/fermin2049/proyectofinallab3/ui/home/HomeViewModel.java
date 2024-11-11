package com.fermin2049.proyectofinallab3.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<MapaActual> mMapaActual;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MapaActual> getMMapaActual() {
        if (mMapaActual == null) {
            mMapaActual = new MutableLiveData<>();
        }
        return mMapaActual;
    }

    public void obtenerMapa() {
        MapaActual mapaActual = new MapaActual();
        mMapaActual.setValue(mapaActual);
    }

    public class MapaActual implements OnMapReadyCallback {

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            // Coordinates for San Luis Capital, postal code 5700
            LatLng location = new LatLng(-33.301726, -66.337752); // Example coordinates for San Luis Capital

            // Add a marker on the map
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Inmobiliaria Cuyo")
                    .snippet("San Luis Capital, CP 5700"));

            // Move the camera to the marker
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
        }
    }
}