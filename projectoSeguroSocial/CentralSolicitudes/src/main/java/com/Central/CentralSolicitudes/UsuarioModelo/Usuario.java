package com.Central.CentralSolicitudes.UsuarioModelo;

public class Usuario {
    private final int id;
    private final String nombre;
    private final Genero genero;

    public Usuario(int id, String nombre, Genero genero) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
    }

    @Override
    public String toString(){
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", Genero=" + genero +
                  '}';
    }

    public enum Genero {
        MASCULINO,
        FEMENINO
    }
}
