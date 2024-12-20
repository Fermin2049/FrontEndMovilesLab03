package com.fermin2049.proyectofinallab3.ui.contract;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.models.ContractAdapter;

import java.util.ArrayList;

public class ContractFragment extends Fragment {

    private ContractViewModel contractViewModel;
    private RecyclerView recyclerView;
    private ContractAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contract, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewContratos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContractAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(adapter);

        contractViewModel = new ViewModelProvider(this).get(ContractViewModel.class);
        contractViewModel.getContratos().observe(getViewLifecycleOwner(), contratos -> {
            adapter.setContratos(contratos);
        });

        // Obtener los contratos por propietario (ejemplo con propietarioId = 1)
        contractViewModel.fetchContratosByPropietarioId();

        return root;
    }
}