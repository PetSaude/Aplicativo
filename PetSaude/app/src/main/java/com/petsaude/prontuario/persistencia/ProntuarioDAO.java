package com.petsaude.prontuario.persistencia;

import android.content.ContentValues;
import android.database.Cursor;

import com.petsaude.database.DAO;
import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.prontuario.dominio.Prontuario;

import java.util.ArrayList;

/**
 * Created by igormlgomes on 19/11/15.
 */
public class ProntuarioDAO extends DAO {


    private static final ProntuarioDAO instance = new ProntuarioDAO();

    private ProntuarioDAO() {
        super();
    }

    public static ProntuarioDAO getInstance(){
        return instance;
    }

    private static PetSaudeSQLiteHelper database = getDataBaseHelper();

    /**metodo que adiciona o Prontuario de um animal no banco de dados
     * @param prontuario
     */
    /*
    public void adicionarProntuario(Prontuario prontuario) {
        open();
        ContentValues initialValues = new ContentValues();
        initialValues.put(database.getIdAnimalProntuario(), prontuario.getIdAnimal());
        initialValues.put(database.getHistoricoMedico(), String.valueOf(prontuario.getHistorico()));
        getDatabase().insert(database.getTableNameProntuario(), null, initialValues);
        close();
    }


    /**metodo que confere se o prontuario de determinado animal já existe no banco
     * @param prontuario
     * @return  condition
     */


    /*
    public boolean existeProntuario(Prontuario prontuario){
        boolean condition = false;
        open();
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameProntuario() + " WHERE historico_medico=? and _id_animal_prontuario=?", new String[]{String.valueOf(prontuario.getHistorico()), ""+ prontuario.getIdAnimal()});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                condition = true;
            }
        }
        close();
        return condition;
    }

    public void exibirConsultas(Prontuario prontuario) {
        open();
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM prontuario where _id_animal_prontuario=?", new String[]{"" + prontuario.getIdAnimal()});
        ArrayList<Integer> animalIds = new ArrayList<Integer>();
        if (cursor != null) {
            cursor.moveToFirst();
            for (int x = 0; x < cursor.getCount(); x++) {
                animalIds.add(cursor.getInt(0));
                cursor.moveToNext();
            }
        }
        close();

    }
    */
}
