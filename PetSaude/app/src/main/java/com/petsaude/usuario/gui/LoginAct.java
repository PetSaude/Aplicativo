package com.petsaude.usuario.gui;

import android.app.Activity;
import android.content.Intent;
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

        final EditText login = (EditText) findViewById(R.id.login);
        final EditText senha = (EditText) findViewById(R.id.senha);
        final Button entrar = (Button) findViewById(R.id.entrar);
        final Button registrar = (Button) findViewById(R.id.registrar);


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (negocio.login(login.getText().toString(), senha.getText().toString()) ==true) {
                        if (Session.getUsuarioLogado()!= null){
                            Toast.makeText(LoginAct.this, "Logado com sucesso.", Toast.LENGTH_SHORT).show();
                            limpaDados(login, senha);
                            Intent i = new Intent();
                            i.setClass(LoginAct.this, MenuActivity.class);
                            startActivity(i);
                        }
                        if (Session.getMedicoLogado()!= null){
                            Toast.makeText(LoginAct.this, "Logado com sucesso.", Toast.LENGTH_SHORT).show();
                            limpaDados(login, senha);
                            Intent i = new Intent();
                            i.setClass(LoginAct.this, MedicoAct.class);
                            startActivity(i);
                        }
                    }
                    ;
                } catch (Exception e) {
                    Toast.makeText(LoginAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                login.requestFocus();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                limpaDados(login, senha);
                Intent j = new Intent();
                j.setClass(LoginAct.this, CadastroAct.class);
                startActivity(j);
            }
        });
    }

}