package com.example.pjnog.cinealdia;

import java.io.Serializable;

/**
 * Created by pjnog on 17/05/2017.
 */

public class Busqueda implements Serializable
{
    private int modo;
    private int genero;
    private String pelicula;

    public Busqueda() {
        this.modo =0;
        this.genero = -1;
        this.pelicula="";
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }
}
