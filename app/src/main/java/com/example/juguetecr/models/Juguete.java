package com.example.juguetecr.models;

public class Juguete {
    private String nombre;
    private String edad;
    private String descripcion;
    private String material;
    private String precio;

    public Juguete() { }

    public Juguete(String nombre, String edad, String descripcion, String material, String precio) {
        this.nombre = nombre;
        this.edad = edad;
        this.descripcion = descripcion;
        this.material = material;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
