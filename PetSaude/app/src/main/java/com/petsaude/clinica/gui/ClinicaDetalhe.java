package com.petsaude.clinica.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.petsaude.R;
import com.petsaude.animal.dominio.Animal;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.gui.MenuActivity;
import com.petsaude.usuario.negocio.UsuarioService;
import com.petsaude.vaga.dominio.Vaga;
import com.petsaude.vaga.negocio.VagaService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClinicaDetalhe extends AppCompatActivity implements com.petsaude.util.calendario.CalendarView.OnDispatchDateSelectListener{

    private TextView mTextDate;
    private SimpleDateFormat mFormat;
    public static int id;

    private static String dataSelecionada;
    final VagaService vagaNegocio = new VagaService(ClinicaDetalhe.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clinica_detalhe);

        final Spinner animais = (Spinner) findViewById(R.id.selecao_animal);

        final Spinner vagas = (Spinner) findViewById(R.id.selecao_vaga);

        final Button consulta = (Button) findViewById(R.id.consulta);

        setTitle(Session.getClinicaSelecionada().getNome());

        mTextDate = (TextView) findViewById(R.id.display_date);

        mFormat = new SimpleDateFormat("EEEE d MMMM yyyy");

        ((com.petsaude.util.calendario.CalendarView) findViewById(R.id.calendar)).setOnDispatchDateSelectListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        createSpinnerAnimais();
        createSpinnerVaga();

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!vagas.getSelectedItem().equals("A clinica não tem vagas disponíveis")) & (!animais.getSelectedItem().equals("Você não possui animais cadastrados"))){
                    Animal animal = Session.getUsuarioLogado().getListaAnimais().get(animais.getSelectedItemPosition());
                    Vaga vaga = Session.getClinicaSelecionada().getVagas().get(vagas.getSelectedItemPosition());
                    try {
                        vagaNegocio.updateVaga(Session.getUsuarioLogado(), animal, vaga);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ClinicaDetalhe.this,"Consulta Marcada com Sucesso",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ClinicaDetalhe.this,MenuActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(ClinicaDetalhe.this,"Verifique os dados submetidos",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onDispatchDateSelect(Date date) {
        mTextDate.setText(mFormat.format(date));
        dataSelecionada = mFormat.format(date);
    }

    public void createSpinnerAnimais() {
        Spinner spinner = (Spinner) findViewById(R.id.selecao_animal);
        ArrayList<String> animais = new ArrayList<String>();
        for(Animal animal:Session.getUsuarioLogado().getListaAnimais()){
           animais.add(animal.getNome());
        }
        if (animais.isEmpty()){
            animais.add("Você não possui animais cadastrados");
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, animais);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void createSpinnerVaga() {
        Spinner spinner = (Spinner) findViewById(R.id.selecao_vaga);
        ArrayList<String> vagas = new ArrayList<String>();
        for(Vaga vaga:Session.getClinicaSelecionada().getVagas()){
            vagas.add(vaga.getMedico().getNome()+"- "+vaga.getData());
        }
        if (vagas.isEmpty()){
            vagas.add("A clinica não tem vagas disponíveis");
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vagas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
