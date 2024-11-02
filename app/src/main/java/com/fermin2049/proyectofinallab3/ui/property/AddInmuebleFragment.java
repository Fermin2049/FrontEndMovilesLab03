package com.fermin2049.proyectofinallab3.ui.property;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fermin2049.proyectofinallab3.databinding.FragmentAddInmuebleBinding;

public class AddInmuebleFragment extends Fragment {

    private FragmentAddInmuebleBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Aquí puedes configurar los elementos de la vista y manejar la lógica para agregar un inmueble

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}