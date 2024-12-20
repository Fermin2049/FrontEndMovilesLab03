package com.fermin2049.proyectofinallab3.ui.contract;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.Contract;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractViewModel extends AndroidViewModel {

    private static final String TAG = "ContractViewModel";
    private final MutableLiveData<List<Contract>> contratosLiveData;

    public ContractViewModel(@NonNull Application application) {
        super(application);
        contratosLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Contract>> getContratos() {
        return contratosLiveData;
    }

    public void fetchContratosByPropietarioId() {
        int propietarioId = RetrofitClient.getPropietarioIdFromToken(getApplication());
        if (propietarioId == -1) {
            Log.d(TAG, "Error: Invalid propietarioId");
            return;
        }

        RetrofitClient.InmobliariaService service = RetrofitClient.getInmobiliariaService(getApplication());
        Call<List<Contract>> call = service.getContratosByPropietarioId(propietarioId);
        call.enqueue(new Callback<List<Contract>>() {
            @Override
            public void onResponse(Call<List<Contract>> call, Response<List<Contract>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API call successful. Data received: " + response.body().size() + " contracts.");
                    contratosLiveData.setValue(response.body());
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
            public void onFailure(Call<List<Contract>> call, Throwable t) {
                Log.d(TAG, "API call failed. Error: " + t.getMessage());
            }
        });
    }
}