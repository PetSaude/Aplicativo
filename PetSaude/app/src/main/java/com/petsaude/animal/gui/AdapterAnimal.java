package com.petsaude.animal.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.petsaude.R;
import com.petsaude.animal.dominio.Animal;

import java.util.ArrayList;

/**
 * Created by Matheus Uehara on 14/11/2015.
 */
public class AdapterAnimal extends BaseAdapter{

    private LayoutInflater mInflater;
    private ArrayList<Animal> itens;

    public AdapterAnimal(Context context, ArrayList<Animal> itens)
    {
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
    }


    public int getCount()
    {
        return itens.size();
    }

    public Animal getItem(int position)
    {
        return itens.get(position);
    }


    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        Animal item = itens.get(position);
        view = mInflater.inflate(R.layout.menu_animal_list_view, null);
        ((TextView) view.findViewById(R.id.nome_animal)).setText(item.getNome());
        ((TextView) view.findViewById(R.id.raca_animal)).setText(item.getRaca());

        return view;
    }
}