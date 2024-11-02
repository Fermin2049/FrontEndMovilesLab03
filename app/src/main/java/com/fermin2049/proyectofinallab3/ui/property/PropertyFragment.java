package com.fermin2049.proyectofinallab3.ui.property;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fermin2049.proyectofinallab3.databinding.FragmentPropertyBinding;
import com.fermin2049.proyectofinallab3.models.InmuebleAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PropertyFragment extends Fragment {

    private FragmentPropertyBinding binding;
    private PropertyViewModel propertyViewModel;
    private InmuebleAdapter inmuebleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        propertyViewModel = new ViewModelProvider(this).get(PropertyViewModel.class);

        binding = FragmentPropertyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar el RecyclerView
        binding.recyclerViewInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));
        inmuebleAdapter = new InmuebleAdapter(new ArrayList<>());
        binding.recyclerViewInmuebles.setAdapter(inmuebleAdapter);

        // Observar los datos de inmuebles
        propertyViewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            inmuebleAdapter.setInmuebles(inmuebles);
            inmuebleAdapter.notifyDataSetChanged();
        });

        // Obtener los inmuebles por propietario (ejemplo con propietarioId = 1)
        propertyViewModel.fetchInmueblesByPropietarioId(1);

        // Configurar el botón flotante
        FloatingActionButton fabAddInmueble = binding.fabAddInmueble;
        fabAddInmueble.setOnClickListener(v -> {
            // Navegar al fragmento de agregar inmueble
            // Aquí puedes usar Navigation Component o FragmentTransaction
            // Ejemplo con Navigation Component:
            // Navigation.findNavController(v).navigate(R.id.action_propertyFragment_to_addInmuebleFragment);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}