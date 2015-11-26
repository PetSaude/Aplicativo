package com.petsaude.usuario.persistencia;


import com.petsaude.database.DAO;
import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.usuario.dominio.Usuario;

import android.database.Cursor;


public class UsuarioDAO extends DAO {

    private static final UsuarioDAO instance = new UsuarioDAO();
    private UsuarioDAO(){
        super();
    }
    public static UsuarioDAO getInstance(){
        return instance;
    }
    private static final String URL="http://192.168.0.14:8080/PetSaudeWebservice/services/UsuarioService?wsdl";
    private static final String NAMESPACE="http://service.usuario.petsaude.com.br";
    private static final String LOGIN="login";

    private static PetSaudeSQLiteHelper database = getDataBaseHelper();


    public boolean existeEmail(Usuario usuario){
        return false;
    }
    public void adicionarUsuario(Usuario usuario){

    }
    public void alterarEmail(String email){

    }
    public boolean deleteUsuario(Usuario usuario){
            return true;
    }
    public void alterarSenha(String senha){

    }

    public Usuario login(String login, String senha){
        Usuario condition = null;
        open();
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameUsuario() + " WHERE login=? AND senha=?", new String[]{login,senha});
        if (((mCursor != null) && (mCursor.getCount() > 0))) {
                mCursor.moveToFirst();
                Usuario novoUsuario = new Usuario();
                novoUsuario.setID(mCursor.getInt(mCursor.getColumnIndex(database.getIdUsuario())));
                novoUsuario.setLogin(mCursor.getString(mCursor.getColumnIndex(database.getLogin())));
                novoUsuario.setNome(mCursor.getString(mCursor.getColumnIndex(database.getNomeUsuario())));
                novoUsuario.setEmail(mCursor.getString(mCursor.getColumnIndex(database.getEmail())));
                novoUsuario.setSenha(mCursor.getString(mCursor.getColumnIndex(database.getSenha())));
                condition = novoUsuario;
                int i = mCursor.getInt(mCursor.getColumnIndex("CRMV"));
                String spaks = i+"";
                if (!spaks.equals("") & !spaks.equals("0")){
                    condition = null;
                }
                close();
        }
        return condition;
    }

    public boolean existeUsuario(Usuario usuario){
                boolean condition = false;
                open();
                Cursor mCursor = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameUsuario() + " WHERE login=?", new String[]{(usuario.getLogin())});
                if (mCursor != null) {
                        if (mCursor.getCount() > 0) {
                                condition = true;
                            }
                   }
                close();
                return condition;
    }
}