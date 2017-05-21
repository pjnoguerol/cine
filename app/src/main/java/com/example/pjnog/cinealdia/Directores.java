package com.example.pjnog.cinealdia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjnog on 19/05/2017.
 */

public class Directores implements Serializable {

    private int id_dir;
    private String nombre;
    private String imagen;
    private String biografia;
    private List<Peliculas> pelicula;

    private Directores()
    {
        this.id_dir=-1;
        this.nombre="";
        this.imagen = "";
        this.pelicula = new ArrayList<>();
    }

    public int getId_dir() {
        return id_dir;
    }

    public void setId_dir(int id_dir) {
        this.id_dir = id_dir;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public List<Peliculas> getPelicula() {
        return pelicula;
    }

    public void setPelicula(List<Peliculas> pelicula) {
        this.pelicula = pelicula;
    }
}
