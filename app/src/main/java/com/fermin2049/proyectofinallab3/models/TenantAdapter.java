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

public class TenantAdapter extends RecyclerView.Adapter<TenantAdapter.TenantViewHolder> {
    private List<Inquilino> inquilinos = new ArrayList<>();

    @NonNull
    @Override
    public TenantAdapter.TenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inquilino, parent, false);
        return new TenantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantAdapter.TenantViewHolder holder, int position) {
        Inquilino inquilino = inquilinos.get(position);
        holder.tvNombreCompleto.setText(inquilino.getNombreCompleto());
        holder.tvDni.setText(inquilino.getDni());
        holder.tvTelefono.setText(inquilino.getTelefono());
        holder.tvEmail.setText(inquilino.getEmail());
    }

    @Override
    public int getItemCount() {
        return inquilinos.size();
    }

    public void setTenants(List<Inquilino> inquilinos) {
        this.inquilinos = inquilinos;
        notifyDataSetChanged();
    }

    public class TenantViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCompleto, tvDni, tvTelefono, tvEmail;
        public TenantViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);
            tvDni = itemView.findViewById(R.id.tvDni);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}
