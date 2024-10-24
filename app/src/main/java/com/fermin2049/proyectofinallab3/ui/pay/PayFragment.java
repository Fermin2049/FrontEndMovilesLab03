package com.fermin2049.proyectofinallab3.ui.pay;

import android.os.Bundle;
import android.util.Log;
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

public class PayFragment extends Fragment {

    private PayViewModel mViewModel;
    private RecyclerView recyclerView;
    private PagoAdapter adapter;

    public static PayFragment newInstance() {
        return new PayFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pay, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewPagos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PagoAdapter();
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PayViewModel.class);
        mViewModel.getPagos().observe(getViewLifecycleOwner(), pagos -> {
            if (pagos != null) {
                adapter.setPagos(pagos);
            } else {
                // Log or handle the case where pagos is null
                Log.e("PayFragment", "Pagos data is null");
            }
        });
    }

}