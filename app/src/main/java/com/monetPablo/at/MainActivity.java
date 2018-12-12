package com.monetPablo.at;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends BaseActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText confirmarSenha;
    private EditText cpf;

    private boolean validador =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showProgressDialog();
        nome = (EditText) findViewById(R.id.nome_subject);
        email = (EditText) findViewById(R.id.email_subject);
        senha = (EditText) findViewById(R.id.senha_subject);
        confirmarSenha = (EditText) findViewById(R.id.confirmar_senha_subject);
        cpf = (EditText) findViewById(R.id.cpf_subject);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(cpf,smf);
        cpf.addTextChangedListener(mtw);
        hideProgressDialog();
    }

    public boolean validar(String nomeString,String senhaString, String emailString, String confirmarSenhaString, String cpfString) {
        boolean passou = true;
        //Obirgar Preenchimento
        if (nomeString.trim().isEmpty()) {
            AutoValidador(nome, "O campo não pode ser vazio");
            passou = false;
        }
        if (confirmarSenhaString.trim().isEmpty()) {
            AutoValidador(confirmarSenha, "O campo não pode ser vazio");
            passou = false;
        }
        if (!emailString.trim().isEmpty() && !emailString.matches("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+")) {
            AutoValidador(email, "email invalido.");
            passou = false;
        }
        if (emailString.trim().isEmpty()) {
            AutoValidador(email, "O campo não pode ser vazio");
            passou = false;
        }
        if (cpfString.trim().isEmpty()) {
            AutoValidador(cpf, "O campo não pode ser vazio");
            passou = false;
        }
        //Detalhe Dos Campos
        if (!nomeString.trim().isEmpty() && !nomeString.matches("[a-zA-Z0-9 ]*")) {
            AutoValidador(nome, "Não pode conter caracteres especiais somente [A-z]");
            passou = false;
        }

        if (!cpfString.trim().isEmpty() && cpfString.trim().length() != 14) {
            AutoValidador(cpf, "CPF incompleto");
            passou = false;

        }
        if (senhaString.trim().isEmpty()) {
            AutoValidador(senha, "O campo não pode ser vazio");
            passou = false;
        }
        if (!senhaString.equals(confirmarSenhaString)){
            AutoValidador(confirmarSenha,"As Senhas precisam ser iguais.");
            passou = false;
        }
        return passou;
    }

    public void FinalizarCadastroButton(View view) {

        validador =true;

        final String nomeString = nome.getText().toString();
        final String senhaString = senha.getText().toString();
        final String emailString = email.getText().toString();
        String confirmarSenhaString = confirmarSenha.getText().toString();
        final String cpfString = cpf.getText().toString();

        validador = validar(nomeString,senhaString,emailString,confirmarSenhaString,cpfString);

        //Se tudo Ok
        if (validador==true){
           if( Salvar(nomeString,senhaString,emailString,confirmarSenhaString,cpfString) ){
               Intent intent = new Intent(this, MainPageActivity.class);
               startActivity(intent);
            }else{
               Snackbar snackbar = Snackbar
                       .make(findViewById(R.id.main), "Ocorreu um erro, contate ao suporte", Snackbar.LENGTH_LONG);
               snackbar.show();
            }
        }
    }

    public boolean Salvar(String nomeString,String senhaString, String emailString, String confirmarSenhaString, String cpfString){
        try {
            File outFile = new File(getFilesDir(), "cadastros.txt");
            OutputStream outputStream = new FileOutputStream(outFile, true);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            // escreve no arquivo
            writer.write("#\n");
            writer.write(nomeString+ "#" +senhaString + "#" + emailString + "#" + cpfString + "#");

            writer.close();
            return true;
        } catch (final FileNotFoundException exception){

            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.main), exception.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        } catch (final IOException exception){
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.main), exception.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
    }

    public void AutoValidador(EditText localValidação,String qualOErroNaValidação){
        validador=false;
        localValidação.setError(qualOErroNaValidação);
        localValidação.setFocusable(true);
        localValidação.requestFocus();
    }
}
