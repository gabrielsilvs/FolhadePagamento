package com.example.folhadepagamento.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.folhadepagamento.Dominio.Entidades.FolhaPagamento;
import com.example.folhadepagamento.Dominio.Repositorios.FormataData;
import com.example.folhadepagamento.R;

public class FolhaPagamentoActivity extends AppCompatActivity {

    private TextView txtEmpresa, txtMesAno, txtNome, txtAdmissao, txtDias_trabalhados, txtSalario_bruto, lblFaltas, txtFaltas, txtValor_Faltas, lblDsr, txtDsr, txtValor_Dsr, txtValor_Inss, txtTotal_Vencimentos, txtTotal_Descontos, txtTotal_Liquido, txtSalario_Base, txtBase_Inss, txtBase_Fgts, txtFgts;
    private FormataData formataData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folha_pagamento);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        carregaWidgets();
        carregaDados();
    }

    private void carregaWidgets(){
        formataData = new FormataData();
        txtEmpresa = findViewById(R.id.txtEmpresa);
        txtMesAno = findViewById(R.id.txtMesAno);
        txtNome = findViewById(R.id.txtNome);
        txtAdmissao = findViewById(R.id.txtAdmissao);
        txtDias_trabalhados = findViewById(R.id.txtDias_trabalhados);
        txtSalario_bruto = findViewById(R.id.txtSalario_bruto);
        lblFaltas = findViewById(R.id.lblFaltas);
        txtFaltas = findViewById(R.id.txtFaltas);
        txtValor_Faltas = findViewById(R.id.txtValor_Faltas);
        lblDsr = findViewById(R.id.lblDsr);
        txtDsr = findViewById(R.id.txtDsr);
        txtValor_Dsr = findViewById(R.id.txtValor_Dsr);
        txtValor_Inss = findViewById(R.id.txtValor_Inss);
        txtTotal_Vencimentos = findViewById(R.id.txtTotal_Vencimentos);
        txtTotal_Descontos = findViewById(R.id.txtTotal_Descontos);
        txtTotal_Liquido = findViewById(R.id.txtTotal_Liquido);
        txtSalario_Base = findViewById(R.id.txtSalario_Base);
        txtBase_Inss = findViewById(R.id.txtBase_Inss);
        txtBase_Fgts = findViewById(R.id.txtBase_Fgts);
        txtFgts = findViewById(R.id.txtFgts);
    }

    private void carregaDados(){
        FolhaPagamento folhaPagamento = (FolhaPagamento) getIntent().getSerializableExtra("folhapagamento");

        if (folhaPagamento.getFaltas() > 0){
            txtEmpresa.setText(folhaPagamento.getEmpresa());
            txtMesAno.setText(folhaPagamento.getMesAno());
            txtNome.setText(folhaPagamento.getNome());
            txtAdmissao.setText(formataData.converteParaString(folhaPagamento.getData_admissao()));
            txtDias_trabalhados.setText(format(folhaPagamento.getDias_trabalhados()));
            txtSalario_bruto.setText(format(folhaPagamento.getSalario_bruto()));
            lblFaltas.setVisibility(View.VISIBLE);
            txtFaltas.setText(format(folhaPagamento.getFaltas()));
            txtFaltas.setVisibility(View.VISIBLE);
            txtValor_Faltas.setText(format(folhaPagamento.getValor_faltas()));
            txtValor_Faltas.setVisibility(View.VISIBLE);
            lblDsr.setVisibility(View.VISIBLE);
            txtDsr.setText(format(folhaPagamento.getDias_dsr()));
            txtDsr.setVisibility(View.VISIBLE);
            txtValor_Dsr.setText(format(folhaPagamento.getValor_dsr()));
            txtValor_Dsr.setVisibility(View.VISIBLE);
            txtValor_Inss.setText(format(folhaPagamento.getInss()));
            txtTotal_Vencimentos.setText(format(folhaPagamento.getTotal_vencimentos()));
            txtTotal_Descontos.setText(format(folhaPagamento.getTotal_descontos()));
            txtTotal_Liquido.setText(format(folhaPagamento.getSalario_liquido()));
            txtSalario_Base.setText(format(folhaPagamento.getSalario_bruto()));
            txtBase_Inss.setText(format(folhaPagamento.getBase_inss()));
            txtBase_Fgts.setText(format(folhaPagamento.getBase_fgts()));
            txtFgts.setText(format(folhaPagamento.getFgts()));
        } else {
            txtEmpresa.setText(folhaPagamento.getEmpresa());
            txtMesAno.setText(folhaPagamento.getMesAno());
            txtNome.setText(folhaPagamento.getNome());
            txtAdmissao.setText(formataData.converteParaString(folhaPagamento.getData_admissao()));
            txtDias_trabalhados.setText(format(folhaPagamento.getDias_trabalhados()));
            txtSalario_bruto.setText(format(folhaPagamento.getSalario_bruto()));
            lblFaltas.setVisibility(View.GONE);
            txtFaltas.setText(format(folhaPagamento.getFaltas()));
            txtFaltas.setVisibility(View.GONE);
            txtValor_Faltas.setText(format(folhaPagamento.getValor_faltas()));
            txtValor_Faltas.setVisibility(View.GONE);
            lblDsr.setVisibility(View.GONE);
            txtDsr.setText(format(folhaPagamento.getDias_dsr()));
            txtDsr.setVisibility(View.GONE);
            txtValor_Dsr.setText(format(folhaPagamento.getValor_dsr()));
            txtValor_Dsr.setVisibility(View.GONE);
            txtValor_Inss.setText(format(folhaPagamento.getInss()));
            txtTotal_Vencimentos.setText(format(folhaPagamento.getTotal_vencimentos()));
            txtTotal_Descontos.setText(format(folhaPagamento.getTotal_descontos()));
            txtTotal_Liquido.setText(format(folhaPagamento.getSalario_liquido()));
            txtSalario_Base.setText(format(folhaPagamento.getSalario_bruto()));
            txtBase_Inss.setText(format(folhaPagamento.getBase_inss()));
            txtBase_Fgts.setText(format(folhaPagamento.getBase_fgts()));
            txtFgts.setText(format(folhaPagamento.getFgts()));
        }
    }

    public static String format(double x) {
        return String.format("%.2f", x);
    }
}