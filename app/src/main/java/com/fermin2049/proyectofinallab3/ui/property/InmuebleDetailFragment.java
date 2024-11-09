package com.fermin2049.proyectofinallab3.ui.property;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.fermin2049.proyectofinallab3.databinding.FragmentInmuebleDetailBinding;
import com.fermin2049.proyectofinallab3.models.Inmueble;

public class InmuebleDetailFragment extends Fragment {

    private FragmentInmuebleDetailBinding binding;
    private InmuebleDetailViewModel inmuebleDetailViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInmuebleDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inmuebleDetailViewModel = new ViewModelProvider(this).get(InmuebleDetailViewModel.class);

        if (getArguments() != null) {
            Inmueble inmueble = (Inmueble) getArguments().getSerializable("inmueble");
            inmuebleDetailViewModel.fetchInmuebleDetails(inmueble);
        }

        inmuebleDetailViewModel.getInmuebleLiveData().observe(getViewLifecycleOwner(), this::displayInmuebleDetails);

        binding.switchEstado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Inmueble inmueble = inmuebleDetailViewModel.getInmuebleLiveData().getValue();
            if (inmueble != null) {
                String nuevoEstado = isChecked ? "Disponible" : "Ocupado";
                inmuebleDetailViewModel.updateEstadoInmueble(inmueble.getIdInmueble(), nuevoEstado, getContext());
            }
        });

        return root;
    }

    private void displayInmuebleDetails(Inmueble inmueble) {
        binding.textViewDireccion.setText(inmueble.getDireccion());
        binding.textViewUso.setText(inmueble.getUso());
        binding.textViewTipo.setText(inmueble.getTipo());
        binding.textViewAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
        binding.textViewPrecio.setText(String.valueOf(inmueble.getPrecio()));
        binding.switchEstado.setText(inmueble.getEstado());
        binding.switchEstado.setChecked("Disponible".equals(inmueble.getEstado()));

        String imageUrl = RetrofitClient.getBaseUrl().replace("/api", "") + inmueble.getImagen();
        Glide.with(this)
                .load(imageUrl)
                .into(binding.imageViewInmueble);
    }
}