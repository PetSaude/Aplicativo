package com.petsaude.medico.dominio;

import com.petsaude.usuario.dominio.Usuario;

/**
 * Created by Matheus Uehara on 17/11/2015.
 */
public class Medico extends Usuario {

    private int CRMV;
    private int id_clinica;

    public Medico (Usuario usuario,int crmv){
        this.setCRMV(crmv);
        this.setId(usuario.getId());
        this.setLogin(usuario.getLogin());
        this.setSenha(usuario.getSenha());
    }

    public Medico(){

    }

    public int getCRMV() {
        return CRMV;
    }

    public void setCRMV(int CRMV) {
        this.CRMV = CRMV;
    }

    public int getId_clinica() {
        return id_clinica;
    }

    public void setId_clinica(int id_clinica) {
        this.id_clinica = id_clinica;
    }

}
