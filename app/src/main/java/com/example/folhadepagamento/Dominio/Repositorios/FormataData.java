package com.example.folhadepagamento.Dominio.Repositorios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormataData {

    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public Date converteParaData (String string){
        Date dataDate = null;
        try {
            dataDate = formato.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataDate;
    }

    public String converteParaString (Date data){
        return formato.format(data);
    }
}
