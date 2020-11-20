package com.example.folhadepagamento.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.folhadepagamento.Dominio.Entidades.FolhaPagamento;
import com.example.folhadepagamento.Dominio.Entidades.Usuarios;
import com.example.folhadepagamento.Dominio.Repositorios.CarregamentoDialog;
import com.example.folhadepagamento.Dominio.Repositorios.Fontes;
import com.example.folhadepagamento.Dominio.Repositorios.FormataData;
import com.example.folhadepagamento.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ornach.nobobutton.NoboButton;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private CFAlertDialog dialog;
    private TextView txtGerarFolhaPagamento, txtSemanas;
    private EditText edtMes, edtAno, edtFaltas;
    private NoboButton btnPerfil, btnGerarFolhaPagamento, btnConsultarFolhaPagamento, btnSair, btnConfirmar, btnCancelar;
    private Fontes fontes;
    private RelativeLayout rlSemanas;
    private RadioButton rb1, rb2, rb3, rb4;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private FormataData formataData;
    private CarregamentoDialog carregamentoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carregaWidgets();
        botoes();
    }

    private void carregaWidgets(){
        carregamentoDialog = new CarregamentoDialog();
        fontes = new Fontes(getApplicationContext());
        btnPerfil = findViewById(R.id.btnPerfil);
        btnGerarFolhaPagamento = findViewById(R.id.btnGerarFolhaPagamento);
        btnConsultarFolhaPagamento = findViewById(R.id.btnConsultarFolhaPagamento);
        btnSair = findViewById(R.id.btnSair);
    }

    private void botoes(){
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConsultaPerfilActivity.class);
                startActivity(intent);
            }
        });

        btnGerarFolhaPagamento.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                dialog = new CFAlertDialog.Builder(MainActivity.this)
                        .setTitle(null)
                        .setHeaderView(R.layout.gerar_folha_pagamento)
                        .setCornerRadius(50)
                        .create();
                dialog.show();
                txtGerarFolhaPagamento = dialog.findViewById(R.id.txtGerarFolhaPagamento);
                txtGerarFolhaPagamento.setTypeface(fontes.fonte);
                edtMes = dialog.findViewById(R.id.edtMes);
                edtMes.setTypeface(fontes.fonte);
                edtAno = dialog.findViewById(R.id.edtAno);
                edtAno.setTypeface(fontes.fonte);
                edtFaltas = dialog.findViewById(R.id.edtFaltas);
                edtFaltas.setTypeface(fontes.fonte);
                btnConfirmar = dialog.findViewById(R.id.btnConfirmar);
                btnCancelar = dialog.findViewById(R.id.btnCancelar);
                rlSemanas = dialog.findViewById(R.id.rlSemanas);
                txtSemanas = dialog.findViewById(R.id.txtSemanas);
                txtSemanas.setTypeface(fontes.fonte);
                rb1 = dialog.findViewById(R.id.rb1);
                rb1.setTypeface(fontes.fonte);
                rb2 = dialog.findViewById(R.id.rb2);
                rb2.setTypeface(fontes.fonte);
                rb3 = dialog.findViewById(R.id.rb3);
                rb3.setTypeface(fontes.fonte);
                rb4 = dialog.findViewById(R.id.rb4);
                rb4.setTypeface(fontes.fonte);

                edtFaltas.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!TextUtils.isEmpty(editable.toString()) || !editable.toString().trim().isEmpty()){
                            Integer Faltas = Integer.valueOf(String.valueOf(editable));
                            if (Faltas == 0){
                                rlSemanas.setVisibility(View.GONE);
                            } else if (Faltas > 0 && Faltas < 31){
                                rlSemanas.setVisibility(View.VISIBLE);
                            } else {
                                rlSemanas.setVisibility(View.GONE);
                            }
                        } else {
                            rlSemanas.setVisibility(View.GONE);
                        }
                    }
                });

                btnConfirmar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ValidaCampos()){
                            CalculaFolhaPagamento();
                        }
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        btnConsultarFolhaPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConsultaFolhaPagamentoActivity.class);
                startActivity(intent);
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                dialog = new CFAlertDialog.Builder(MainActivity.this)
                        .setTitle("Deseja Sair?")
                        .setHeaderView(null)
                        .setCornerRadius(50)
                        .addButton("CONFIRMAR", getColor(R.color.branco), getColor(R.color.cinco), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addButton("CANCELAR", getColor(R.color.branco), getColor(R.color.colorPrimary), CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }

    private boolean ValidaCampos(){
        String Mes = edtMes.getText().toString();
        String Ano = edtAno.getText().toString();
        String Faltas = edtFaltas.getText().toString();

        if(TextUtils.isEmpty(Mes) || Mes.trim().isEmpty()){
            edtMes.setError(getString(R.string.errCampoVazio));
            edtMes.requestFocus();
        } else if (Integer.parseInt(Mes) < 1 || Integer.parseInt(Mes) > 12){
            edtMes.setError(getString(R.string.errMesInvalido));
            edtMes.requestFocus();
        } else if (TextUtils.isEmpty(Ano) || Ano.trim().isEmpty()){
            edtAno.setError(getString(R.string.errCampoVazio));
            edtAno.requestFocus();
        } else if (Integer.parseInt(Ano) < 1900 || Integer.parseInt(Ano) > 2100){
            edtAno.setError(getString(R.string.errAnoInvalido));
            edtAno.requestFocus();
        } else if (TextUtils.isEmpty(Faltas) || Faltas.trim().isEmpty()){
            edtFaltas.setError(getString(R.string.errCampoVazio));
            edtFaltas.requestFocus();
        } else if (Integer.parseInt(Faltas) < 0 || Integer.parseInt(Faltas) > 30){
            edtFaltas.setError(getString(R.string.errFaltasInvalido));
            edtFaltas.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    private void CalculaFolhaPagamento(){
        carregamentoDialog.show(getSupportFragmentManager(), "Dialog");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        final FolhaPagamento folhaPagamento = new FolhaPagamento();

        firebaseFirestore.collection("Usuarios").document(firebaseUser.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            Usuarios user = new Usuarios();
                            user.setUid(documentSnapshot.getString("uid"));
                            user.setNome(documentSnapshot.getString("nome"));
                            user.setEmpresa(documentSnapshot.getString("empresa"));
                            user.setData_admissao(documentSnapshot.getDate("data_admissao"));
                            user.setSalario_bruto(documentSnapshot.getDouble("salario_bruto"));
                            user.setEmail(documentSnapshot.getString("email"));

                            folhaPagamento.setNome(user.getNome());
                            folhaPagamento.setEmpresa(user.getEmpresa());
                            folhaPagamento.setData_admissao(user.getData_admissao());
                            folhaPagamento.setSalario_bruto(user.getSalario_bruto());
                            folhaPagamento.setMes(Integer.valueOf(edtMes.getText().toString()));
                            folhaPagamento.setAno(Integer.valueOf(edtAno.getText().toString()));
                            folhaPagamento.setFaltas(Integer.valueOf(edtFaltas.getText().toString()));
                            if (folhaPagamento.getFaltas() > 0) {
                                if (rb1.isChecked()){
                                    folhaPagamento.setDias_dsr(1);
                                } else if (rb2.isChecked()){
                                    folhaPagamento.setDias_dsr(2);
                                } else if (rb3.isChecked()){
                                    folhaPagamento.setDias_dsr(3);
                                } else if (rb4.isChecked()){
                                    folhaPagamento.setDias_dsr(4);
                                }
                            } else {
                                folhaPagamento.setDias_dsr(0);
                            }

                            folhaPagamento.setMesAno(ConvertMesAno(folhaPagamento.getMes(), folhaPagamento.getAno()));
                            folhaPagamento.setDias_trabalhados(30);

                            Double salario_dia = folhaPagamento.getSalario_bruto() / 30;

                            folhaPagamento.setValor_faltas(salario_dia * folhaPagamento.getFaltas());
                            folhaPagamento.setValor_dsr(salario_dia * folhaPagamento.getDias_dsr());

                            folhaPagamento.setBase_inss(folhaPagamento.getSalario_bruto() - folhaPagamento.getValor_faltas() - folhaPagamento.getValor_dsr());
                            folhaPagamento.setInss(CalculaInss(folhaPagamento.getBase_inss()));
                            folhaPagamento.setBase_fgts(folhaPagamento.getBase_inss());
                            folhaPagamento.setFgts(folhaPagamento.getBase_fgts() * 0.08);
                            folhaPagamento.setTotal_vencimentos(folhaPagamento.getSalario_bruto());
                            folhaPagamento.setTotal_descontos(folhaPagamento.getValor_faltas() + folhaPagamento.getValor_dsr() + folhaPagamento.getInss());
                            folhaPagamento.setSalario_liquido(folhaPagamento.getTotal_vencimentos() - folhaPagamento.getTotal_descontos());

                            firebaseFirestore.collection("FolhaPagamento")
                                    .document(firebaseUser.getUid()+""+folhaPagamento.getMes()+folhaPagamento.getAno())
                                    .set(folhaPagamento)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dialog.dismiss();
                                            carregamentoDialog.dismiss();
                                            Intent intent = new Intent(MainActivity.this, FolhaPagamentoActivity.class);
                                            intent.putExtra("folhapagamento", folhaPagamento);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity.this, "Erro:" + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Erro:" + e, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private String ConvertMesAno(Integer mes, Integer ano){
        if (mes == 1){
            return "JANEIRO/" + ano;
        } else if (mes == 2){
            return "FEVEREIRO/" + ano;
        } else if (mes == 3){
            return "MARÇO/" + ano;
        } else if (mes == 4){
            return "ABRIL/" + ano;
        } else if (mes == 5){
            return "MAIO/" + ano;
        } else if (mes == 6){
            return "JUNHO/" + ano;
        } else if (mes == 7){
            return "JULHO/" + ano;
        } else if (mes == 8){
            return "AGOSTO/" + ano;
        } else if (mes == 9){
            return "SETEMBRO/" + ano;
        } else if (mes == 10){
            return "OUTUBRO/" + ano;
        } else if (mes == 11){
            return "NOVEMBRO/" + ano;
        } else if (mes == 12){
            return "DEZEMBRO/" + ano;
        } else {
            return "";
        }
    }

    private Double CalculaInss(Double base_inss){
        Double inss = 0.0;

        if (base_inss <= 1045){
            inss = base_inss * 0.075;
        } else if (base_inss > 1045 && base_inss <= 2089.60){
            inss = (1045 * 0.075) + ((base_inss - 1045) * 0.09);
        } else if (base_inss > 2089.60 && base_inss <= 3134.40){
            inss = (1045 * 0.075) + ((2089.60 - 1045) * 0.09) + ((base_inss - 2089.60) * 0.12);
        } else if (base_inss > 3134.40 && base_inss <= 6101.06){
            inss = (1045 * 0.075) + ((2089.60 - 1045) * 0.09) + ((3134.40 - 2089.60) * 0.12) + ((base_inss - 3134.40) * 0.14);
        }

        return inss;
    }
}