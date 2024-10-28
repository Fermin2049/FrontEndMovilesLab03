package com.fermin2049.proyectofinallab3;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void llamarLogin(String email, String password) {
        RetrofitClient.InmobliariaService api = RetrofitClient.getInmobiliariaService(getApplication());
        Call<LoginResponse> call = api.login(email, password);
        Log.d("LoginViewModel", "Calling API with email: " + email);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Log.d("LoginViewModel", "Login successful: " + loginResponse.getToken());
                    saveToken(loginResponse.getToken());
                    startMainActivity();
                } else {
                    Log.d("LoginViewModel", "Login failed: " + response.code() + " - " + response.message());
                    Toast.makeText(getApplication(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable throwable) {
                Log.d("LoginViewModel", "API call failed: " + throwable.getMessage());
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void llamarRegistro(String username, String email, String password) {
        // Lógica para registrar al usuario (similar a llamarLogin)
        // Puedes crear una llamada a tu API de registro aquí
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt_token", token);
        editor.apply();
        Log.d("LoginViewModel", "Token saved: " + token);
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }
}
