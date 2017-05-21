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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText usua;
    EditText pass;
    Button bt1;
    Button insert;
    HttpURLConnection con;
    Usuarios usuario;
    public class JsonTask extends AsyncTask<URL, Void, Usuarios> {

        @Override
        protected Usuarios doInBackground(URL... urls) {


            try {

                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();

                if(statusCode!=200) {
                    usuario = new Usuarios();
                    //animales.add(new Animal("Error",null,null));

                } else {

                    // Parsear el flujo con formato JSON
                    InputStream in = new BufferedInputStream(con.getInputStream());

                    // JsonAnimalParser parser = new JsonAnimalParser();
                    GsonUsuariosParser parser = new GsonUsuariosParser();

                    usuario = parser.leerFlujoJson(in);



                }

            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                con.disconnect();
            }
            return usuario;
        }


        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        protected void onPostExecute(Usuarios usuario) {
            /*
            Asignar los objetos de Json parseados al adaptador
             */
            if(usuario!=null) {
                //  adaptador = new AdaptadorDeAnimales(getBaseContext(), animales);
                // lista.setAdapter(adaptador);

                if (usuario.isOk())
                {
                    Toast.makeText(LoginActivity.this,"Bienvenido: "+ usuario.getUsuario(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("usuario",usuario);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,usuario.getError(),Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(
                        LoginActivity.this,
                        "Ocurrió un error de Parsing Json",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context context = LoginActivity.this;
        usua = (EditText)findViewById(R.id.userLogin);
        pass = (EditText) findViewById(R.id.passwordLogin);
        bt1 = (Button)findViewById(R.id.btLogin);
        insert = (Button)findViewById(R.id.btInsert);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(view.getContext(), MainActivity.class));
                try {
                    ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {

                        new LoginActivity.JsonTask().
                                execute(
                                        new URL(Constantes.SERVIDOR+"/cinealdia/cinealdia_clase.php?usuario="+usua.getText()+"&password="+pass.getText()));
                    } else {
                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, InsertUsuario.class);

                startActivity(i);
            }
        });


    }
}
