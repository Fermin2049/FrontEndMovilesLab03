package com.fermin2049.proyectofinallab3.ui.tenant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.models.TenantAdapter;

import java.util.ArrayList;

public class TenantFragment extends Fragment {

    private TenantViewModel tenantViewModel;
    private RecyclerView recyclerView;
    private TenantAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tenant, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewInquilinos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TenantAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(adapter);

        tenantViewModel = new ViewModelProvider(this).get(TenantViewModel.class);
        tenantViewModel.getInquilinos().observe(getViewLifecycleOwner(), inquilinos -> {
            adapter.setInquilinos(inquilinos);
        });

        // Obtener los inquilinos por propietario
        tenantViewModel.fetchInquilinosByPropietarioId();

        return root;
    }
}