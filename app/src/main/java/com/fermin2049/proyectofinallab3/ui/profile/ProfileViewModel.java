package com.fermin2049.proyectofinallab3.ui.profile;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.Propietario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {

    private final MutableLiveData<Propietario> propietarioLiveData = new MutableLiveData<>();
    private final MutableLiveData<Uri> uriMutableLiveData = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getPropietario() {
        return propietarioLiveData;
    }

    public LiveData<Uri> getUriMutable() {
        return uriMutableLiveData;
    }

    public void actualizarPropietario(Propietario propietario, Uri fotoUri, Context context) {
        if (propietario.getIdPropietario() == 0) {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error de Actualización")
                    .setContentText("IdPropietario es 0, no se puede actualizar.")
                    .show();
            return;
        }

        RetrofitClient.InmobliariaService api = RetrofitClient.getInmobiliariaService(context);

        RequestBody idPropietario = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(propietario.getIdPropietario()));
        RequestBody dni = RequestBody.create(MediaType.parse("text/plain"), propietario.getDni());
        RequestBody apellido = RequestBody.create(MediaType.parse("text/plain"), propietario.getApellido());
        RequestBody nombre = RequestBody.create(MediaType.parse("text/plain"), propietario.getNombre());
        RequestBody telefono = RequestBody.create(MediaType.parse("text/plain"), propietario.getTelefono());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), propietario.getEmail());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), propietario.getPassword());

        MultipartBody.Part fotoPerfilPart = null;
        if (fotoUri != null) {
            String filePath = getFilePathFromUri(context, fotoUri);
            Log.d("ProfileViewModel", "File path from URI: " + filePath);
            if (filePath != null) {
                File file = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                fotoPerfilPart = MultipartBody.Part.createFormData("fotoPerfil", file.getName(), requestFile);
            }
        }

        Call<Propietario> call = api.updatePropietarioWithImage(
                propietario.getIdPropietario(), idPropietario, dni, apellido, nombre, telefono, email, password, fotoPerfilPart
        );

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                if (response.isSuccessful()) {
                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Actualización Exitosa")
                            .setContentText("El perfil se ha actualizado correctamente.")
                            .show();
                } else {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error de Actualización")
                            .setContentText("Ocurrió un error al actualizar el perfil.")
                            .show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error de Actualización")
                        .setContentText("Ocurrió un error al actualizar el perfil.")
                        .show();
            }
        });
    }

    public void fetchPropietarioDetails(Context context) {
        RetrofitClient.getInmobiliariaService(context).getMyDetails().enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Propietario propietario = response.body();
                    propietarioLiveData.setValue(propietario);

                    if (propietario.getFotoPerfil() != null) {
                        String fullUrl = RetrofitClient.getBaseUrl().replace("/api", "") + propietario.getFotoPerfil();
                        Log.d("ProfileViewModel", "Full URL: " + fullUrl);
                        uriMutableLiveData.setValue(Uri.parse(fullUrl));
                    }
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void recibirFoto(Uri uri) {
        Log.d("ProfileViewModel", "Received URI: " + uri.toString());
        uriMutableLiveData.setValue(uri);
    }

    private String getFilePathFromUri(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (columnIndex != -1) {
                String displayName = cursor.getString(columnIndex);
                File file = new File(context.getCacheDir(), displayName);
                try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
                     OutputStream outputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    Log.d("ProfileViewModel", "File copied to: " + file.getAbsolutePath());
                    return file.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return null;
    }
}