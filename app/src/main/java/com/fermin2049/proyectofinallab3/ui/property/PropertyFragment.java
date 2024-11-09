package com.fermin2049.proyectofinallab3.ui.property;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.databinding.FragmentPropertyBinding;
import com.fermin2049.proyectofinallab3.models.Inmueble;
import com.fermin2049.proyectofinallab3.models.InmuebleAdapter;

import java.util.ArrayList;
import java.util.List;

public class PropertyFragment extends Fragment {

    private FragmentPropertyBinding binding;
    private PropertyViewModel propertyViewModel;
    private InmuebleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPropertyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.d("PropertyFragment", "onCreateView called");

        propertyViewModel = new ViewModelProvider(this).get(PropertyViewModel.class);

        adapter = new InmuebleAdapter(new ArrayList<>(), getContext(), this::navigateToInmuebleDetail);
        binding.recyclerViewInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewInmuebles.setAdapter(adapter);

        propertyViewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            Log.d("PropertyFragment", "Inmuebles observed: " + inmuebles.size());
            adapter.setInmuebles(inmuebles);
        });

        propertyViewModel.fetchInmueblesByPropietarioId();

        binding.fabAddInmueble.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_propertyFragment_to_addInmuebleFragment);
        });

        return root;
    }

    private void navigateToInmuebleDetail(Inmueble inmueble) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("inmueble", inmueble);
        Navigation.findNavController(requireView()).navigate(R.id.action_propertyFragment_to_inmuebleDetailFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}