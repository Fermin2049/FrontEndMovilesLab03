package com.fermin2049.proyectofinallab3.ui.property;

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
import com.fermin2049.proyectofinallab3.models.Inmueble;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInmuebleViewModel extends AndroidViewModel {

    private final MutableLiveData<Uri> imageUri = new MutableLiveData<>();

    public AddInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri uri) {
        imageUri.setValue(uri);
    }

    public void addInmueble(String direccion, String uso, String tipo, int ambientes, double precio, String estado, Context context) {
        int idPropietario = RetrofitClient.getPropietarioIdFromToken(context);
        if (idPropietario == -1) {
            Log.d("AddInmuebleViewModel", "Error: Invalid propietarioId");
            return;
        }

        RetrofitClient.InmobliariaService api = RetrofitClient.getInmobiliariaService(context);

        RequestBody direccionBody = RequestBody.create(MediaType.parse("text/plain"), direccion);
        RequestBody usoBody = RequestBody.create(MediaType.parse("text/plain"), uso);
        RequestBody tipoBody = RequestBody.create(MediaType.parse("text/plain"), tipo);
        RequestBody ambientesBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ambientes));
        RequestBody precioBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(precio));
        RequestBody estadoBody = RequestBody.create(MediaType.parse("text/plain"), estado);
        RequestBody idPropietarioBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idPropietario));

        MultipartBody.Part imagenPart = null;
        Uri uri = imageUri.getValue();
        if (uri != null) {
            String filePath = getFilePathFromUri(context, uri);
            if (filePath != null) {
                File file = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                imagenPart = MultipartBody.Part.createFormData("imagen", file.getName(), requestFile);
            }
        }

        Call<Inmueble> call = api.addInmueble(direccionBody, usoBody, tipoBody, ambientesBody, precioBody, estadoBody, idPropietarioBody, imagenPart);
        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(@NonNull Call<Inmueble> call, @NonNull Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Log.d("AddInmuebleViewModel", "Inmueble added successfully");
                } else {
                    Log.d("AddInmuebleViewModel", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Inmueble> call, @NonNull Throwable t) {
                Log.d("AddInmuebleViewModel", "API call failed: " + t.getMessage());
            }
        });
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
                    Log.d("AddInmuebleViewModel", "File copied to: " + file.getAbsolutePath());
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