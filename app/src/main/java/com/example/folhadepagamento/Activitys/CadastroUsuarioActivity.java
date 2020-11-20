package com.example.folhadepagamento.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ornach.nobobutton.NoboButton;

import java.util.Calendar;
import java.util.Date;

public class CadastroUsuarioActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Fontes fontes;
    private EditText edtNome, edtEmpresa, edtData, edtSalario, edtEmail, edtSenha, edtConfirmar_senha;
    private NoboButton btnConfirmar, btnLimpar;
    private CarregamentoDialog carregamentoDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private FormataData formataData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        carregaWidgets();
        botoes();
    }

    private void carregaWidgets(){
        fontes = new Fontes(getApplicationContext());
        edtNome = findViewById(R.id.edtNome);
        edtNome.setTypeface(fontes.fonte);
        edtEmpresa = findViewById(R.id.edtEmpresa);
        edtEmpresa.setTypeface(fontes.fonte);
        edtData = findViewById(R.id.edtData);
        edtData.setTypeface(fontes.fonte);
        edtSalario = findViewById(R.id.edtSalario);
        edtSalario.setTypeface(fontes.fonte);
        edtEmail = findViewById(R.id.edtEmail);
        edtEmail.setTypeface(fontes.fonte);
        edtSenha = findViewById(R.id.edtSenha);
        edtSenha.setTypeface(fontes.fonte);
        edtConfirmar_senha = findViewById(R.id.edtConfirmar_senha);
        edtConfirmar_senha.setTypeface(fontes.fonte);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnLimpar = findViewById(R.id.btnCancelar);
        carregamentoDialog = new CarregamentoDialog();

    }

    private void botoes(){
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtData.setError("", null);
                int year, month, day;
                if (edtData.getText().toString().equals("")){
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                } else {
                    formataData = new FormataData();
                    Date dateDate = formataData.converteParaData(edtData.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateDate);
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }
                DialogFragment datePicker = new DatePickerFragment(year, month, day);
                datePicker.show(getSupportFragmentManager(), "Data");
                ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        edtEmpresa.getWindowToken(), 0);
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidaCampos()){
                    CadastrarEmailSenha(v);
                }
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNome.setText("");
                edtNome.requestFocus();
                edtEmpresa.setText("");
                edtData.setText("");
                edtSalario.setText("");
                edtEmail.setText("");
                edtSenha.setText("");
                edtConfirmar_senha.setText("");
            }
        });
    }

    private boolean ValidaCampos(){
        String Nome = edtNome.getText().toString();
        String Empresa = edtEmpresa.getText().toString();
        String Data = edtData.getText().toString();
        String Salario = edtSalario.getText().toString();
        String Email = edtEmail.getText().toString();
        String Senha = edtSenha.getText().toString();
        String ConfirmarSenha = edtConfirmar_senha.getText().toString();

        if (TextUtils.isEmpty(Nome) || Nome.trim().isEmpty()){
            edtNome.setError(getString(R.string.errCampoVazio));
            edtNome.requestFocus();
        } else if (TextUtils.isEmpty(Empresa) || Empresa.trim().isEmpty()){
            edtEmpresa.setError(getString(R.string.errCampoVazio));
            edtEmpresa.requestFocus();
        } else if (TextUtils.isEmpty(Data) || Data.trim().isEmpty()){
            edtData.setError(getString(R.string.errCampoVazio));
            edtData.requestFocus();
        } else if (TextUtils.isEmpty(Salario) || Salario.trim().isEmpty()){
            edtSalario.setError(getString(R.string.errCampoVazio));
            edtSalario.requestFocus();
        } else if (TextUtils.isEmpty(Email) || Email.trim().isEmpty()){
            edtEmail.setError(getString(R.string.errCampoVazio));
            edtEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            edtEmail.setError(getString(R.string.errEmailInvalido));
            edtEmail.requestFocus();
        } else if (TextUtils.isEmpty(Senha) || Senha.trim().isEmpty()){
            edtSenha.setError(getString(R.string.errCampoVazio));
            edtSenha.requestFocus();
        } else if (Senha.length() < 6){
            edtSenha.setError(getString(R.string.errSenhaInvalida));
            edtSenha.requestFocus();
        } else if (TextUtils.isEmpty(ConfirmarSenha) || ConfirmarSenha.trim().isEmpty()){
            edtConfirmar_senha.setError(getString(R.string.errCampoVazio));
            edtConfirmar_senha.requestFocus();
        } else if (!Senha.equals(ConfirmarSenha)){
            edtConfirmar_senha.setError(getString(R.string.errSenhasDiferentes));
            edtConfirmar_senha.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    private void CadastrarEmailSenha(final View v){
        carregamentoDialog.show(getSupportFragmentManager(), "Dialog");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            CadastrarUsuario(v);
                        } else {
                            carregamentoDialog.dismiss();
                            edtEmail.setError(getString(R.string.errEmailExistente));
                            edtEmail.requestFocus();
                        }
                    }
                });
    }

    private void CadastrarUsuario(final View v){
        firebaseUser = firebaseAuth.getCurrentUser();

        Usuarios user = new Usuarios();
        user.setUid(firebaseUser.getUid());
        user.setNome(edtNome.getText().toString());
        user.setEmpresa(edtEmpresa.getText().toString());
        user.setData_admissao(formataData.converteParaData(edtData.getText().toString()));
        user.setSalario_bruto(Double.valueOf(edtSalario.getText().toString()));
        user.setEmail(edtEmail.getText().toString());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Usuarios").document(user.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        carregamentoDialog.dismiss();
                        firebaseUser.sendEmailVerification();
                        Snackbar.make(v, R.string.snkVerificarEmail, Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CadastroUsuarioActivity.this, "Erro:" + e, Toast.LENGTH_SHORT).show();
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
        edtData.setText(dataString);
    }
}
