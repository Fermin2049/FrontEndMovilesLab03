package com.fermin2049.proyectofinallab3.login;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.fermin2049.proyectofinallab3.api.RetrofitClient;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverPasswordViewModel extends AndroidViewModel {
    public MutableLiveData<Boolean> solicitudEnviada = new MutableLiveData<>();

    public RecoverPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public void solicitarRestablecimiento(String email) {
        RetrofitClient.getInmobiliariaService(getApplication().getApplicationContext())
                .solicitarRestablecimiento(email)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            solicitudEnviada.setValue(true);
                            Toast.makeText(getApplication(), "Solicitud enviada", Toast.LENGTH_SHORT).show();
                        } else {
                            solicitudEnviada.setValue(false);
                            Toast.makeText(getApplication(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        solicitudEnviada.setValue(false);
                        Toast.makeText(getApplication(), "Error de red", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void observarSolicitudEnviada(AppCompatActivity activity) {
        solicitudEnviada.observe(activity, enviada -> {
            if (enviada) {
                new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("¡Éxito!")
                    .setContentText("Solicitud enviada con éxito.")
                    .show();
            } else {
                new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("¡Error!")
                    .setContentText("Error al enviar la solicitud.")
                    .show();
            }
        });
    }
}