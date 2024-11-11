package com.fermin2049.proyectofinallab3.ui.property;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.models.Contract;
import com.fermin2049.proyectofinallab3.models.Inmueble;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetailViewModel extends AndroidViewModel {

    private final MutableLiveData<Inmueble> inmuebleLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Contract>> contratosLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> revertSwitchLiveData = new MutableLiveData<>(false);

    public InmuebleDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Inmueble> getInmuebleLiveData() {
        return inmuebleLiveData;
    }

    public MutableLiveData<Boolean> getRevertSwitchLiveData() {
        return revertSwitchLiveData;
    }

    public void setInmueble(Inmueble inmueble) {
        inmuebleLiveData.setValue(inmueble);
        fetchContratosByPropietarioId();
    }

    private void fetchContratosByPropietarioId() {
        int propietarioId = RetrofitClient.getPropietarioIdFromToken(getApplication());
        if (propietarioId == -1) {
            Log.d("InmuebleDetailViewModel", "Error: Invalid propietarioId");
            return;
        }

        RetrofitClient.InmobliariaService service = RetrofitClient.getInmobiliariaService(getApplication());
        Call<List<Contract>> call = service.getContratosByPropietarioId(propietarioId);
        call.enqueue(new Callback<List<Contract>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contract>> call, @NonNull Response<List<Contract>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contratosLiveData.setValue(response.body());
                } else {
                    Log.d("InmuebleDetailViewModel", "API call unsuccessful. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Contract>> call, @NonNull Throwable t) {
                Log.d("InmuebleDetailViewModel", "API call failed: " + t.getMessage());
            }
        });
    }

    public void onEstadoSwitchChanged(boolean isChecked, Context context) {
        Inmueble inmueble = inmuebleLiveData.getValue();
        if (inmueble == null) return;

        List<Contract> contratos = contratosLiveData.getValue();
        if (contratos != null && contratoExistente(inmueble, contratos)) {
            mostrarAlertaContratoVigente(context);
            revertSwitchLiveData.setValue(true); // Revertir el estado del switch en la vista
            return;
        }

        String nuevoEstado = isChecked ? "Disponible" : "Ocupado";
        updateEstadoInmueble(inmueble.getIdInmueble(), nuevoEstado, context);
    }

    private boolean contratoExistente(Inmueble inmueble, List<Contract> contratos) {
        for (Contract contrato : contratos) {
            if (contrato.getIdInmueble() == inmueble.getIdInmueble()) {
                return true;
            }
        }
        return false;
    }

    private void mostrarAlertaContratoVigente(Context context) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Inmueble con Contrato")
                .setContentText("El inmueble tiene un contrato vigente y no se puede cambiar el estado.")
                .setConfirmText("OK")
                .show();
    }

    private void updateEstadoInmueble(int id, String nuevoEstado, Context context) {
        RetrofitClient.InmobliariaService api = RetrofitClient.getInmobiliariaService(context);
        Call<Void> call = api.updateEstadoInmueble(id, nuevoEstado);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Inmueble updatedInmueble = inmuebleLiveData.getValue();
                    if (updatedInmueble != null) {
                        updatedInmueble.setEstado(nuevoEstado);
                        inmuebleLiveData.setValue(updatedInmueble);
                    }
                } else {
                    Log.d("InmuebleDetailViewModel", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("InmuebleDetailViewModel", "API call failed: " + t.getMessage());
            }
        });
    }
}
