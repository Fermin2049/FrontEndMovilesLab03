package com.fermin2049.proyectofinallab3.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.api.CustomDateTypeAdapter;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder> {
    private List<Contract> contratos;
    private int propietarioId;
    private CustomDateTypeAdapter dateAdapter;

    public ContractAdapter(List<Contract> contratos, Context context) {
        this.contratos = contratos;
        this.propietarioId = RetrofitClient.getPropietarioIdFromToken(context);
        filterContratosByPropietarioId();
        dateAdapter = new CustomDateTypeAdapter();
    }

    private void filterContratosByPropietarioId() {
        List<Contract> filteredList = new ArrayList<>();
        for (Contract contrato : contratos) {
            if (contrato.getInmueble().getIdPropietario() == propietarioId) {
                filteredList.add(contrato);
            }
        }
        this.contratos = filteredList;
    }

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

        try {
            Date fechaInicio = dateAdapter.read(new JsonReader(new StringReader("\"" + contrato.getFechaInicio() + "\"")));
            Date fechaFin = dateAdapter.read(new JsonReader(new StringReader("\"" + contrato.getFechaFin() + "\"")));
            holder.tvFechaInicio.setText(dateAdapter.formatForDisplay(fechaInicio));
            holder.tvFechaFin.setText(dateAdapter.formatForDisplay(fechaFin));
        } catch (IOException e) {
            e.printStackTrace();
            holder.tvFechaInicio.setText("Invalid date");
            holder.tvFechaFin.setText("Invalid date");
        }

        holder.tvMontoAlquiler.setText(String.valueOf(contrato.getMontoAlquiler()));
    }

    @Override
    public int getItemCount() {
        return contratos.size();
    }

    public void setContratos(List<Contract> contratos) {
        this.contratos = contratos;
        filterContratosByPropietarioId();
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