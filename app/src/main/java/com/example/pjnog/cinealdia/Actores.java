package com.example.pjnog.cinealdia;

/**
 * Created by pjnog on 08/05/2017.
 */

public class Actores


{
    private int id_actor;
    private String nombre;
    private String imagen;
    private Double longitud;
    private Double latitud;

    public Actores(int id_actor, String nombre, String imagen, Double longitud, Double latitud) {

        this.id_actor = id_actor;
        this.nombre = nombre;
        this.imagen = imagen;
        this.longitud = longitud;
        this.latitud = latitud;
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

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public int getId_actor() {
        return id_actor;
    }

    public void setId_actor(int id_actor) {
        this.id_actor = id_actor;
    }
}
