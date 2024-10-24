package com.fermin2049.proyectofinallab3.ui.pay;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.api.RetrofitClient.InmobliariaService;
import com.fermin2049.proyectofinallab3.models.Pago;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Pago>> pagosLiveData;

    public PayViewModel(Application application) {
        super(application);
        pagosLiveData = new MutableLiveData<>();
        fetchPagos(application.getApplicationContext());
    }

    public LiveData<List<Pago>> getPagos() {
        return pagosLiveData;
    }

    private void fetchPagos(Context context) {
    InmobliariaService service = RetrofitClient.getInmobiliariaService(context);
    Call<List<Pago>> call = service.getPagos();
    call.enqueue(new Callback<List<Pago>>() {
        @Override
        public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
            if (response.isSuccessful() && response.body() != null) {
                pagosLiveData.setValue(response.body());
            } else {
                // Log error or handle the case where response is not successful
                Log.e("PayViewModel", "Error in response: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Pago>> call, Throwable t) {
            // Handle failure
            Log.e("PayViewModel", "API call failed", t);
        }
    });
}
}