package com.example.pjnog.cinealdia;

/**
 * Created by pjnog on 08/05/2017.
 */

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Creado por Hermosa Programación.
 */
public class GsonPeliculaParser {


    public List<Peliculas> leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia de la clase Gson
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<Peliculas> peliculas = new ArrayList<>();

        // Iniciar el array
        reader.beginArray();

        while (reader.hasNext()) {
            // Lectura de objetos
            Peliculas pelicula = gson.fromJson(reader, Peliculas.class);
            peliculas.add(pelicula);
        }


        reader.endArray();
        reader.close();
        return peliculas;
    }
}