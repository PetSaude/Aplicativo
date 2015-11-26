package com.petsaude.animal.persistencia;

import android.content.ContentValues;
import android.database.Cursor;

import com.petsaude.database.DAO;
import com.petsaude.animal.dominio.Animal;
import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.dominio.Usuario;

import java.util.ArrayList;


/**
 * Created by alessondelmiro on 11/10/15.
 */
public class AnimalDAO extends DAO{

    private static final AnimalDAO instance = new AnimalDAO();

    private AnimalDAO() {
        super();
    }

    public static AnimalDAO getInstance(){
        return instance;
    }

    private static PetSaudeSQLiteHelper database = getDataBaseHelper();

    /**metodo que adiciona o animal no banco de dados
     * @param animal
     */
    public void adicionarAnimal(Animal animal) {
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


    /**metodo que confere se o animal que está sendo cadastrado já existe
     * @param animal
     * @return  condition
     */
    public boolean existeAnimal(Animal animal){
        boolean condition = false;
        open();
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameAnimal() + " WHERE nome=? and id_usuario_animal=?", new String[]{animal.getNome(), ""+animal.getUsuario()});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                condition = true;
            }
        }
        close();
        return condition;
    }

    public void retrieveAnimais(Usuario usuario){
        open();
        Cursor cursorAnimais = getDatabase().rawQuery("SELECT * FROM animal where id_usuario_animal=?",new String[]{""+usuario.getID()});
        ArrayList<Integer> animalIds = new ArrayList<Integer>();
        if (cursorAnimais != null){
            cursorAnimais .moveToFirst();
            for (int x = 0 ; x<cursorAnimais .getCount(); x++){
                animalIds.add(cursorAnimais .getInt(0));
                cursorAnimais .moveToNext();
            }
        }
        close();

        ArrayList<Animal> animal = new ArrayList<Animal>();

        if (!animalIds.isEmpty()){
            for (int i : animalIds){
                animal.add(this.getAnimal(i));
            }
        }
        Session.getUsuarioLogado().setListaAnimais(animal);
        close();
    }

    public Animal getAnimal(int id){
        open();
        String idSQL = Integer.toString(id);
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM animal WHERE _id=?", new String[]{(idSQL)});
        mCursor.moveToFirst();
        Animal animal= new Animal();
        animal.setId(mCursor.getInt(0));
        animal.setNome(mCursor.getString(1));
        animal.setRaca(mCursor.getString(2));
        animal.setDataNasc(mCursor.getString(3));
        animal.setPeso(mCursor.getInt(4));
        animal.setProntuario(mCursor.getString(6));
        close();
        return animal;
    }

    public boolean atualizaProntuario(String prontuario, int id) {
        open();
        String idSQL = Integer.toString(id);
        getDatabase().execSQL("UPDATE animal SET prontuario=? WHERE _id =?", new String[]{prontuario,idSQL});
        close();
        return true;
    }
}
