package com.fermin2049.proyectofinallab3.ui.profile;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.api.RetrofitClient.InmobliariaService;
import com.fermin2049.proyectofinallab3.models.Property;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {

    private final MutableLiveData<Property> propietarioLiveData;

    public ProfileViewModel(Application application) {
        super(application);
        propietarioLiveData = new MutableLiveData<>();
        fetchPropietarioDetails(application.getApplicationContext());
    }

    public LiveData<Property> getPropietario() {
        return propietarioLiveData;
    }

    private void fetchPropietarioDetails(Context context) {
        InmobliariaService service = RetrofitClient.getInmobiliariaService(context);
        Call<Property> call = service.getMyDetails();
        call.enqueue(new Callback<Property>() {
            @Override
            public void onResponse(Call<Property> call, Response<Property> response) {
                if (response.isSuccessful() && response.body() != null) {
                    propietarioLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Property> call, Throwable t) {
                // Handle failure
            }
        });
    }
}