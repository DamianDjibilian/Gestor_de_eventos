package com.example.gestor_de_eventos;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class TimePicker extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendarioReloj = Calendar.getInstance();
            int hora = calendarioReloj.get(Calendar.HOUR_OF_DAY);
            int minuto = calendarioReloj.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hora, minuto,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }

