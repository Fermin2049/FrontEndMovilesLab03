package com.fermin2049.proyectofinallab3.login;

import android.app.Application;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.RestablecerContrasenaRequest;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordViewModel extends AndroidViewModel {

    private String token;
    private int idPropietario;
    private MutableLiveData<Result> passwordChangeResult = new MutableLiveData<>();

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Result> getPasswordChangeResult() {
        return passwordChangeResult;
    }

    public void processUri(Uri uri) {
        token = uri.getQueryParameter("token");

        // Verifica si el segmento anterior al último es un número válido
        String[] pathSegments = uri.getPath().split("/");
        if (pathSegments.length >= 2) {
            try {
                idPropietario = Integer.parseInt(pathSegments[pathSegments.length - 2]);
            } catch (NumberFormatException e) {
                new SweetAlertDialog(getApplication(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("Error parsing ID from URI")
                    .show();
            }
        }
    }

    public void resetPassword(String newPassword, String confirmPassword) {
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            new SweetAlertDialog(getApplication(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error")
                .setContentText("Por favor, complete todos los campos")
                .show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            new SweetAlertDialog(getApplication(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error")
                .setContentText("Las contraseñas no coinciden")
                .show();
            return;
        }

        RestablecerContrasenaRequest request = new RestablecerContrasenaRequest(token, newPassword);

        RetrofitClient.getInmobiliariaService(getApplication()).restablecerContrasena(idPropietario, request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    passwordChangeResult.setValue(new Result(true, null));
                } else {
                    passwordChangeResult.setValue(new Result(false, "Error al cambiar la contraseña"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                passwordChangeResult.setValue(new Result(false, "Error de red"));
            }
        });
    }

    public void handlePasswordChangeResult(AppCompatActivity activity) {
        passwordChangeResult.observe(activity, result -> {
            if (result.isSuccess()) {
                new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Éxito")
                    .setContentText("Contraseña cambiada exitosamente")
                    .show();
            } else {
                new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText(result.getErrorMessage())
                    .show();
            }
        });
    }

    public static class Result {
        private boolean success;
        private String errorMessage;

        public Result(boolean success, String errorMessage) {
            this.success = success;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}