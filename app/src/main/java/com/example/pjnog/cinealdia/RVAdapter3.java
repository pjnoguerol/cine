package com.example.pjnog.cinealdia;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter3 extends RecyclerView.Adapter<RVAdapter3.PersonViewHolder>  {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        TextView personAge;


        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv3);
            personName = (TextView)itemView.findViewById(R.id.nombrePelicula);
            personAge = (TextView)itemView.findViewById(R.id.textPelicula);

        }

    }

    List<Usuarios> usuarios;

    RVAdapter3(List<Usuarios> persons){
        this.usuarios = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item3, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {



        personViewHolder.personName.setText(usuarios.get(i).getUsuario());

        personViewHolder.personAge.setText(usuarios.get(i).getTelefono());

        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
