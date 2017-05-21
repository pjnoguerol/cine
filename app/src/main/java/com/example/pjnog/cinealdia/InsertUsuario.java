package com.example.pjnog.cinealdia;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InsertUsuario extends AppCompatActivity {
    private Spinner gene;
    HttpURLConnection con;
    List<Generos> generos;
    private Generos genero;
    private EditText usuario;
    private EditText pass;
    private EditText email;
    private EditText telefono;
    private Button btInsert;
    private Resultado resul;


    private void inserUser(JsonTask2 jsonTask)
    {
        try {
            String url = Constantes.SERVIDOR+"/cinealdia/cinealdia_clase.php?userinsert="+usuario.getText();
            url +="&passinsert="+pass.getText();
            url +="&emailinsert="+email.getText();
            url +="&telefonoinsert="+telefono.getText();
            url +="&generoinsert="+genero.getId_gen();
            System.out.println(url);
            ConnectivityManager connMgr = (ConnectivityManager)InsertUsuario.this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                jsonTask.
                        execute(
                                new URL(url));
            } else {
                Toast.makeText(InsertUsuario.this, "Error de conexión", Toast.LENGTH_LONG).show();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void getGeneros(JsonTask jsonTask)
    {
        try {
            String url = Constantes.SERVIDOR+"/cinealdia/cinealdia_clase.php?generos";
            ConnectivityManager connMgr = (ConnectivityManager)InsertUsuario.this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                jsonTask.
                        execute(
                                new URL(url));
            } else {
                Toast.makeText(InsertUsuario.this, "Error de conexión", Toast.LENGTH_LONG).show();
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
                final SpinAdapter adapter = new SpinAdapter(InsertUsuario.this,android.R.layout.simple_spinner_item,generos);
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
                        InsertUsuario.this,
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
                    Toast.makeText(InsertUsuario.this,"Valor insertado correctamente", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(InsertUsuario.this, LoginActivity.class);

                    startActivity(i);




                }
                else
                {
                    Toast.makeText(InsertUsuario.this,"Error al actualizar"+resul.getError(), Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(
                        InsertUsuario.this,
                        "Ocurrió un error de Parsing Json",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }
    private boolean inserUser(View v)
    {
        boolean ok = true;
        if (usuario.getText().toString().trim().equals("")) {
            usuario.setError(getString(R.string.error_field_required));
            v = usuario;
            ok = false;
        }
        if (pass.getText().toString().trim().equals("")) {
            pass.setError(getString(R.string.error_field_required));
            v = pass;
            ok = false;
        }
        if (email.getText().toString().trim().equals("")) {
            email.setError(getString(R.string.error_field_required));
            v = email;
            ok = false;
        }
        if (telefono.getText().toString().trim().equals("")) {
            telefono.setError(getString(R.string.error_field_required));
            v = telefono;
            ok = false;
        }
        return ok;

    }
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_usuario);
        gene = (Spinner)findViewById(R.id.UserGeneros);
        getGeneros(new JsonTask());
        usuario = (EditText)findViewById(R.id.userInsert);
        pass = (EditText)findViewById(R.id.passwordLogin);
        email = (EditText)findViewById(R.id.userEmail);
        telefono = (EditText)findViewById(R.id.userTelefono);
        btInsert = (Button)findViewById(R.id.btLogin);
        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inserUser(v))
                {
                    inserUser(new JsonTask2());

                }
            }
        });

    }
}
