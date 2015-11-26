package com.petsaude.prontuario.dominio;

import java.util.ArrayList;

/**
 * Created by igormlgomes on 19/11/15.
 */
public class Prontuario {

    private int id;
    private int idAnimal;
    //private ArrayList<Consulta> historico;

    public Prontuario() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

/*    public ArrayList<Consulta> getHistorico() {
        return historico;
    }

    public void setHistorico(ArrayList<Consulta> historico) {
        this.historico = historico;
    }

    */

}
