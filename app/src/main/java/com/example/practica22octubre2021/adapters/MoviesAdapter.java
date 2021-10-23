package com.example.practica22octubre2021.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.practica22octubre2021.Models.Movie;
import com.example.practica22octubre2021.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.UsuariosHolder>{

    List<Movie> listaMovies;

    public MoviesAdapter(List<Movie> listaUsuarios) {
        this.listaMovies = listaUsuarios;
    }

    @Override
    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item1,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtDocumento.setText(listaMovies.get(position).getTitle_movie().toString());
        holder.txtNombre.setText(listaMovies.get(position).getDescription_moview().toString());
        Glide
                .with(holder.imagen.getContext())
                .load(listaMovies.get(position).getUrl_picture_movie())
                .centerCrop()
                .placeholder(R.mipmap.imageno)
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return listaMovies.size();
    }

    public class UsuariosHolder extends RecyclerView.ViewHolder{

        TextView txtDocumento,txtNombre,txtProfesion;
        CircleImageView imagen;
        CardView linearLayout;

        public UsuariosHolder(View itemView) {
            super(itemView);
            txtDocumento= (TextView) itemView.findViewById(R.id.idNombre);
            txtNombre= (TextView) itemView.findViewById(R.id.idDescription);
            linearLayout = (CardView) itemView.findViewById(R.id.card);
            imagen = (CircleImageView) itemView.findViewById(R.id.image1);

        }
    }
}
