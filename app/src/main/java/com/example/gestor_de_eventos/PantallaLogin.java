package com.example.gestor_de_eventos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PantallaLogin extends AppCompatActivity{
    EditText LoginUsuario, LoginPassword;
    Button BtnLogin, BtnLoginReg;
    private long apretarAtras;
    private Toast atrasToast;
    BaseDeDatos BD;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_login);

        LoginUsuario= findViewById(R.id.LoginUsuario);
        LoginPassword= findViewById(R.id.LoginPassword);
        BtnLogin= findViewById(R.id.BtnLogin);
        BtnLoginReg= findViewById(R.id.BtnLoginReg);
        BD = new BaseDeDatos(this);


        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = LoginUsuario.getText().toString();
                String password = LoginPassword.getText().toString();

                if (usuario.equals("") || password.equals(""))
                    Toast.makeText(PantallaLogin.this, "Ingrese un usuario y contraseña",
                            Toast.LENGTH_SHORT).show();
                else{
                    Boolean verificarUsuarioPassword = BD.verificarUsuarioPassword(usuario, password);
                    if (verificarUsuarioPassword == true){
                        Toast.makeText(PantallaLogin.this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show();
                        Utilidades.guardarValor(PantallaLogin.this, "usuario", usuario);
                        Intent llevarInicio = new Intent (getApplicationContext(), PantallaInicio.class);
                        startActivity(llevarInicio);
                    }else{
                        Toast.makeText(PantallaLogin.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        BtnLoginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrar = new Intent (getApplicationContext(), PantallaRegistro.class);
                startActivity(registrar);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (apretarAtras + 2000 > System.currentTimeMillis()){
            atrasToast.cancel();
            super.onBackPressed();
            return;
        }else{
            atrasToast = Toast.makeText(PantallaLogin.this, "Presionar atras de nuevo para salir", Toast.LENGTH_LONG);
            atrasToast.show();
        }
        apretarAtras = System.currentTimeMillis();
    }
}