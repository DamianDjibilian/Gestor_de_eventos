package com.example.gestor_de_eventos.entidades;

public class Usuario {
    private String usuario;
    private String password;

    public Usuario (String usuario, String password){
        this.usuario =  usuario;
        this.password = password;
    }

    public String getUsuario (){
        return  usuario;
    }

    public String getPassword(){
        return  password;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
