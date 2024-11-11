package com.fermin2049.proyectofinallab3.ui.tenant;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.Inquilino;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoDetailViewModel extends AndroidViewModel {

    private final MutableLiveData<Inquilino> inquilinoLiveData = new MutableLiveData<>();

    public InquilinoDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Inquilino> getInquilinoLiveData() {
        return inquilinoLiveData;
    }

    public void setInquilino(Inquilino inquilino) {
        inquilinoLiveData.setValue(inquilino);
        fetchInquilinoDetails(inquilino.getIdInquilino());
    }

    private void fetchInquilinoDetails(int idInquilino) {
        RetrofitClient.InmobliariaService service = RetrofitClient.getInmobiliariaService(getApplication());
        Call<Inquilino> call = service.getInquilinoById(idInquilino);
        call.enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(@NonNull Call<Inquilino> call, @NonNull Response<Inquilino> response) {
                if (response.isSuccessful() && response.body() != null) {
                    inquilinoLiveData.setValue(response.body());
                } else {
                    Log.d("InquilinoDetailViewModel", "API call unsuccessful. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Inquilino> call, @NonNull Throwable t) {
                Log.d("InquilinoDetailViewModel", "API call failed: " + t.getMessage());
            }
        });
    }
}