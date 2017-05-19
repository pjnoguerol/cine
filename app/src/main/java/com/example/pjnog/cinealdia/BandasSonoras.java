package com.example.pjnog.cinealdia;

/**
 * Created by pjnog on 19/05/2017.
 */

public class BandasSonoras {

    private int id_bson;
    private String nombre;
    private String fichero;
    private String imagen;
    private String canciones;

    public int getId_bson() {
        return id_bson;
    }

    public void setId_bson(int id_bson) {
        this.id_bson = id_bson;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCanciones() {
        return canciones;
    }

    public void setCanciones(String canciones) {
        this.canciones = canciones;
    }
}
