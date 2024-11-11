package com.fermin2049.proyectofinallab3.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder> {
    private List<Pago> pagos;
    private int propietarioId;

    public PagoAdapter(List<Pago> pagos, Context context) {
        this.pagos = pagos;
        this.propietarioId = RetrofitClient.getPropietarioIdFromToken(context);
        filterPagosByPropietarioId();
    }

    private void filterPagosByPropietarioId() {
        List<Pago> filteredList = new ArrayList<>();
        for (Pago pago : pagos) {
            if (pago.getContrato().getInmueble().getIdPropietario() == propietarioId) {
                filteredList.add(pago);
            }
        }
        this.pagos = filteredList;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago, parent, false);
        return new PagoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago pago = pagos.get(position);
        holder.tvNumeroPago.setText(String.valueOf(pago.getNumeroPago()));
        holder.tvFechaPago.setText(pago.getFechaPago().toString());
        holder.tvImporte.setText(String.valueOf(pago.getImporte()));
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
        filterPagosByPropietarioId();
        notifyDataSetChanged();
    }

    static class PagoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumeroPago, tvFechaPago, tvImporte;

        PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumeroPago = itemView.findViewById(R.id.tvNumeroPago);
            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
            tvImporte = itemView.findViewById(R.id.tvImporte);
        }
    }
}