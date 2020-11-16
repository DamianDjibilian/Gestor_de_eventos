package com.example.gestor_de_eventos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.net.ContentHandler;

public class BaseDeDatos extends SQLiteOpenHelper {

    // setear nombre de la BD
    public static final String BDNOMBRE = "BaseDeDatos.bd";



    //creacion de la BD
    public BaseDeDatos(Context context) {
        super(context, "BaseDeDatos.bd", null, 1);
    }

    //Creacion tablas para Usuarios y Eventos
    @Override
    public void onCreate(SQLiteDatabase BDControl) {
        //creacion tabla para usuarios con campos y usuario como PK
        BDControl.execSQL("create Table usuarios(usuario text primary key, " +
                "password text)");
        BDControl.execSQL("create Table eventos (descripcion text, ubicacion text, hora text, minuto text, usuario text)");
    }

    @Override
    // metodo onUpgrade si al momento de crear la tabla existe, la borra
    public void onUpgrade(SQLiteDatabase BDControl, int versionAnterior, int versionNueva) {
        BDControl.execSQL("drop table if exists usuarios");
        BDControl.execSQL("drop table if exists eventos");
        this.onCreate(BDControl);
    }

    //insercion de los datos en la BD para tabla Usuarios
    public Boolean insertarDatos(String usuario, String password) {
        SQLiteDatabase BDControl = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("usuario", usuario);
        contentValues.put("password", password);
        //validacion para la insercion de datos, si resultado es == 0 realiza la insercion
        long resultado = BDControl.insert("usuarios", null, contentValues);
        if (resultado == -1) {
            return false;
        } else {
            return true;
        }
    }

    //valdacion si el usuario existe.
    public Boolean verificarUsuario(String usuario) {
        SQLiteDatabase BDControl = this.getWritableDatabase();
        Cursor cursor = BDControl.rawQuery("Select * from usuarios where usuario = ?",
                new String[]{usuario});
        //si el cursor es mayor a 0 el usuario existe.
        return cursor.getCount() > 0;
    }

    //validacion si el password existe para el usuario
    public Boolean verificarUsuarioPassword(String usuario, String password) {
        SQLiteDatabase BDControl = this.getWritableDatabase();
        Cursor cursor = BDControl.rawQuery("Select * from usuarios where usuario = ? and password =? ",
                new String[]{usuario, password});
        //si el cursor es mayor a 0 el password coincide con el usuario.
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    //insercion de los datos en la BD para tabla eventos
    public Boolean insertarEventos(String descripcion, String ubicacion, String hora, String minuto, String usuario) {
        SQLiteDatabase BDControl = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //valores del CV
        contentValues.put("descripcion", descripcion);
        contentValues.put("ubicacion", ubicacion);
        contentValues.put ("hora", hora);
        contentValues.put ("minuto", minuto);
        contentValues.put("usuario", usuario);
        //validacion para la insercion de datos
        long resultadoEventos = BDControl.insert("eventos", null, contentValues);
        if (resultadoEventos == -1) {
            return false;
        } else {
            return true;
        }
    }

}




