package com.petsaude.vaga.negocio;

import android.content.Context;

import com.petsaude.animal.dominio.Animal;
import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.clinica.persistencia.ClinicaDAO;
import com.petsaude.usuario.dominio.Usuario;
import com.petsaude.vaga.dominio.Vaga;
import com.petsaude.vaga.persistencia.VagaDAO;

import java.util.ArrayList;

/**
 * Created by Matheus Uehara on 17/11/2015.
 */
public class VagaService {

    private VagaDAO vagaDAO = VagaDAO.getInstance();

    public VagaService(Context context){
        this.vagaDAO = VagaDAO.getInstance();
        this.vagaDAO.setContextUp(context);
    }

    public ArrayList<Vaga> getVagas(Clinica clinica) throws Exception {
        return vagaDAO.retrieveVagas(clinica);
    }


    public void updateVaga(Usuario usuarioLogado, Animal animal, Vaga vaga) throws Exception {
        vagaDAO.updateVaga(usuarioLogado,animal,vaga);
    }
}
