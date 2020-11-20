package com.example.folhadepagamento.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.folhadepagamento.Dominio.Entidades.Usuarios;
import com.example.folhadepagamento.Dominio.Repositorios.CarregamentoDialog;
import com.example.folhadepagamento.Dominio.Repositorios.DatePickerFragment;
import com.example.folhadepagamento.Dominio.Repositorios.Fontes;
import com.example.folhadepagamento.Dominio.Repositorios.FormataData;
import com.example.folhadepagamento.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ornach.nobobutton.NoboButton;

import java.util.Calendar;
import java.util.Date;

public class ConsultaPerfilActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText edtAlterarNome, edtAlterarEmpresa, edtAlterarData, edtAlterarSalario, edtEmail;
    private NoboButton btnAlterar, btnCancelar;
    private Fontes fontes;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FormataData formataData;
    private Usuarios user;
    private CarregamentoDialog carregamentoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_perfil);
        carregaWidgets();
        carregaDados();
        botoes();
    }

    private void carregaWidgets(){
        fontes = new Fontes(getApplicationContext());
        edtAlterarNome = findViewById(R.id.edtAlterarNome);
        edtAlterarNome.setTypeface(fontes.fonte);
        edtAlterarNome.setEnabled(false);
        edtAlterarEmpresa = findViewById(R.id.edtAlterarEmpresa);
        edtAlterarEmpresa.setTypeface(fontes.fonte);
        edtAlterarEmpresa.setEnabled(false);
        edtAlterarData = findViewById(R.id.edtAlterarData);
        edtAlterarData.setTypeface(fontes.fonte);
        edtAlterarData.setEnabled(false);
        edtAlterarSalario = findViewById(R.id.edtAlterarSalario);
        edtAlterarSalario.setTypeface(fontes.fonte);
        edtAlterarSalario.setEnabled(false);
        edtEmail = findViewById(R.id.edtEmail);
        edtEmail.setTypeface(fontes.fonte);
        edtEmail.setEnabled(false);
        btnAlterar = findViewById(R.id.btnAlterar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setVisibility(View.GONE);
        carregamentoDialog = new CarregamentoDialog();
    }

    private void carregaDados(){
        carregamentoDialog.show(getSupportFragmentManager(), "Dialog");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Usuarios").document(firebaseUser.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            user = new Usuarios();
                            user.setUid(documentSnapshot.getString("uid"));
                            user.setNome(documentSnapshot.getString("nome"));
                            user.setEmpresa(documentSnapshot.getString("empresa"));
                            user.setData_admissao(documentSnapshot.getDate("data_admissao"));
                            user.setSalario_bruto(documentSnapshot.getDouble("salario_bruto"));
                            user.setEmail(documentSnapshot.getString("email"));

                            formataData = new FormataData();
                            edtAlterarNome.setText(user.getNome());
                            edtAlterarEmpresa.setText(user.getEmpresa());
                            edtAlterarData.setText(formataData.converteParaString(user.getData_admissao()));
                            edtAlterarSalario.setText(user.getSalario_bruto().toString());
                            edtEmail.setText(user.getEmail());
                            carregamentoDialog.dismiss();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ConsultaPerfilActivity.this, "Erro:" + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void botoes(){
        edtAlterarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAlterarData.setError("", null);
                int year, month, day;
                if (edtAlterarData.getText().toString().equals("")){
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                } else {
                    formataData = new FormataData();
                    Date dateDate = formataData.converteParaData(edtAlterarData.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateDate);
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }
                DialogFragment datePicker = new DatePickerFragment(year, month, day);
                datePicker.show(getSupportFragmentManager(), "Data");
                ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        edtAlterarEmpresa.getWindowToken(), 0);
            }
        });

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (btnAlterar.getText().toString().equals(getString(R.string.btnConfirmar))){
                    carregamentoDialog.show(getSupportFragmentManager(), "Dialog");
                    final Usuarios user_update = new Usuarios();
                    user_update.setUid(user.getUid());
                    user_update.setEmail(user.getEmail());
                    user_update.setNome(edtAlterarNome.getText().toString());
                    user_update.setEmpresa(edtAlterarEmpresa.getText().toString());
                    user_update.setData_admissao(formataData.converteParaData(edtAlterarData.getText().toString()));
                    user_update.setSalario_bruto(Double.valueOf(edtAlterarSalario.getText().toString()));

                    firebaseFirestore.collection("Usuarios").document(firebaseUser.getUid()).set(user_update)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    carregamentoDialog.dismiss();
                                    Snackbar.make(view, R.string.snkCadastroAlterado, Snackbar.LENGTH_LONG).show();
                                    btnAlterar.setText(getString(R.string.btnAlterar));
                                    edtAlterarNome.setEnabled(false);
                                    edtAlterarEmpresa.setEnabled(false);
                                    edtAlterarData.setEnabled(false);
                                    edtAlterarSalario.setEnabled(false);
                                    btnCancelar.setVisibility(View.GONE);
                                    user = user_update;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ConsultaPerfilActivity.this, "Erro:" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                if (btnAlterar.getText().toString().equals(getString(R.string.btnAlterar))){
                    btnAlterar.setText(getString(R.string.btnConfirmar));
                    edtAlterarNome.setEnabled(true);
                    edtAlterarEmpresa.setEnabled(true);
                    edtAlterarData.setEnabled(true);
                    edtAlterarSalario.setEnabled(true);
                    btnCancelar.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAlterar.setText(getString(R.string.btnAlterar));
                edtAlterarNome.setText(user.getNome());
                edtAlterarNome.setEnabled(false);
                edtAlterarEmpresa.setText(user.getEmpresa());
                edtAlterarEmpresa.setEnabled(false);
                edtAlterarData.setText(formataData.converteParaString(user.getData_admissao()));
                edtAlterarData.setEnabled(false);
                edtAlterarSalario.setText(user.getSalario_bruto().toString());
                edtAlterarSalario.setEnabled(false);
                btnCancelar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        formataData = new FormataData();
        String dataString = formataData.converteParaString(calendar.getTime());
        edtAlterarData.setText(dataString);
    }
}