/**
 * Proyecto: PaisesMeApp
 * Autor: Elmer Fuentes
 * Universidad Galileo
 */
package com.elmerfuentes.paisesmeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PaisAdapter extends RecyclerView.Adapter<PaisAdapter.PaisViewHolder> {

    private List<Pais> listaPaises;

    public PaisAdapter(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    @NonNull
    @Override
    public PaisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pais, parent, false);

        return new PaisViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PaisViewHolder holder, int position) {
        Pais pais = listaPaises.get(position);

        holder.txtNombrePais.setText(pais.getNombre());
        holder.imgBandera.setContentDescription(holder.itemView.getContext()
                .getString(R.string.country_flag, pais.getNombre()));

        Glide.with(holder.itemView.getContext())
                .load(pais.getBandera())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .fitCenter()
                .into(holder.imgBandera);
    }

    @Override
    public int getItemCount() {
        return listaPaises.size();
    }

    public static class PaisViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBandera;
        TextView txtNombrePais;

        public PaisViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBandera = itemView.findViewById(R.id.imgBandera);
            txtNombrePais = itemView.findViewById(R.id.txtNombrePais);
        }
    }
}
