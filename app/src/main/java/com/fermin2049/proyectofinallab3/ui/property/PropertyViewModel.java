package com.fermin2049.proyectofinallab3.ui.property;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.Inmueble;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Inmueble>> inmuebles;

    public PropertyViewModel(@NonNull Application application) {
        super(application);
        inmuebles = new MutableLiveData<>();
    }

    public LiveData<List<Inmueble>> getInmuebles() {
        return inmuebles;
    }

    public void fetchInmueblesByPropietarioId() {
        int propietarioId = RetrofitClient.getPropietarioIdFromToken(getApplication());
        if (propietarioId == -1) {
            Log.d("PropertyViewModel", "Error: Invalid propietarioId");
            return;
        }

        RetrofitClient.InmobliariaService api = RetrofitClient.getInmobiliariaService(getApplication());
        Call<List<Inmueble>> call = api.getInmueblesByPropietarioId(propietarioId);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(@NonNull Call<List<Inmueble>> call, @NonNull Response<List<Inmueble>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("PropertyViewModel", "Inmuebles fetched: " + response.body().size());
                    inmuebles.postValue(response.body());
                } else {
                    Log.d("PropertyViewModel", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Inmueble>> call, @NonNull Throwable t) {
                Log.d("PropertyViewModel", "API call failed: " + t.getMessage());
            }
        });
    }
}