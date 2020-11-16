package com.example.gestor_de_eventos;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;
import androidx.appcompat.app.AppCompatActivity;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.List;

public class PantallaInicio extends AppCompatActivity implements View.OnClickListener {
    Button btnCargar;
    BaseDeDatos BD;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);
        btnCargar = (Button)findViewById(R.id.btnCargar);
        btnCargar.setOnClickListener( this);
        BD = new BaseDeDatos(this);
        cargarLista();
    }

    private void cargarLista() {
        String user;
        user = Utilidades.leerValor(PantallaInicio.this, "usuario");
        String queryGastos = "select * from eventos where usuario = " + "'"+ user +"'";
        BaseDeDatos bd = new BaseDeDatos(getBaseContext());
        SQLiteDatabase BDControl = bd.getReadableDatabase();
        // contador registros
        Cursor registros = BDControl.rawQuery(queryGastos, null);
        int cantidad = registros.getCount();
        int i = 0;
        String[] contadorRegistros = new String[cantidad];
        if (registros.moveToFirst()) {
            do {
                String linea = registros.getString(0) + " - " + registros.getString(1) + " - " + registros.getString(2) + ":" + registros.getString(3);
                contadorRegistros[i] = linea;
                i++;
            } while (registros.moveToNext());
        }
        if (cantidad == 0) {
            Toast.makeText(PantallaInicio.this, "No hay eventos cargados", Toast.LENGTH_SHORT).show();
        } else {
            final ArrayAdapter<String> adapterLista = new ArrayAdapter<String>(this, R.layout.columnas,
                    contadorRegistros);
            final ListView listaEventos = (ListView) findViewById(R.id.listaEventos);
            listaEventos.setAdapter(adapterLista);
        }
    }
    //acciones de los botones
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCargar:
                Intent cargar = new Intent(PantallaInicio.this, PantallaAlarmas.class);
                startActivity(cargar);
                finish();
                break;
        }
    }

}
