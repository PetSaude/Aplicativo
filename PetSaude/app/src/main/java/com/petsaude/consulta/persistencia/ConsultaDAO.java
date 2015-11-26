package com.petsaude.consulta.persistencia;

import com.petsaude.database.DAO;
import com.petsaude.database.PetSaudeSQLiteHelper;

import android.content.ContentValues;
import android.database.Cursor;

import com.petsaude.animal.dominio.Animal;
import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.database.DAO;
import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.medico.persistencia.MedicoDAO;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.dominio.Usuario;
import com.petsaude.vaga.dominio.Vaga;


import java.util.ArrayList;

/**
 * Created by Matheus Uehara on 17/11/2015.
 */
public class ConsultaDAO extends DAO {

    private static final ConsultaDAO instance = new ConsultaDAO();

    private ConsultaDAO() {
        super();
    }

    public static ConsultaDAO getInstance() {
        return instance;
    }

    private static PetSaudeSQLiteHelper database = getDataBaseHelper();

    public void cadastraConsulta(Usuario usuarioLogado, Animal animal, Vaga vaga){
        open();
        ContentValues initialValues = new ContentValues();
        initialValues.put(database.getNomeAnimal(), animal.getNome());
        initialValues.put(database.getRacaAnimal(), animal.getRaca());
        initialValues.put(database.getDataNascAnimal(), animal.getDataNasc());
        initialValues.put(database.getPesoAnimal(), animal.getPeso());
        initialValues.put(database.getIdUsuarioAnimal(), animal.getUsuario());
        getDatabase().insert(database.getTableNameAnimal(), null, initialValues);
        close();
    }

}