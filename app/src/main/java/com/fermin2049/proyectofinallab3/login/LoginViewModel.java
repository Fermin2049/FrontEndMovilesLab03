package com.fermin2049.proyectofinallab3.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.fermin2049.proyectofinallab3.MainActivity;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.LoginResponse;
import com.fermin2049.proyectofinallab3.models.Propietario;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    public MutableLiveData<Boolean> registroExitoso = new MutableLiveData<>();
    public MutableLiveData<Void> limpiarCampos = new MutableLiveData<>();
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void llamarLogin(String email, String password) {
        RetrofitClient.InmobliariaService api = RetrofitClient.getInmobiliariaService(getApplication());
        Call<LoginResponse> call = api.login(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (response.isSuccessful() && response.body() != null) {
                        saveToken(response.body().getToken());
                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Login Successful")
                            .setContentText("Welcome!")
                            .show();
                        new Handler().postDelayed(() -> startMainActivity(), 3000); // 3 seconds delay
                    } else {
                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Login Failed")
                            .setContentText("Invalid credentials")
                            .show();
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Login Failed")
                        .setContentText("An error occurred")
                        .show();
                });
            }
        });
    }

    public void llamarRegistro(String dni, String apellido, String nombre, String telefono, String email, String password) {
        RetrofitClient.InmobliariaService api = RetrofitClient.getInmobiliariaService(getApplication());
        Propietario propietario = new Propietario(0, dni, apellido, nombre, telefono, email, password, null, null);
        Call<Propietario> call = api.register(propietario);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (response.isSuccessful() && response.body() != null) {
                        registroExitoso.postValue(true);
                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Registration Successful")
                            .setContentText("Welcome!")
                            .show();
                        new Handler().postDelayed(() -> startMainActivity(), 3000); // 3 seconds delay
                    } else {
                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Registration Failed")
                            .setContentText("An error occurred")
                            .show();
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Registration Failed")
                        .setContentText("An error occurred")
                        .show();
                });
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt_token", token);
        editor.apply();
    }

    private void startMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}