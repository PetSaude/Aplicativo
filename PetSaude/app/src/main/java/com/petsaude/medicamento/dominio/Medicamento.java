package com.petsaude.medicamento.dominio;

/**
 * Created by Rodolfo on 19/11/2015.
 */
public class Medicamento {
    private int id;
    private String finalidade;
    private String principioAtivo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }
}
