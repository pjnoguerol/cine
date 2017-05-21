package com.example.pjnog.cinealdia;


import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment  {

    private Usuarios usuario;
    private TextView txusuario;
    private TextView txgenero;
    private Spinner gene;
    private Context context;
    private Generos genero;
    private Button btgenero;
    private Resultado resul;
    HttpURLConnection con;
    List<Generos> generos;
    public SettingsFragment() {
        // Required empty public constructor
    }

    private void getGeneros(JsonTask jsonTask)
    {
        try {
            String url = Constantes.SERVIDOR+"/cinealdia/cinealdia_clase.php?generos";
            ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                jsonTask.
                        execute(
                                new URL(url));
            } else {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_LONG).show();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private void  updateGeneros(JsonTask2 jsonTask)
    {
        try {
            String url = Constantes.SERVIDOR+"/cinealdia/cinealdia_clase.php?generousuario="+usuario.getId_usua()+"&generoupdate="+genero.getId_gen();
            System.out.println(url);
            ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                jsonTask.
                        execute(
                                new URL(url));
            } else {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_LONG).show();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    public class JsonTask extends AsyncTask<URL, Void, List<Generos>> {

        @Override
        protected List<Generos> doInBackground(URL... urls) {


            try {

                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();

                if(statusCode!=200) {
                    generos = new ArrayList<>();
                    //animales.add(new Animal("Error",null,null));

                } else {

                    // Parsear el flujo con formato JSON
                    InputStream in = new BufferedInputStream(con.getInputStream());

                    // JsonAnimalParser parser = new JsonAnimalParser();
                    GsonGeneroParser parser = new GsonGeneroParser();

                   generos = parser.leerFlujoJson(in);
                    System.out.println(generos.size());


                }

            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                con.disconnect();
            }
            return generos;
        }


        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        protected void onPostExecute(List<Generos> generos) {
            /*
            Asignar los objetos de Json parseados al adaptador
             */
            if(generos!=null) {
                final SpinAdapter adapter = new SpinAdapter(context,android.R.layout.simple_spinner_item,generos);
                gene.setAdapter(adapter);
                gene.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        // Here you get the current item (a User object) that is selected by its position
                        genero = adapter.getItem(position);
                        // Here you can do the action you want to...
                       // Toast.makeText(context, "ID: " + genero.getId_gen() + "\nName: " + genero.getDescripcion(),
                               // Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {  }
                });

            }else{
                Toast.makeText(
                        getActivity(),
                        "Ocurrió un error de Parsing Json",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }
    public class JsonTask2 extends AsyncTask<URL, Void, Resultado> {

        @Override
        protected Resultado doInBackground(URL... urls) {


            try {

                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();

                if(statusCode!=200) {
                    resul = new Resultado();
                    //animales.add(new Animal("Error",null,null));

                } else {

                    // Parsear el flujo con formato JSON
                    InputStream in = new BufferedInputStream(con.getInputStream());

                    // JsonAnimalParser parser = new JsonAnimalParser();
                    GsonResultadoParser parser = new GsonResultadoParser();

                    resul = parser.leerFlujoJson(in);
                    System.out.println(generos.size());


                }

            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                con.disconnect();
            }
            return resul;
        }


        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        protected void onPostExecute(Resultado resul) {
            /*
            Asignar los objetos de Json parseados al adaptador
             */
            if(resul!=null) {
                if(resul.isOk())
                {
                    Toast.makeText(getActivity(),"Valor actualizado correctamente", Toast.LENGTH_SHORT).show();



                }
                else
                {
                    Toast.makeText(getActivity(),"Error al actualizar"+resul.getError(), Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(
                        getActivity(),
                        "Ocurrió un error de Parsing Json",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = inflater.getContext();
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        txusuario = (TextView) rootView.findViewById(R.id.nombreUsuario);
        txgenero = (TextView) rootView.findViewById(R.id.nombreGenero);
        btgenero = (Button) rootView.findViewById(R.id.btgenero);
        usuario = (Usuarios) getArguments().getSerializable("usuario");
        txusuario.setText("USUARIO: "+usuario.getUsuario());
        txgenero.setText("Genero actual: "+usuario.getId_gen());


        //ArrayList<String> return_likes = new ArrayList<String>();
        //return_likes.add("hola");
       // return_likes.add("aqui ando");
        gene = (Spinner) rootView.findViewById(R.id.generos);
        //ArrayAdapter<String> karant_adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, return_likes);
        //gene.setAdapter(karant_adapter);

        getGeneros(new JsonTask());

        btgenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGeneros(new JsonTask2());
                usuario.setId_gen(genero.getId_gen());
                txgenero.setText("Genero actual: "+usuario.getId_gen());
            }

        });



        return rootView;
    }



}
