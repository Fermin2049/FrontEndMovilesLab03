package com.fermin2049.proyectofinallab3.models;

import android.net.Uri;

import java.util.List;

public class Propietario {
    private int idPropietario;
    private String dni;
    private String apellido;
    private String nombre;
    private String telefono;
    private String email;
    private String password;
    private transient Uri fotoPerfilUri; // `transient` para evitar la serialización directa
    private String fotoPerfil; // Para guardar el `Uri` como String
    private List<Inmueble> inmuebles;

    public Propietario() {
    }

    public Propietario(int idPropietario, String dni, String apellido, String nombre, String telefono, String email, String password, String fotoPerfil, List<Inmueble> inmuebles) {
        this.idPropietario = idPropietario;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.fotoPerfil = fotoPerfil;
        this.inmuebles = inmuebles;
    }

    public Propietario(int idPropietario, String dni, String apellido, String nombre, String telefono, String email, String password, Uri fotoPerfilUri, String fotoPerfil, List<Inmueble> inmuebles) {
        this.idPropietario = idPropietario;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.fotoPerfilUri = fotoPerfilUri;
        this.fotoPerfil = fotoPerfil;
        this.inmuebles = inmuebles;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Uri getFotoPerfilUri() {
        return fotoPerfilUri;
    }

    public void setFotoPerfilUri(Uri fotoPerfilUri) {
        this.fotoPerfilUri = fotoPerfilUri;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public List<Inmueble> getInmuebles() {
        return inmuebles;
    }

    public void setInmuebles(List<Inmueble> inmuebles) {
        this.inmuebles = inmuebles;
    }
}