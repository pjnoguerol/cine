package com.example.pjnog.cinealdia;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pjnog on 16/05/2017.
 */

public class GsonResultadoParser
{
    public Resultado leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia de la clase Gson
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        Resultado resul = null;

        // Iniciar el array
        reader.beginArray();

        if (reader.hasNext()) {
            // Lectura de objetos
            resul = gson.fromJson(reader, Resultado.class);

        }



        reader.endArray();
        reader.close();
        return resul;
    }
}
