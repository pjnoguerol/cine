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

public class GsonUsuarioParser
{
    public List<Usuarios> leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia de la clase Gson
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List <Usuarios> usuarios = new ArrayList<>();

        // Iniciar el array
        reader.beginArray();

        while (reader.hasNext()) {
            // Lectura de objetos
            Usuarios usuario = gson.fromJson(reader, Usuarios.class);
            usuarios.add(usuario);

        }



        reader.endArray();
        reader.close();
        return usuarios;
    }
}
