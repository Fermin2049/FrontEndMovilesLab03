package com.fermin2049.proyectofinallab3.ui.tenant;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.api.RetrofitClient.InmobliariaService;
import com.fermin2049.proyectofinallab3.models.Inquilino;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TenantViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Inquilino>> tenantLiveData;

    public TenantViewModel(Application application) {
        super(application);
        tenantLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Inquilino>> getInquilinos() {
        return tenantLiveData;
    }

    public void fetchInquilinosByPropietarioId(int propietarioId) {
        InmobliariaService service = RetrofitClient.getInmobiliariaService(getApplication());
        Call<List<Inquilino>> call = service.getInquilinosByPropietarioId(propietarioId);
        call.enqueue(new Callback<List<Inquilino>>() {
            @Override
            public void onResponse(Call<List<Inquilino>> call, Response<List<Inquilino>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tenantLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Inquilino>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}