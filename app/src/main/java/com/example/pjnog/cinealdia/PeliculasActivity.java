package com.example.pjnog.cinealdia;

import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.media.MediaPlayer;
import android.os.Handler;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PeliculasActivity extends AppCompatActivity {
    private TextView test1;
    private TextView test2;
    private ImageView img;
    static MediaPlayer mPlayer;
    private ToggleButton tog;
    private Handler myHandler = new Handler();
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;

    private SeekBar seekbar;

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mPlayer.getCurrentPosition();

            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };

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
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);

        Picasso.with(this).load("http://www.intraco.es/cinealdia/img/"+pelicula.getImagen()).into(img);


        tog = (ToggleButton)findViewById(R.id.musicOnOf);


        tog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View ar)
            {
                if(tog.isChecked())
                {
                    if(mPlayer!=null && mPlayer.isPlaying()){
                        mPlayer.stop();
                    }
                    String url = "http://www.intraco.es/cinealdia/music/salvar.mp3";
                    mPlayer = new MediaPlayer();
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mPlayer.setDataSource(url);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(ar.getContext(), "No encontrado el archivo..", Toast.LENGTH_SHORT).show();
                    } catch (SecurityException e) {
                        Toast.makeText(ar.getContext(), "aqui..", Toast.LENGTH_SHORT).show();
                    } catch (IllegalStateException e) {
                        Toast.makeText(ar.getContext(), "aqui..", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mPlayer.prepare();
                    } catch (IllegalStateException e) {
                        Toast.makeText(ar.getContext(), "aqui..", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(ar.getContext(), "aqui..", Toast.LENGTH_SHORT).show();
                    }
                    mPlayer.start();
                    finalTime = mPlayer.getDuration();
                    startTime = mPlayer.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekbar.setMax((int) finalTime);
                        oneTimeOnly = 1;
                    }
                    seekbar.setProgress((int)startTime);
                    myHandler.postDelayed(UpdateSongTime,100);


                }

                else
                {
                    if(mPlayer!=null && mPlayer.isPlaying()){
                        mPlayer.stop();
                    }
                }

            }
        });

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
            }
        });
    }
}
