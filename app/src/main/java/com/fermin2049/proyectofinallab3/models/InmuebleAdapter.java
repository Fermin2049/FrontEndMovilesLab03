package com.fermin2049.proyectofinallab3.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fermin2049.proyectofinallab3.R;
import com.fermin2049.proyectofinallab3.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {

    private List<Inmueble> inmuebles;
    private static final String BASE_URL = "http://192.168.1.2:5157/";
    private int propietarioId;

    public InmuebleAdapter(List<Inmueble> inmuebles, Context context) {
        this.inmuebles = inmuebles;
        this.propietarioId = RetrofitClient.getPropietarioIdFromToken(context);
        filterInmueblesByPropietarioId();
    }

    private void filterInmueblesByPropietarioId() {
        List<Inmueble> filteredList = new ArrayList<>();
        for (Inmueble inmueble : inmuebles) {
            if (inmueble.getIdPropietario() == propietarioId) {
                filteredList.add(inmueble);
            }
        }
        this.inmuebles = filteredList;
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inmueble, parent, false);
        return new InmuebleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        holder.direccion.setText(inmueble.getDireccion());
        holder.precio.setText(String.valueOf(inmueble.getPrecio()));
        String imageUrl = BASE_URL + inmueble.getImagen();
        Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public void setInmuebles(List<Inmueble> inmuebles) {
        this.inmuebles = inmuebles;
        filterInmueblesByPropietarioId();
        notifyDataSetChanged();
    }

    public static class InmuebleViewHolder extends RecyclerView.ViewHolder {
        TextView direccion, precio;
        ImageView imagen;

        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.textViewDireccion);
            precio = itemView.findViewById(R.id.textViewPrecio);
            imagen = itemView.findViewById(R.id.imageViewInmueble);
        }
    }
}