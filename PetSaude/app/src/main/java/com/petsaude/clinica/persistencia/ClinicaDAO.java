package com.petsaude.clinica.persistencia;

import android.database.Cursor;

import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.database.DAO;
import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.usuario.dominio.Session;

import java.util.ArrayList;

/**
 * Created by Matheus Uehara on 10/11/2015.
 */
public class ClinicaDAO extends DAO {

    private static final ClinicaDAO instance = new ClinicaDAO();

    private ClinicaDAO() {
        super();
    }

    public static ClinicaDAO getInstance(){
        return instance;
    }

    private static PetSaudeSQLiteHelper database = getDataBaseHelper();

    /**metodo que é usado para carregar e para atualiza as listas de Clinicas do APP
     *@param
     */
    public void retrieveClinicas(){
        open();
        Cursor cursorClinicas = getDatabase().rawQuery("SELECT * FROM clinica",new String[]{});
        ArrayList<Integer> clinicaIds = new ArrayList<Integer>();
        if (cursorClinicas != null){
            cursorClinicas.moveToFirst();
            for (int x = 0 ; x<cursorClinicas.getCount(); x++){
                clinicaIds.add(cursorClinicas.getInt(0));
                cursorClinicas.moveToNext();
            }
        }
        close();

        ArrayList<Clinica> clinica = new ArrayList<Clinica>();

        if (!clinicaIds.isEmpty()){
            for (int i : clinicaIds){
                clinica.add(this.getClinica(i));
            }
        }

        Session.setListaClinicas(clinica);
        close();
    }

    public Clinica getClinica(int id){
        open();
        String idSQL = Integer.toString(id);
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM clinica WHERE _id=?", new String[]{(idSQL)});
        mCursor.moveToFirst();
        Clinica clinica= new Clinica();
        clinica.setId(mCursor.getInt(0));
        clinica.setNome(mCursor.getString(1));
        clinica.setEndereco(mCursor.getString(2));
        clinica.setLatitude(mCursor.getDouble(3));
        clinica.setLongitude(mCursor.getDouble(4));
        close();
        return clinica;
    }


}
