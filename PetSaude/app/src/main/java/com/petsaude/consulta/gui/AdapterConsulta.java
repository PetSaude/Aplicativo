package com.petsaude.consulta.gui;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.petsaude.R;
import java.util.ArrayList;

import com.petsaude.animal.dominio.Animal;
import com.petsaude.animal.persistencia.AnimalDAO;
import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.clinica.persistencia.ClinicaDAO;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.vaga.dominio.Vaga;


/**
 * Created by Matheus Uehara on 18/11/2015.
 */
public class AdapterConsulta extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<Vaga> itens;
    private String nomeAnimal;
    private String nomeClinica;


    public AdapterConsulta(Context context, ArrayList<Vaga> itens)
    {
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
    }


    public int getCount()
    {
        return itens.size();
    }

    public Vaga getItem(int position)
    {
        return itens.get(position);
    }


    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        Vaga item = itens.get(position);

        view = mInflater.inflate(R.layout.menu_vaga_list_view, null);

        ((TextView) view.findViewById(R.id.nome_clinica)).setText(nomeAnimal);
        ((TextView) view.findViewById(R.id.nome_animal)).setText(nomeClinica);
        ((TextView) view.findViewById(R.id.horario_consulta)).setText(item.getData());
        ((TextView) view.findViewById(R.id.status_consulta)).setText(item.getStatus());


        return view;
    }
}
