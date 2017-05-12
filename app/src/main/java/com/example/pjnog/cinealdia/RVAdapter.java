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

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.nombreActor);
            personAge = (TextView)itemView.findViewById(R.id.textActor);
            personPhoto = (ImageView)itemView.findViewById(R.id.imagenActor);
        }

    }

    List<Actores> actores;

    RVAdapter(List<Actores> persons){
        this.actores = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        final Actores act = actores.get(i);
        personViewHolder.personName.setText(actores.get(i).getNombre());
        personViewHolder.personAge.setText(actores.get(i).getImagen());
        Picasso.with(personViewHolder.itemView.getContext()).load("http://www.intraco.es/cinealdia/img/"+actores.get(i).getImagen()).into(personViewHolder.personPhoto);
        personViewHolder.personPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), ActoresActivity.class);
                i.putExtra("actores",act);
                view.getContext().startActivity(i);
            }
        });
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return actores.size();
    }
}
