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

public class RVAdapter2 extends RecyclerView.Adapter<RVAdapter2.PersonViewHolder>  {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv2);
            personName = (TextView)itemView.findViewById(R.id.nombrePelicula);
            personAge = (TextView)itemView.findViewById(R.id.textPelicula);
            personPhoto = (ImageView)itemView.findViewById(R.id.imagenPelicula);
        }

    }

    List<Peliculas> peliculas;

    RVAdapter2(List<Peliculas> persons){
        this.peliculas = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item2, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {

        String actor="Actores: ";
        final Peliculas pel = peliculas.get(i);
        final int id = peliculas.get(i).getId_pel();
        personViewHolder.personName.setText(peliculas.get(i).getNombre());
        for (Actores act:peliculas.get(i).getActor())
        {
            actor += act.getNombre()+", ";
        }

        personViewHolder.personAge.setText(actor);
        Picasso.with(personViewHolder.itemView.getContext()).load(Constantes.SERVIDOR+"/cinealdia/img/"+peliculas.get(i).getImagen()).into(personViewHolder.personPhoto);
        personViewHolder.personPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "aqui.."+id , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(), PeliculasActivity.class);
                i.putExtra("peliculas",pel);
                view.getContext().startActivity(i);
            }
        });
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }
}
