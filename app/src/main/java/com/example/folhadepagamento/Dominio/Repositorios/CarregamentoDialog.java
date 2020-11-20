package com.example.folhadepagamento.Dominio.Repositorios;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.folhadepagamento.R;

public class CarregamentoDialog extends DialogFragment {

    private Fontes fontes;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        fontes = new Fontes(getContext());
        Dialog carregamentoDialog = new Dialog(getContext());
        carregamentoDialog.setContentView(R.layout.tela_carregamento);
        TextView txtCarregando = carregamentoDialog.findViewById(R.id.txtCarregando);
        txtCarregando.setTypeface(fontes.fonte);
        if(carregamentoDialog.getWindow() != null){
            carregamentoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        carregamentoDialog.setCancelable(false);
        return carregamentoDialog;
    }
}
