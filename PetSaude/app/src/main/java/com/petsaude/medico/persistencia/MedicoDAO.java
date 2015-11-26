package com.petsaude.medico.persistencia;

import com.petsaude.database.DAO;

import android.database.Cursor;

import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.medico.dominio.Medico;

/**
 * Created by Matheus Uehara on 17/11/2015.
 */
public class MedicoDAO extends DAO {


    private static final MedicoDAO instance = new MedicoDAO();

    private MedicoDAO() {
        super();
    }

    public static MedicoDAO getInstance() {
        return instance;
    }

    private static PetSaudeSQLiteHelper database = getDataBaseHelper();

    public Medico getMedico(int id){
        open();
        String idSQL = Integer.toString(id);
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM usuario WHERE _id=? and CRMV!='' ", new String[]{(idSQL)});
        mCursor.moveToFirst();
        Medico medico= new Medico();
        medico.setId(mCursor.getInt(0));
        medico.setNome(mCursor.getString(1));
        close();
        return medico;
    }

    public Medico login(String login, String senha){
        Medico condition = null;
        open();
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameUsuario() + " WHERE login=? AND senha=?", new String[]{login,senha});
        if (((mCursor != null) && (mCursor.getCount() > 0))) {
            mCursor.moveToFirst();
            Medico novoUsuario = new Medico();
            novoUsuario.setID(mCursor.getInt(mCursor.getColumnIndex(database.getIdUsuario())));
            novoUsuario.setLogin(mCursor.getString(mCursor.getColumnIndex(database.getLogin())));
            novoUsuario.setNome(mCursor.getString(mCursor.getColumnIndex(database.getNomeUsuario())));
            novoUsuario.setEmail(mCursor.getString(mCursor.getColumnIndex(database.getEmail())));
            novoUsuario.setSenha(mCursor.getString(mCursor.getColumnIndex(database.getSenha())));
            novoUsuario.setCRMV(mCursor.getInt(mCursor.getColumnIndex("CRMV")));

            condition = novoUsuario;
            close();
        }
        return condition;
    }


}