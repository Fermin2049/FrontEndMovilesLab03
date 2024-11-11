package com.fermin2049.proyectofinallab3.ui.tenant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.fermin2049.proyectofinallab3.databinding.FragmentInquilinoDetailBinding;
import com.fermin2049.proyectofinallab3.models.Inquilino;

public class InquilinoDetailFragment extends Fragment {

    private FragmentInquilinoDetailBinding binding;
    private InquilinoDetailViewModel inquilinoDetailViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInquilinoDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inquilinoDetailViewModel = new ViewModelProvider(this).get(InquilinoDetailViewModel.class);

        // Obtener el inquilino de los argumentos y pasarlo al ViewModel
        Inquilino inquilino = (Inquilino) getArguments().getSerializable("inquilino");
        inquilinoDetailViewModel.setInquilino(inquilino);

        // Observa los datos de inquilino
        inquilinoDetailViewModel.getInquilinoLiveData().observe(getViewLifecycleOwner(), this::displayInquilinoDetails);

        return root;
    }

    private void displayInquilinoDetails(Inquilino inquilino) {
        binding.textViewNombreCompleto.setText(inquilino.getNombreCompleto());
        binding.textViewDni.setText(inquilino.getDni());
        binding.textViewTelefono.setText(inquilino.getTelefono());
        binding.textViewEmail.setText(inquilino.getEmail());
        binding.textViewLugarTrabajo.setText(inquilino.getLugarTrabajo());
        binding.textViewNombreGarante.setText(inquilino.getNombreGarante());
        binding.textViewDniGarante.setText(inquilino.getDniGarante());
    }
}