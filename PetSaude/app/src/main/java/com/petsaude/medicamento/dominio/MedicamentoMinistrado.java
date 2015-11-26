package com.petsaude.medicamento.dominio;

import com.petsaude.util.Date;

/**
 * Created by Rodolfo on 19/11/2015.
 */
public class MedicamentoMinistrado {
    private int id;
    //private Prontuario prontuario;
    private Medicamento medicamento;
    private String inicio;
    private String fim;
    private String quantidade;
    private String intervalo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }
}
