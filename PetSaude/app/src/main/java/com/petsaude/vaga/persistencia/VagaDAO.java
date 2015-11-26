package com.petsaude.vaga.persistencia;

import android.database.Cursor;

import com.petsaude.animal.dominio.Animal;
import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.consulta.persistencia.ConsultaDAO;
import com.petsaude.database.DAO;
import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.medico.dominio.Medico;
import com.petsaude.medico.persistencia.MedicoDAO;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.dominio.Usuario;
import com.petsaude.vaga.dominio.Vaga;


import java.util.ArrayList;

/**
 * Created by Matheus Uehara on 17/11/2015.
 */
public class VagaDAO extends DAO {

    private static final VagaDAO instance = new VagaDAO ();

    private VagaDAO () {
        super();
    }

    public static VagaDAO getInstance(){
        return instance;
    }

    private static PetSaudeSQLiteHelper database = getDataBaseHelper();


    private static ConsultaDAO consultaDAO = ConsultaDAO.getInstance();



    public ArrayList<Vaga> retrieveVagas(Clinica clinica){
        open();
        String idSQL = Integer.toString(clinica.getId());
        Cursor cursorVagas = getDatabase().rawQuery("SELECT * FROM vaga where id_clinica=?", new String[]{(idSQL)});
        ArrayList<Integer> vagaIds = new ArrayList<Integer>();
        if (cursorVagas != null){
            cursorVagas.moveToFirst();
            for (int x = 0 ; x<cursorVagas.getCount(); x++){
                vagaIds.add(cursorVagas.getInt(0));
                cursorVagas.moveToNext();
            }
        }
        close();

        ArrayList<Vaga> vaga = new ArrayList<Vaga>();

        if (!vagaIds.isEmpty()){
            for (int i : vagaIds){
                if (this.getVaga(i).getStatus().equals("DISPONIVEL")) {
                    vaga.add(this.getVaga(i));
                }
            }
        }
        close();
        return vaga;
    }


    public ArrayList<Vaga> retrieveVagas(Usuario usuario){
        open();
        String idSQL = Integer.toString(usuario.getId());
        Cursor cursorVagas = getDatabase().rawQuery("SELECT * FROM vaga where id_usuario=?", new String[]{(idSQL)});
        ArrayList<Integer> vagaIds = new ArrayList<Integer>();
        if (cursorVagas != null){
            cursorVagas.moveToFirst();
            for (int x = 0 ; x<cursorVagas.getCount(); x++){
                vagaIds.add(cursorVagas.getInt(0));
                cursorVagas.moveToNext();
            }
        }
        close();

        ArrayList<Vaga> vaga = new ArrayList<Vaga>();

        if (!vagaIds.isEmpty()){
            for (int i : vagaIds){
                vaga.add(this.getVaga(i));
            }
        }
        close();
        Session.getUsuarioLogado().setListaConsultas(vaga);
        return vaga;
    }


    public ArrayList<Vaga> retrieveVagas(Medico usuario){
        open();
        String idSQL = Integer.toString(usuario.getId());
        Cursor cursorVagas = getDatabase().rawQuery("SELECT * FROM vaga where id_medico=?", new String[]{(idSQL)});
        ArrayList<Integer> vagaIds = new ArrayList<Integer>();
        if (cursorVagas != null){
            cursorVagas.moveToFirst();
            for (int x = 0 ; x<cursorVagas.getCount(); x++){
                vagaIds.add(cursorVagas.getInt(0));
                cursorVagas.moveToNext();
            }
        }
        close();

        ArrayList<Vaga> vaga = new ArrayList<Vaga>();

        if (!vagaIds.isEmpty()){
            for (int i : vagaIds){
                vaga.add(this.getVaga(i));
            }
        }
        close();
        Session.getMedicoLogado().setListaConsultas(vaga);
        return vaga;
    }

    public Vaga getVaga(int id){
        open();
        String idSQL = Integer.toString(id);
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM vaga WHERE _id=?", new String[]{(idSQL)});
        mCursor.moveToFirst();
        Vaga vaga= new Vaga();
        vaga.setId(mCursor.getInt(0));
        vaga.setIdClinica(mCursor.getInt(1));
        vaga.setIdAnimal(mCursor.getInt(3));
        vaga.setMedico(MedicoDAO.getInstance().getMedico(mCursor.getInt(4)));
        vaga.setData(mCursor.getString(5));
        vaga.setStatus(mCursor.getString(6));
        close();
        return vaga;
    }

    public void updateVaga(Usuario usuarioLogado, Animal animal, Vaga vaga){
        String id_usuario = Integer.toString(usuarioLogado.getId());
        String id_animal = Integer.toString(animal.getId());
        String id_vaga = Integer.toString(vaga.getId());
        open();
        getDatabase().execSQL("UPDATE vaga SET id_usuario=?,id_animal=?,status='CONFIRMADO' WHERE _id=?;", new String[]{id_usuario, id_animal, id_vaga});
        close();
    }
}
