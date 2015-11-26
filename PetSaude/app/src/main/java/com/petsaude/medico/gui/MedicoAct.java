package com.petsaude.medico.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.petsaude.R;
import com.petsaude.animal.dominio.Animal;
import com.petsaude.animal.gui.ProntuarioAct;
import com.petsaude.animal.negocio.AnimalService;
import com.petsaude.consulta.gui.AdapterConsulta;
import com.petsaude.medico.dominio.Medico;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.gui.LoginAct;
import com.petsaude.usuario.negocio.UsuarioService;
import com.petsaude.vaga.dominio.Vaga;
import com.petsaude.vaga.gui.AdapterVaga;

import java.util.ArrayList;

public class MedicoAct extends AppCompatActivity implements AdapterView.OnItemClickListener {

    final UsuarioService negocio = new UsuarioService(MedicoAct.this);
    final AnimalService negocioAnimal = new AnimalService(MedicoAct.this);


    private ListView listView;
    private AdapterConsulta adapterListView;
    private ArrayList<Vaga> itens;

    public void onQuit(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MedicoAct.this);

        alertDialogBuilder.setTitle("Sair");
        alertDialogBuilder.setMessage("Deseja sair do aplicativo?");

        alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Session.setMedicoLogado(null);
                Intent i = new Intent(MedicoAct.this, LoginAct.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    public void onBackPressed(){
        onQuit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);

        setTitle("Logado como Médico: " + Session.getMedicoLogado().getNome());

        final TextView crmv = (TextView) findViewById(R.id.crmv);

        crmv.append(Session.getMedicoLogado().getCRMV() + "");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        negocio.atualizaClinica(Session.getMedicoLogado());

        if (Session.getMedicoLogado().getListaConsultas().size() == 0){
            final TextView mensagem = (TextView) findViewById(R.id.consultas_text);
            mensagem.append("\n \nVocê ainda não possui nenhuma consulta");
        }else{
            listView = (ListView) findViewById(R.id.listView_consultas);
            listView.setOnItemClickListener(this);
            createListView();
        }
    }
    private void createListView()
    {
        itens = Session.getMedicoLogado().getListaConsultas();
        adapterListView = new AdapterConsulta(this, itens);
        listView.setAdapter(adapterListView);
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        if (adapterListView.getItem(arg2).getStatus().equals("CONFIRMADO")){
            ProntuarioAct.setAnimal(negocioAnimal.getAnimal(adapterListView.getItem(arg2).getIdAnimal()));

            if (ProntuarioAct.getAnimal() != null){
                Intent i = new Intent(MedicoAct.this, ProntuarioAct.class);
                startActivity(i);
            }else{
                Toast.makeText(MedicoAct.this,"Algo deu errado com o Animal",Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(MedicoAct.this,"Id do animal é: "+adapterListView.getItem(arg2).getIdAnimal(),Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MedicoAct.this,"A consulta selecionada ainda está em aberto",Toast.LENGTH_LONG).show();
        }
    }
}