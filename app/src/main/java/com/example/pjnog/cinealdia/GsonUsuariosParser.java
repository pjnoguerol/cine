package com.example.pjnog.cinealdia;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjnog on 16/05/2017.
 */

public class GsonUsuariosParser
{
    public Usuarios leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia de la clase Gson
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        Usuarios usuario = null;

        // Iniciar el array
        reader.beginArray();

        if (reader.hasNext()) {
            // Lectura de objetos
            usuario = gson.fromJson(reader, Usuarios.class);

        }



        reader.endArray();
        reader.close();
        return usuario;
    }
}
