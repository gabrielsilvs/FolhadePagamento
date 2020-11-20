package com.example.folhadepagamento.Dominio.Repositorios;

import android.content.Context;
import android.graphics.Typeface;

public class Fontes {

    public Typeface fonte, fonte_negrito, fonte_aplicativo;

    public Fontes(Context context) {
        this.fonte = Typeface.createFromAsset(context.getAssets(), "arvo.ttf");
        this.fonte_negrito = Typeface.createFromAsset(context.getAssets(), "arvo-bold.ttf");
        this.fonte_aplicativo = Typeface.createFromAsset(context.getAssets(), "forte.ttf");
    }
}
