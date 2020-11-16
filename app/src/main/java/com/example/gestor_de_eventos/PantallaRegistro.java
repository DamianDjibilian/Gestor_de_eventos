package com.example.gestor_de_eventos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PantallaRegistro extends AppCompatActivity{
    EditText RegUsuario, RegPassword,RegConfPassword;
    Button BtnRegistrar, BtnRegCancelar;
    BaseDeDatos BD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_registro);
        RegUsuario = findViewById(R.id.RegUsuario);
        RegPassword = findViewById(R.id.RegPassword);
        RegConfPassword = findViewById(R.id.RegConfPassword);
        BtnRegistrar = findViewById(R.id.BtnRegistrar);
        BtnRegCancelar = findViewById(R.id.BtnRegCancelar);
        BD = new BaseDeDatos(this);



        BtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = RegUsuario.getText().toString().trim();
                String password = RegPassword.getText().toString();
                String confpassword = RegConfPassword.getText().toString();

                //validacion usuario y contraseña
                if (usuario.isEmpty() || password.isEmpty())
                    Toast.makeText(PantallaRegistro.this, "Debe ingresar un email  y contraseña",
                            Toast.LENGTH_SHORT).show();
                else {
                    // valida si las contraseñas coinciden
                    if (password.equals(confpassword)) {
                        Boolean verificarUsuario = BD.verificarUsuario(usuario);
                        if (verificarUsuario == false) {
                            // valida formato del email
                            if (validarFormato(usuario)) {
                                //inserta usuario/password en BD
                                Boolean insertarDatos = BD.insertarDatos(usuario, password);
                                // si la insercion fue exitosa, toast y a pantalla Login
                                if (insertarDatos == true) {
                                    Toast.makeText(PantallaRegistro.this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
                                    Intent login = new Intent(getApplicationContext(), PantallaLogin.class);
                                    startActivity(login);
                                    finish();
                                } else {
                                    Toast.makeText(PantallaRegistro.this, "Fallo el registro!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(PantallaRegistro.this, "Formato de email invalido", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // si el usuario existe limpia los campos
                            Toast.makeText(PantallaRegistro.this, "El usuario ya existe!",
                                    Toast.LENGTH_SHORT).show();
                            RegUsuario.getText().clear();
                            RegPassword.getText().clear();
                            RegConfPassword.getText().clear();

                        }
                    } else {
                        Toast.makeText(PantallaRegistro.this, "Los passwords no coinciden",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        //accion boton cancelar.
        BtnRegCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent cancelarRegistro = new Intent(getApplicationContext(), PantallaLogin.class);
                startActivity(cancelarRegistro);

            }
        });
    }

    // formato del email
    public static boolean validarFormato (String usuario){
        String formato = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern patron = Pattern.compile(formato, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patron.matcher(usuario);
        return matcher.matches();
    }
}

