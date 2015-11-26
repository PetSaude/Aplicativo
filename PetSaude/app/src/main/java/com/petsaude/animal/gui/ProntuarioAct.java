package com.petsaude.animal.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.petsaude.R;
import com.petsaude.animal.dominio.Animal;
import com.petsaude.animal.persistencia.AnimalDAO;
import com.petsaude.medico.gui.MedicoAct;

import org.w3c.dom.Text;

public class ProntuarioAct extends AppCompatActivity {

    private static Animal animal;


    public static void setAnimal(Animal animalNovo){
        animal = animalNovo;
    }

    public static Animal getAnimal(){
        return animal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prontuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        setTitle("Prontuário: "+animal.getNome());

        final Button atualizarAnimal = (Button) findViewById(R.id.salvar_prontuario);
        final TextView nome = (TextView) findViewById(R.id.nome_animal);
        final TextView raca = (TextView) findViewById(R.id.raca);
        final TextView dataNascimento = (TextView) findViewById(R.id.data_nasc);
        final TextView peso = (TextView) findViewById(R.id.peso);
        final TextView prontuario = (TextView) findViewById(R.id.prontuario);

        final Spinner medicamento = (Spinner) findViewById(R.id.spinner_medicamento);
        final Spinner vacina= (Spinner) findViewById(R.id.spinner_vacina);



        if (animal.getNome().isEmpty()){
            nome.append("-");
        }else{
            nome.append(animal.getNome());
        }

        if (animal.getRaca().isEmpty()){
            raca.append("-");
        }else{
            raca.append(animal.getRaca());
        }

        if (animal.getDataNasc().isEmpty()){
            dataNascimento.append("-");
        }else{
            dataNascimento.append(animal.getDataNasc());
        }

        if ((""+animal.getPeso()).isEmpty()){
            peso.append("-");
        }else{
            peso.append(""+animal.getPeso()+"kg");
        }

        createSpinnerMedicamento();
        createSpinnerVacina();
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        atualizarAnimal.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String i = " - "+prontuario.getText().toString()+ "\n\n Vacinas - "+vacina.getSelectedItem()+"\n\n Medicamentos - "+medicamento.getSelectedItem();
                AnimalDAO.getInstance().atualizaProntuario(i, animal.getId());
                Toast.makeText(ProntuarioAct.this,"Prontuário atualizado com sucesso!",Toast.LENGTH_LONG).show();
                Intent act = new Intent(ProntuarioAct.this, MedicoAct.class);
                startActivity(act);
            }
        }));
    }

    public void createSpinnerVacina() {
        final Spinner vacina= (Spinner) findViewById(R.id.spinner_vacina);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vacina_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vacina.setAdapter(adapter);
    }

    public void createSpinnerMedicamento() {
        final Spinner medicamento = (Spinner) findViewById(R.id.spinner_medicamento);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.medicamento_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        medicamento.setAdapter(adapter);
    }
}
