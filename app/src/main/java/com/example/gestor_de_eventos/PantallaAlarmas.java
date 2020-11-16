package com.example.gestor_de_eventos;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class PantallaAlarmas extends AppCompatActivity {
    Button btnAgregarEvento, btnAgregarAlarma, btnBorrar, btnReloj, btnCargarEvento;
    EditText editUbicacion, editDescripcion;
    TextView txtNomHora, txtHora, txtMinuto;
    int horaActual, minutosActual;
    Calendar calendarioAlarma;
    TimePickerDialog timePickerDialog;
    BaseDeDatos BD;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.pantalla_alarma);
        // conexion elementos entre java y xml
        txtHora = findViewById(R.id.txtHora);
        txtMinuto = findViewById(R.id.txtMinuto);
        txtNomHora = findViewById(R.id.txtNomHora);
        editDescripcion = findViewById(R.id.editDescripcion);
        editUbicacion = findViewById(R.id.editUbicacion);
        btnAgregarEvento = findViewById(R.id.btnAgregarEvento);
        btnAgregarAlarma = findViewById(R.id.btnAgregarAlarma);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnCargarEvento = findViewById(R.id.btnCargarEvento);
        btnReloj = findViewById(R.id.btnReloj);
        BD = new BaseDeDatos(this);



        // accion del boton reloj
        btnReloj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarioAlarma = Calendar.getInstance();
                horaActual = calendarioAlarma.get(Calendar.HOUR_OF_DAY);
                minutosActual = calendarioAlarma.get(Calendar.MINUTE);
                //valores formateados de la hora.
                timePickerDialog = new TimePickerDialog(PantallaAlarmas.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                        txtHora.setText(String.format("%02d", hora));
                        txtMinuto.setText(String.format("%02d", minuto));
                        txtNomHora.setText("Horario seleccionado: ");
                    }
                }, horaActual, minutosActual, true);
                // muestra el time picker
                timePickerDialog.show();
            }
        });

        // Accion del boton Agregar Alarma
        btnAgregarAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //valida si hay una hora seleccionada para setear la alarma
                if (!txtHora.getText().toString().isEmpty() && !txtMinuto.getText().toString().isEmpty()
                        && !editDescripcion.getText().toString().isEmpty()) {
                    // Intent para configurar la alamra
                    Intent setAlarma = new Intent(AlarmClock.ACTION_SET_ALARM);
                    setAlarma.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(txtHora.getText().toString()));
                    setAlarma.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(txtMinuto.getText().toString()));
                    setAlarma.putExtra(AlarmClock.EXTRA_MESSAGE, editDescripcion.getText().toString());
                    //valida si existe una app para cargar de tipo Alarma y si los campos estan completos
                    if (setAlarma.resolveActivity(getPackageManager()) != null) {
                        startActivity(setAlarma);
                    } else {
                        Toast.makeText(PantallaAlarmas.this, "No existe aplicacion para esta accion",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PantallaAlarmas.this, "Debe seleccionar una hora y descripcion para la alarma",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        //Accion del boton Agregar Evento.
        btnAgregarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editUbicacion.getText().toString().isEmpty() && !editDescripcion.getText().toString().isEmpty()) {
                    Intent abrirCalendario = new Intent(Intent.ACTION_INSERT);
                    abrirCalendario.setData(CalendarContract.Events.CONTENT_URI);
                    abrirCalendario.putExtra(CalendarContract.Events.TITLE, editDescripcion.getText().toString());
                    abrirCalendario.putExtra(CalendarContract.Events.EVENT_LOCATION, editUbicacion.getText().toString());
                    abrirCalendario.putExtra(CalendarContract.Events.ALL_DAY, true);
                    //valida si existe una app para cargar de tipo Calendario y si los campos estan completos
                    if (abrirCalendario.resolveActivity(getPackageManager()) != null) {
                        startActivity(abrirCalendario);
                    } else {
                        Toast.makeText(PantallaAlarmas.this, "No existe aplicacion para esta accion",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PantallaAlarmas.this, "Debe seleccionar descripcion y ubicacion", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //accion boton Borrar
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //valida si la descripcion y la ubicacion estan completos
                if (!editDescripcion.getText().toString().isEmpty() || !editUbicacion.getText().toString().isEmpty()) {
                    // limpia campos
                    editDescripcion.getText().clear();
                    editUbicacion.getText().clear();
                } else {
                    Toast.makeText(PantallaAlarmas.this, "No hay descripcion y/o ubicacion que borrar", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnCargarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user;
                user = Utilidades.leerValor(PantallaAlarmas.this, "usuario");
                String descripcion = editDescripcion.getText().toString();
                String ubicacion = editUbicacion.getText().toString();
                String hora = txtHora.getText().toString();
                String minuto = txtMinuto.getText().toString();
                //validaciones para la carga de eventos
                if (!descripcion.isEmpty() && !ubicacion.isEmpty() && !hora.isEmpty()){
                    if (BD.insertarEventos(descripcion, ubicacion, hora, minuto, user)) {
                        Toast.makeText(PantallaAlarmas.this, "Evento cargado correctamente", Toast.LENGTH_SHORT).show();
                        Intent insertarInicio = new Intent(getApplicationContext(), PantallaInicio.class);
                        startActivity(insertarInicio);
                        finish();
                        } else {
                            Toast.makeText(PantallaAlarmas.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PantallaAlarmas.this, "Ingrese los valores del evento", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }

}