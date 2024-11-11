package com.fermin2049.proyectofinallab3.ui.pay;

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
import com.fermin2049.proyectofinallab3.models.PagoAdapter;

import java.util.ArrayList;

public class PayFragment extends Fragment {

    private PayViewModel payViewModel;
    private RecyclerView recyclerView;
    private PagoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pay, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewPagos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PagoAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(adapter);

        payViewModel = new ViewModelProvider(this).get(PayViewModel.class);
        payViewModel.getPagos().observe(getViewLifecycleOwner(), pagos -> {
            adapter.setPagos(pagos);
        });

        // Obtener los pagos por propietario
        payViewModel.fetchPagosByPropietarioId();

        return root;
    }
}