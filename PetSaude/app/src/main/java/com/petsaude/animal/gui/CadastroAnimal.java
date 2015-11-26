package com.petsaude.animal.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.petsaude.R;
import com.petsaude.animal.dominio.Animal;
import com.petsaude.animal.negocio.AnimalService;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.gui.MenuActivity;
import com.petsaude.util.Mask;

public class CadastroAnimal extends AppCompatActivity {

    final AnimalService negocio = new AnimalService(CadastroAnimal.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal);

        setTitle("Cadastro de Animal");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final EditText nome = (EditText) findViewById(R.id.editTextNome);
        final EditText dataNasc = (EditText) findViewById(R.id.editTextDataNasc);
        final Spinner raca = (Spinner) findViewById(R.id.spinnerRaca);
        final EditText peso = (EditText) findViewById(R.id.editTextPeso);

        final Button salvar = (Button) findViewById(R.id.buttonSalvar);
        dataNasc.addTextChangedListener(Mask.insert("##/##/####", dataNasc));

        createSpinnerRaca();

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animal animal = new Animal();
                animal.setNome(nome.getText().toString());
                animal.setDataNasc(dataNasc.getText().toString());
                animal.setRaca(raca.getSelectedItem().toString());
                animal.setPeso(Integer.valueOf(peso.getText().toString()));
                animal.setUsuario(Session.getUsuarioLogado().getID());


                try {
                    negocio.adicionar(animal);
                    Toast.makeText(CadastroAnimal.this, "Animal cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.setClass(CadastroAnimal.this, MenuActivity.class);
                    startActivity(i);

                } catch (Exception e) {
                    Toast.makeText(CadastroAnimal.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void createSpinnerRaca() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerRaca);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.raca_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }


}
