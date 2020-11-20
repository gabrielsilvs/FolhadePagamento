package com.example.folhadepagamento.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.folhadepagamento.Dominio.Repositorios.CarregamentoDialog;
import com.example.folhadepagamento.Dominio.Repositorios.Fontes;
import com.example.folhadepagamento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ornach.nobobutton.NoboButton;

public class LoginActivity extends AppCompatActivity {

    private TextView txtFolhadePagamento, txtEsqueci_minha_senha, txtRedefinirSenha;
    private EditText edtEmail, edtSenha, edtRedefinirSenha;
    private NoboButton btnLogin, btnCadastrar, btnRedefinir, btnCancelar;
    private CarregamentoDialog carregamentoDialog;
    private Fontes fontes;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        carregaWidgets();
        botoes();
    }

    private void carregaWidgets(){
        fontes = new Fontes(getApplicationContext());
        txtFolhadePagamento = findViewById(R.id.txtFolhadePagamento);
        txtFolhadePagamento.setTypeface(fontes.fonte_aplicativo);
        txtEsqueci_minha_senha = findViewById(R.id.txtEsqueci_minha_senha);
        txtEsqueci_minha_senha.setTypeface(fontes.fonte_negrito);
        edtEmail = findViewById(R.id.edtEmail);
        edtEmail.setTypeface(fontes.fonte);
        edtSenha = findViewById(R.id.edtSenha);
        edtSenha.setTypeface(fontes.fonte);
        btnLogin = findViewById(R.id.btnLogin);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        carregamentoDialog = new CarregamentoDialog();
    }

    private void botoes(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidaCampos()){
                    EfetuaLogin(v);
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });

        txtEsqueci_minha_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CFAlertDialog dialog = new CFAlertDialog.Builder(LoginActivity.this)
                        .setTitle(null)
                        .setHeaderView(R.layout.redefinir_senha)
                        .setCornerRadius(50)
                        .create();
                dialog.show();
                txtRedefinirSenha = dialog.findViewById(R.id.txtRedefinirSenha);
                txtRedefinirSenha.setTypeface(fontes.fonte_negrito);
                edtRedefinirSenha = dialog.findViewById(R.id.edtRedefinirSenha);
                edtRedefinirSenha.setTypeface(fontes.fonte);
                btnRedefinir = dialog.findViewById(R.id.btnRedefinir);
                btnCancelar = dialog.findViewById(R.id.btnCancelar);
                carregamentoDialog = new CarregamentoDialog();

                btnRedefinir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (TextUtils.isEmpty(edtRedefinirSenha.getText().toString()) || edtRedefinirSenha.getText().toString().trim().isEmpty()){
                            edtRedefinirSenha.setError(getString(R.string.errCampoVazio));
                            edtRedefinirSenha.requestFocus();
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtRedefinirSenha.getText().toString()).matches()){
                            edtRedefinirSenha.setError(getString(R.string.errEmailInvalido));
                            edtRedefinirSenha.requestFocus();
                        } else {
                            carregamentoDialog.show(getSupportFragmentManager(), "Dialog");
                            firebaseAuth = FirebaseAuth.getInstance();
                            firebaseAuth.sendPasswordResetEmail(edtRedefinirSenha.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(LoginActivity.this, R.string.tstEmailEnviado, Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                                carregamentoDialog.dismiss();
                                            } else {
                                                carregamentoDialog.dismiss();
                                                edtRedefinirSenha.setError(getString(R.string.errEmailInexistente));
                                                edtRedefinirSenha.requestFocus();
                                            }
                                        }
                                    });
                        }
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private boolean ValidaCampos(){
        String Email = edtEmail.getText().toString();
        String Senha = edtSenha.getText().toString();

        if(TextUtils.isEmpty(Email) || Email.trim().isEmpty()){
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
        } else {
            return true;
        }
        return false;
    }

    private void EfetuaLogin(final View v){
        carregamentoDialog.show(getSupportFragmentManager(),"Dialog");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            firebaseUser = firebaseAuth.getCurrentUser();
                            if(firebaseUser.isEmailVerified()){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                firebaseUser.sendEmailVerification();
                                carregamentoDialog.dismiss();
                                Snackbar.make(v, R.string.snkEmailNaoVerificado, Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            carregamentoDialog.dismiss();
                            Snackbar.make(v, R.string.snkEmailSenhaInvalidos, Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null && firebaseUser.isEmailVerified()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}