package com.fermin2049.proyectofinallab3;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fermin2049.proyectofinallab3.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void llamarLogin(String email, String password) {
        RetrofitClient.InmobliariaService api = RetrofitClient.getInmobiliariaService();
        Call<String> call = api.login(email, password);
        Log.d("LoginViewModel", "Calling API with email: " + email);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.d("LoginViewModel", "Login successful: " + response.body());
                    Toast.makeText(getApplication(), "Login exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("LoginViewModel", "Login failed: " + response.code() + " - " + response.message());
                    Toast.makeText(getApplication(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                Log.d("LoginViewModel", "API call failed: " + throwable.getMessage());
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}