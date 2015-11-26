package com.petsaude.usuario.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.petsaude.R;
import com.petsaude.animal.dominio.Animal;
import com.petsaude.animal.gui.AdapterAnimal;
import com.petsaude.animal.gui.PerfilAnimalAct;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.negocio.UsuarioService;

import java.util.ArrayList;


public class PerfilAct extends AppCompatActivity implements AdapterView.OnItemClickListener {

    final UsuarioService negocio = new UsuarioService(PerfilAct.this);

    private ListView listView;
    private AdapterAnimal adapterListView;
    private ArrayList<Animal> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        setTitle("Meu Perfil");

        final TextView nome = (TextView) findViewById(R.id.nome_perfil);
        final TextView email = (TextView) findViewById(R.id.email_perfil);
        final Button editarEmail  = (Button) findViewById(R.id.editarEmail);
        final Button editarSenha  = (Button) findViewById(R.id.editarSenha);
        final Button excluirUsuario  = (Button) findViewById(R.id.excluirUsuario);

        nome.append(Session.getUsuarioLogado().getNome());
        email.append(Session.getUsuarioLogado().getEmail());

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (Session.getUsuarioLogado().getListaAnimais().size() == 0){
            final TextView mensagem = (TextView) findViewById(R.id.mensagem);
            mensagem.append("\n \nVocê ainda não possui nenhum animal cadastrado");
        }else{
            listView = (ListView) findViewById(R.id.listView_animal);
            listView.setOnItemClickListener(this);
            createListView();
        }

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        editarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PerfilAct.this, EditarEmailAct.class);
                startActivity(i);
            }
        });

        editarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PerfilAct.this, EditarSenhaAct.class);
                startActivity(i);
            }
        });

        excluirUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PerfilAct.this);
                alertDialogBuilder.setTitle("Excluir usuário");
                alertDialogBuilder.setMessage("Deseja excluir sua conta?");
                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            negocio.deleteUsuario(Session.usuarioLogado);
                            Toast.makeText(PerfilAct.this, "Usuario excluido com sucesso.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            i.setClass(PerfilAct.this, LoginAct.class);
                            startActivity(i);
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(PerfilAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();

            }
        });
    }

    private void createListView()
    {
        itens = Session.getUsuarioLogado().getListaAnimais();
        adapterListView = new AdapterAnimal(this, itens);
        listView.setAdapter(adapterListView);
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        Animal item = adapterListView.getItem(arg2);
        PerfilAnimalAct.setAnimalSeleciona(item);
        Intent i = new Intent();
        i.setClass(PerfilAct.this, PerfilAnimalAct.class);
        startActivity(i);
    }

}


