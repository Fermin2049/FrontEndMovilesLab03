package com.fermin2049.proyectofinallab3.ui.profile;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.Propietario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    private MutableLiveData<Propietario> propietarioLiveData;
    private MutableLiveData<Boolean> updateStatusLiveData;

    public ProfileViewModel() {
        propietarioLiveData = new MutableLiveData<>();
        updateStatusLiveData = new MutableLiveData<>();
    }

    public LiveData<Propietario> getPropietario() {
        return propietarioLiveData;
    }

    public LiveData<Boolean> getUpdateStatus() {
        return updateStatusLiveData;
    }

    public void fetchPropietarioDetails(Context context) {
        RetrofitClient.InmobliariaService service = RetrofitClient.getInmobiliariaService(context);
        Call<Propietario> call = service.getMyDetails();
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Propietario propietario = response.body();
                    // Adjust the photo URL
                    propietario.setFotoPerfil("http://192.168.1.2:5157/" + propietario.getFotoPerfil().replace("\\", "/"));
                    propietarioLiveData.setValue(propietario);
                } else {
                    Log.d(TAG, "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d(TAG, "Error fetching propietario details: " + t.getMessage());
            }
        });
    }

    public void updatePropietario(Context context, Propietario propietario) {
        RetrofitClient.InmobliariaService service = RetrofitClient.getInmobiliariaService(context);
        Call<Propietario> call = service.updatePropietarioWithImage(propietario.getIdPropietario(), propietario, null);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateStatusLiveData.setValue(true);
                } else {
                    updateStatusLiveData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d(TAG, "Error updating profile: " + t.getMessage());
                updateStatusLiveData.setValue(false);
            }
        });
    }
}