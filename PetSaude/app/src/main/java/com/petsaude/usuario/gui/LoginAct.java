package com.petsaude.usuario.gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.petsaude.R;
import com.petsaude.medico.gui.MedicoAct;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.negocio.UsuarioService;


public class LoginAct extends Activity {

    final UsuarioService negocio = new UsuarioService(LoginAct.this);
    private String login;
    private String senha;

    public void limpaDados(EditText login,EditText senha){
        login.setText("");
        senha.setText("");
    };

    public void onBackPressed(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(android.os.Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        setContentView(R.layout.activity_login);
       final EditText edtlogin = (EditText) findViewById(R.id.login);
       final EditText edtSenha = (EditText) findViewById(R.id.senha);
        final Button entrar = (Button) findViewById(R.id.entrar);
        final Button registrar = (Button) findViewById(R.id.registrar);



        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = edtlogin.getText().toString();
                senha = edtSenha.getText().toString();
                new Sincronizar().execute();

            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent j = new Intent();
                j.setClass(LoginAct.this, CadastroAct.class);
                startActivity(j);
            }
        });
    }
    private class Sincronizar extends AsyncTask<Void,Void,String>{
        private ProgressDialog progressDialog = new ProgressDialog(LoginAct.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("carregando..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String mensagem =null;
            try {
                if(negocio.login(login, senha) ==true){
                    if (Session.getUsuarioLogado()!= null){
                        mensagem = "bem vindo!";
                        Intent i = new Intent();
                        i.setClass(LoginAct.this, MenuActivity.class);
                        startActivity(i);
                    }
                    if (Session.getMedicoLogado()!= null){
                        Intent i = new Intent();
                        mensagem = "bem vindo!";
                        i.setClass(LoginAct.this, MedicoAct.class);
                        startActivity(i);
                    }
                }
            } catch (Exception e) {
                mensagem = e.getMessage();
            }
            return mensagem;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Toast.makeText(LoginAct.this,result, Toast.LENGTH_SHORT).show();
        }
    }

}