package com.petsaude.clinica.negocio;

import android.content.Context;

import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.clinica.persistencia.ClinicaDAO;
import com.petsaude.usuario.persistencia.UsuarioDAO;
import com.petsaude.vaga.dominio.Vaga;
import com.petsaude.vaga.persistencia.VagaDAO;

import java.util.ArrayList;

/**
 * Created by Matheus Uehara on 14/11/2015.
 */
public class ClinicaService {

    private ClinicaDAO clinicaDAO = ClinicaDAO.getInstance();

    public ClinicaService(Context context){
        this.clinicaDAO = ClinicaDAO.getInstance();
        this.clinicaDAO.setContextUp(context);
    }

    public Clinica getClinica(int id){
        return clinicaDAO.getClinica(id);
    }


}
