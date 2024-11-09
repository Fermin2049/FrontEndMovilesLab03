package com.fermin2049.proyectofinallab3.ui.property;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.Inmueble;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetailViewModel extends AndroidViewModel {

    private final MutableLiveData<Inmueble> inmuebleLiveData = new MutableLiveData<>();

    public InmuebleDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Inmueble> getInmuebleLiveData() {
        return inmuebleLiveData;
    }

    public void fetchInmuebleDetails(Inmueble inmueble) {
        inmuebleLiveData.setValue(inmueble);
    }

    public void updateEstadoInmueble(int id, String nuevoEstado, Context context) {
        RetrofitClient.InmobliariaService api = RetrofitClient.getInmobiliariaService(context);
        Call<Void> call = api.updateEstadoInmueble(id, nuevoEstado);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Inmueble updatedInmueble = inmuebleLiveData.getValue();
                    if (updatedInmueble != null) {
                        updatedInmueble.setEstado(nuevoEstado);
                        inmuebleLiveData.setValue(updatedInmueble);
                    }
                } else {
                    Log.d("InmuebleDetailViewModel", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("InmuebleDetailViewModel", "API call failed: " + t.getMessage());
            }
        });
    }
}