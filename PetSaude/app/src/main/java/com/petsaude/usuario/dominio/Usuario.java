package com.petsaude.usuario.dominio;


import com.petsaude.animal.dominio.Animal;
import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.vaga.dominio.Vaga;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Uehara on 07/10/2015.
 */

public class Usuario {
    private int id;
    private String login;
    private String email;
    private String nome;
    private String senha;
    private ArrayList<Animal> listaAnimais;
    private ArrayList<Vaga> listaConsultas;

    public Usuario(){}

    public ArrayList<Vaga> getListaConsultas() {
        return listaConsultas;
    }

    public void setListaConsultas(ArrayList<Vaga> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Animal> getListaAnimais() {
        return listaAnimais;
    }

    public void setListaAnimais(ArrayList<Animal> listaAnimais) {
        this.listaAnimais = listaAnimais;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getID(){
        return this.id;
    }
    public void setID(int id) {
        this.id = id;
    }

    public String getSenha(){
        return this.senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin(){
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
}