package com.petsaude.clinica.dominio;

import com.petsaude.medico.dominio.Medico;
import com.petsaude.vaga.dominio.Vaga;

import java.util.ArrayList;

/**
 * Created by Matheus Uehara on 10/11/2015.
 */
public class Clinica {

    private int id;
    private String nome;
    private String endereco;
    private Double latitude;
    private Double longitude;
    private String numero;
    private int cnpj;
    private ArrayList<Vaga> vagas;
    private ArrayList<Medico> medicos;

    public ArrayList<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(ArrayList<Vaga> vagas) {
        this.vagas = vagas;
    }

    public ArrayList<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(ArrayList<Medico> medicos) {
        this.medicos = medicos;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    public Clinica(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
