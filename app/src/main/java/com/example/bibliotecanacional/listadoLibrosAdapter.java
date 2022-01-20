package com.example.bibliotecanacional;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bibliotecanacional.modelo.Biblioteca;

import java.util.ArrayList;

public class listadoLibrosAdapter extends RecyclerView.Adapter<listadoLibrosAdapter.ViewHolder> {

    private final ArrayList<Biblioteca> dataset;
    private final Context context;

    public listadoLibrosAdapter(Context context){
        this.context=context;
        dataset = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_libros, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Biblioteca b = dataset.get(position);
        holder.tituloLibro.setText(b.getTitle());
        holder.subTituloLibro.setText(b.getSubtitle());
        holder.isbn13.setText(b.getIsbn13());
        holder.precio.setText(b.getPrice());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), Librodetalle.class);
            intent.putExtra("detalleb",b.getIsbn13());
            holder.itemView.getContext().startActivity(intent);

        });

        Glide.with(context)
                .load(b.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoLibro);


    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void agregarListaLibros(ArrayList<Biblioteca> listaBiblioteca) {

        dataset.addAll(listaBiblioteca);
        notifyDataSetChanged();
    }
     public void limpiarLista (){
        dataset.clear();
     }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView fotoLibro;
        private final TextView tituloLibro;
        private final TextView subTituloLibro;
        private final TextView isbn13;
        private final TextView precio;



        public ViewHolder(View itemView){
            super (itemView);

            fotoLibro = (ImageView) itemView.findViewById(R.id.fotoLibro);
            tituloLibro = (TextView) itemView.findViewById(R.id.tituloLibro);
            subTituloLibro = (TextView) itemView.findViewById(R.id.subTituloLibro);
            isbn13 = (TextView) itemView.findViewById(R.id.isbn13);
            precio = (TextView) itemView.findViewById(R.id.precioLibro);

        }
    }
}
