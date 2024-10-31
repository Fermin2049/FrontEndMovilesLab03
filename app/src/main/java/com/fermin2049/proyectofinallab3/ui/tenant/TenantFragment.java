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

import java.util.List;

public class TenantFragment extends Fragment {

    private TenantViewModel mViewModel;
    private RecyclerView recyclerView;
    private TenantAdapter adapter;

    public static TenantFragment newInstance() {
        return new TenantFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tenant, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewInquilinos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TenantAdapter();
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TenantViewModel.class);
        mViewModel.getInquilinos().observe(getViewLifecycleOwner(), tenants -> {
            adapter.setTenants(tenants);
        });

        // Obtener los inquilinos por propietario (ejemplo con propietarioId = 1)
        mViewModel.fetchInquilinosByPropietarioId(1);
    }
}