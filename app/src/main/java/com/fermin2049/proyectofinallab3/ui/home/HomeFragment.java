package com.fermin2049.proyectofinallab3.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.databinding.FragmentHomeBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel mapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set welcome text
        binding.textViewWelcome.setText("Bienvenido a la inmobiliaria Cuyo");
        binding.textViewAddress.setText("Nos encuentra en esta dirección");

        // Set up the map
        mapViewModel.getMMapaActual().observe(getViewLifecycleOwner(), mapaActual -> {
            ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapaActual);
        });
        mapViewModel.obtenerMapa();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}