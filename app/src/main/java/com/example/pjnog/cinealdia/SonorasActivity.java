package com.example.pjnog.cinealdia;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class SonorasActivity extends AppCompatActivity {
    private TextView test1;
    private TextView test2;
    private ImageView img;
    private TextView peliculas;
    MapView mMapView;
    private GoogleMap googleMap;
    private Context context;
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
        setContentView(R.layout.activity_sonoras);
        Resources res = getResources();
        context = this;
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Canciones",
                res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Ejemplo",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        Intent i = getIntent();
        final BandasSonoras sonoras = (BandasSonoras) i.getSerializableExtra("sonoras");
        test1 = (TextView) findViewById(R.id.textView1);
        test2 = (TextView) findViewById(R.id.nameSonoraActivity);
        //peliculas = (TextView) findViewById(R.id.directorPeliculas);
        test2.setText(sonoras.getNombre());
        test1.setText(sonoras.getCanciones());
        img = (ImageView) findViewById(R.id.imagenSonoraActivity);
        Picasso.with(this).load(Constantes.SERVIDOR+"/cinealdia/img/" + sonoras.getImagen()).into(img);
        String pelicula = "";
        int cont =1;
        seekbar = (SeekBar)findViewById(R.id.seekBar2);
        seekbar.setClickable(false);

        tog = (ToggleButton)findViewById(R.id.sonosOnOf);

        tog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View ar)
            {
                oneTimeOnly=0;
                if(tog.isChecked())
                {
                    if(mPlayer!=null && mPlayer.isPlaying()){
                        mPlayer.stop();
                    }
                    String url = Constantes.SERVIDOR+"/cinealdia/music/"+sonoras.getFichero();
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






    }
}
