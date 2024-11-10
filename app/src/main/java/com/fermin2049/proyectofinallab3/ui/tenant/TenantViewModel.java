package com.fermin2049.proyectofinallab3.ui.tenant;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.Inquilino;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TenantViewModel extends AndroidViewModel {

    private static final String TAG = "TenantViewModel";
    private final MutableLiveData<List<Inquilino>> tenantLiveData;

    public TenantViewModel(@NonNull Application application) {
        super(application);
        tenantLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Inquilino>> getInquilinos() {
        return tenantLiveData;
    }

    public void fetchInquilinosByPropietarioId() {
        int propietarioId = RetrofitClient.getPropietarioIdFromToken(getApplication());
        if (propietarioId == -1) {
            Log.d(TAG, "Error: Invalid propietarioId");
            return;
        }

        RetrofitClient.InmobliariaService service = RetrofitClient.getInmobiliariaService(getApplication());
        Call<List<Inquilino>> call = service.getInquilinosByPropietarioId(propietarioId);
        call.enqueue(new Callback<List<Inquilino>>() {
            @Override
            public void onResponse(Call<List<Inquilino>> call, Response<List<Inquilino>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API call successful. Data received: " + response.body().size() + " tenants.");
                    tenantLiveData.setValue(response.body());
                } else {
                    Log.d(TAG, "API call unsuccessful. Response code: " + response.code());
                    try {
                        Log.d(TAG, "Response error body: " + response.errorBody().string());
                    } catch (Exception e) {
                        Log.d(TAG, "Error reading response error body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Inquilino>> call, Throwable t) {
                Log.d(TAG, "API call failed. Error: " + t.getMessage());
            }
        });
    }
}