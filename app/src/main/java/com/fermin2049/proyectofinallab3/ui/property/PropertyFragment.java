package com.fermin2049.proyectofinallab3.ui.property;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.databinding.FragmentPropertyBinding;
import com.fermin2049.proyectofinallab3.models.InmuebleAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PropertyFragment extends Fragment {

    private FragmentPropertyBinding binding;
    private PropertyViewModel propertyViewModel;
    private InmuebleAdapter inmuebleAdapter;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        propertyViewModel = new ViewModelProvider(this).get(PropertyViewModel.class);

        binding = FragmentPropertyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar el RecyclerView
        binding.recyclerViewInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));
        inmuebleAdapter = new InmuebleAdapter(new ArrayList<>(), getContext());
        binding.recyclerViewInmuebles.setAdapter(inmuebleAdapter);

        // Observar los datos de inmuebles
        propertyViewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            inmuebleAdapter.setInmuebles(inmuebles);
            inmuebleAdapter.notifyDataSetChanged();
        });

        // Obtener los inmuebles por propietario
        propertyViewModel.fetchInmueblesByPropietarioId();

        // Configurar el botón flotante
        FloatingActionButton fabAddInmueble = binding.fabAddInmueble;
        fabAddInmueble.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(PropertyFragment.this);
            navController.navigate(R.id.action_nav_property_to_addInmuebleFragment); // Usa la acción definida
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}