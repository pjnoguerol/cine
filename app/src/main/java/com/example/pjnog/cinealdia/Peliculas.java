package com.example.pjnog.cinealdia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by pjnog on 09/05/2017.
 */

public class Peliculas implements Serializable
{
    private int id_pel;
    private String nombre;
    private String director;
    private Date fecha;
    private String genero;
    private String banda;
    private String imagen;
    private List<Actores> actor;

    public Peliculas(int id_pel, String nombre, String director, Date fecha, String genero, String banda, String imagen, List<Actores> actor) {
        this.id_pel = id_pel;
        this.nombre = nombre;
        this.director = director;
        this.fecha = fecha;
        this.genero = genero;
        this.banda = banda;
        this.imagen = imagen;
        this.actor = actor;
    }

    public int getId_pel() {
        return id_pel;
    }

    public void setId_pel(int id_pel) {
        this.id_pel = id_pel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Actores> getActor() {
        return actor;
    }

    public void setActor(List<Actores> actor) {
        this.actor = actor;
    }
}
