package com.example.pjnog.cinealdia;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class DirectoresActivity extends AppCompatActivity {
    private TextView test1;
    private TextView test2;
    private ImageView img;
    private TextView peliculas;
    MapView mMapView;
    private GoogleMap googleMap;
    private Context context;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directores);
        Resources res = getResources();
        context = this;
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Datos",
                res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Peliculas",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        Intent i = getIntent();
        final Directores director = (Directores) i.getSerializableExtra("directores");
        test1 = (TextView) findViewById(R.id.textView1);
        test2 = (TextView) findViewById(R.id.nameDirectorActivity);
        peliculas = (TextView) findViewById(R.id.directorPeliculas);
        test2.setText(director.getNombre());
        test1.setText(director.getBiografia());
        img = (ImageView) findViewById(R.id.imagenDirectorActivity);
        Picasso.with(this).load(Constantes.SERVIDOR+"/cinealdia/img/" + director.getImagen()).into(img);
        String pelicula = "";
        int cont =1;
        for (Peliculas pel:director.getPelicula())
        {
            pelicula += cont +".- "+ pel.getNombre()+"\r\n";
            cont++;
        }
        peliculas.setText(pelicula);




    }
}
