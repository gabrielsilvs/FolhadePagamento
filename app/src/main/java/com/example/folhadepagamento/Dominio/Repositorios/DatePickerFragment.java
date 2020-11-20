package com.example.folhadepagamento.Dominio.Repositorios;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.folhadepagamento.R;


public class DatePickerFragment extends DialogFragment {

    private int year, month, day;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), R.style.CalendarTheme,(DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }

    public DatePickerFragment(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
