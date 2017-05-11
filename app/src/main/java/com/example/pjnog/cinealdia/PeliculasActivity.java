package com.example.pjnog.cinealdia;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PeliculasActivity extends AppCompatActivity {
    private TextView test1;
    private TextView test2;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Datos",
                res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Sinopsis",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Criticas",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);

        Intent i = getIntent();
        Peliculas pelicula = (Peliculas) i.getSerializableExtra("peliculas");
        test1 = (TextView) findViewById(R.id.textView1);
        test2 = (TextView) findViewById(R.id.namePeliculaActivity);
        String datos="";
        datos += "DIRECTOR: "+ pelicula.getDirector()+ "\r\n";
        datos += "ACTORES: ";
        for (Actores act: pelicula.getActor())
        {
            datos += act.getNombre()+",";
        }

        test1.setText(datos);
        test2.setText(pelicula.getNombre());
        img = (ImageView) findViewById(R.id.imagenPeliculaActivity);
        Picasso.with(this).load("http://www.intraco.es/cinealdia/img/"+pelicula.getImagen()).into(img);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
            }
        });
    }
}
