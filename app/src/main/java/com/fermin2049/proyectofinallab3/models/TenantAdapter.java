package com.fermin2049.proyectofinallab3.models;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;
import java.util.ArrayList;
import java.util.List;

public class TenantAdapter extends RecyclerView.Adapter<TenantAdapter.TenantViewHolder> {
    private List<Inquilino> inquilinos;
    private int propietarioId;

    public TenantAdapter(List<Inquilino> inquilinos, Context context) {
        this.inquilinos = inquilinos;
        this.propietarioId = RetrofitClient.getPropietarioIdFromToken(context);
        filterInquilinosByPropietarioId();
    }

    private void filterInquilinosByPropietarioId() {
        List<Inquilino> filteredList = new ArrayList<>();
        for (Inquilino inquilino : inquilinos) {
            if (inquilino.getContratos().stream().anyMatch(contrato -> contrato.getInmueble().getIdPropietario() == propietarioId)) {
                filteredList.add(inquilino);
            }
        }
        this.inquilinos = filteredList;
    }

    @NonNull
    @Override
    public TenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inquilino, parent, false);
        return new TenantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantViewHolder holder, int position) {
        Inquilino inquilino = inquilinos.get(position);
        holder.tvNombreCompleto.setText(inquilino.getNombreCompleto());
        holder.tvDni.setText(inquilino.getDni());
        holder.tvTelefono.setText(inquilino.getTelefono());
        holder.tvEmail.setText(inquilino.getEmail());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inquilino", inquilino);
            Navigation.findNavController(v).navigate(R.id.action_nav_tenant_to_inquilinoDetailFragment3, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return inquilinos.size();
    }

    public void setInquilinos(List<Inquilino> inquilinos) {
        this.inquilinos = inquilinos;
        filterInquilinosByPropietarioId();
        notifyDataSetChanged();
    }

    static class TenantViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCompleto, tvDni, tvTelefono, tvEmail;

        TenantViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);
            tvDni = itemView.findViewById(R.id.tvDni);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}