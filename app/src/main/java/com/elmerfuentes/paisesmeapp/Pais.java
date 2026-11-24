/**
 * Proyecto: PaisesMeApp
 * Autor: Elmer Fuentes
 * Universidad Galileo
 */
package com.elmerfuentes.paisesmeapp;

public class Pais {

    private String nombre;
    private String bandera;

    public Pais(String nombre, String bandera) {
        this.nombre = nombre;
        this.bandera = bandera;
    }

    public String getNombre() {
        return nombre;
    }

    public String getBandera() {
        return bandera;
    }
}

