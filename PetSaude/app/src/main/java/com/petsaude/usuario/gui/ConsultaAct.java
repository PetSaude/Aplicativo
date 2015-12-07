package com.petsaude.usuario.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.petsaude.R;
import com.petsaude.animal.dominio.Animal;
import com.petsaude.animal.gui.AdapterAnimal;
import com.petsaude.animal.gui.PerfilAnimalAct;
import com.petsaude.animal.negocio.AnimalService;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.negocio.UsuarioService;
import com.petsaude.vaga.dominio.Vaga;
import com.petsaude.vaga.gui.AdapterVaga;
import com.petsaude.vaga.negocio.VagaService;

import java.util.ArrayList;

public class ConsultaAct extends AppCompatActivity implements AdapterView.OnItemClickListener {

    final UsuarioService negocio = new UsuarioService(ConsultaAct.this);

    private ListView listView;
    private AdapterVaga adapterListView;
    private ArrayList<Vaga> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        setTitle("Minhas Consultas");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        new Sincronizar().execute();

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void createListView()
    {
        itens = Session.getUsuarioLogado().getListaConsultas();
        adapterListView = new AdapterVaga(this, itens);
        listView.setAdapter(adapterListView);
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
    }
    private class Sincronizar extends AsyncTask<Void,Void,Void> {
        private ProgressDialog progressDialog = new ProgressDialog(ConsultaAct.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("carregando consultas...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                negocio.atualizaClinica(Session.getUsuarioLogado());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void Void) {
            progressDialog.dismiss();
            if (Session.getUsuarioLogado().getListaConsultas().size() == 0){
                final TextView mensagem = (TextView) findViewById(R.id.mensagem);
                mensagem.append("\n \nVocê ainda não possui nenhuma consulta");
            }else{
                listView = (ListView) findViewById(R.id.listView_consulta);
                listView.setOnItemClickListener(ConsultaAct.this);
                createListView();
            }


        }

    }
}
