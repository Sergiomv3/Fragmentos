package com.example.sergio.fragment2;

import java.io.Serializable;

/**
 * Created by Sergio on 04/12/2014.
 */
public class Inmueble implements Comparable, Serializable{
    private int id;
    private String tipo;
    private String direccion;
    private String precio;
    public Inmueble() {
    }



    public Inmueble(int id,String tipo, String direccion, String precio) {
        this.id = id;
        this.tipo = tipo;
        this.direccion = direccion;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }
}
