package com.fermin2049.proyectofinallab3.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fermin2049.proyectofinallab3.R;

import java.util.ArrayList;
import java.util.List;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder> {
    private List<Contract> contratos = new ArrayList<>();

    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contract, parent, false);
        return new ContractViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractViewHolder holder, int position) {
        Contract contrato = contratos.get(position);
        holder.tvInmueble.setText(contrato.getInmueble().getDireccion());
        holder.tvInquilino.setText(contrato.getInquilino().getNombreCompleto());
        holder.tvFechaInicio.setText(contrato.getFechaInicio().toString());
        holder.tvFechaFin.setText(contrato.getFechaFin().toString());
        holder.tvMontoAlquiler.setText(String.valueOf(contrato.getMontoAlquiler()));
    }

    @Override
    public int getItemCount() {
        return contratos.size();
    }

    public void setContratos(List<Contract> contratos) {
        this.contratos = contratos;
        notifyDataSetChanged();
    }

    static class ContractViewHolder extends RecyclerView.ViewHolder {
        TextView tvInmueble, tvInquilino, tvFechaInicio, tvFechaFin, tvMontoAlquiler;

        ContractViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInmueble = itemView.findViewById(R.id.tvInmueble);
            tvInquilino = itemView.findViewById(R.id.tvInquilino);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            tvMontoAlquiler = itemView.findViewById(R.id.tvMontoAlquiler);
        }
    }
}