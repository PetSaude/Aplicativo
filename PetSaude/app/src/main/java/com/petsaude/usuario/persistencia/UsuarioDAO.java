package com.petsaude.usuario.persistencia;


import com.petsaude.database.DAO;
import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.usuario.dominio.Usuario;

import android.database.Cursor;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class UsuarioDAO extends DAO {

    private static final UsuarioDAO instance = new UsuarioDAO();
    private UsuarioDAO(){
        super();
    }
    public static UsuarioDAO getInstance(){
        return instance;
    }
    private static final String URL="http://7211a1d455b74b45b921350403a1fecd.cloudapp.net/PetSaudeWebservice/services/UsuarioService?wsdl";
    private static final String NAME_ESPACE_GERAL ="http://service.usuario.petsaude.com.br";

    private static PetSaudeSQLiteHelper database = getDataBaseHelper();


    public boolean existeEmail(Usuario usuario){
        SoapObject existeEmail=new SoapObject(getUsuarioServiceNamespace(),getExisteEmail());
        existeEmail.addSoapObject(usuarioSoapObject(usuario));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(existeEmail);
        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL,80000);
        try {
            http.call(getUsuarioServiceNamespace() + getExisteEmail(), envelope);
            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
            return Boolean.parseBoolean(resposta.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void adicionarUsuario(Usuario usuario) throws Exception {
        SoapObject adicionarUsuario =new SoapObject(getUsuarioServiceNamespace(),getInserirUsuario());
        adicionarUsuario.addSoapObject(usuarioSoapObject(usuario));
        //envelopeSoapObject(adicionarUsuario,URL,getInserirUsuario());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(adicionarUsuario);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call(getUsuarioServiceNamespace()+ getInserirUsuario(), envelope);

        }catch (Exception e){
            throw new Exception("ops!,não foi possivel acessar o servidor");
        }
    }
    public void alterarEmail(String email,Usuario usuario) throws Exception {
        SoapObject alterarEmail = new SoapObject(getUsuarioServiceNamespace(),getAlterarEmail());
        alterarEmail.addProperty(getEmailProperty(email));
        alterarEmail.addSoapObject(usuarioSoapObject(usuario));
        envelopeSoapObject(alterarEmail, URL, getAlterarEmail());
    }
    public boolean deleteUsuario(Usuario usuario){
            return true;
    }
    public void alterarSenha(String senha,Usuario usuario) throws Exception {
        SoapObject alterarSenha = new SoapObject(getUsuarioServiceNamespace(),getAlterarSenha());
        alterarSenha.addProperty(getSenhaProperty(senha));
        alterarSenha.addSoapObject(usuarioSoapObject(usuario));
        envelopeSoapObject(alterarSenha,URL,getAlterarSenha());

    }

    public boolean existeUsuario(Usuario usuario) {
        SoapObject usuarioObject = new SoapObject(getUsuarioServiceNamespace(), getExisteUsuario());
        usuarioObject.addSoapObject(usuarioSoapObject(usuario));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(usuarioObject);
        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL,80000);
        try {
            http.call(getUsuarioServiceNamespace() + getExisteUsuario(), envelope);
            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
            return Boolean.parseBoolean(resposta.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Usuario login(String login, String senha) throws Exception {
        Usuario usr = null; // Cria um ponteiro para receber as respostas...
        SoapObject buscarUsuario = new SoapObject(getUsuarioServiceNamespace(),getLOGIN());
        PropertyInfo loginPro=new PropertyInfo();
        PropertyInfo senhaPro=new PropertyInfo();
        loginPro.setValue(login);
        loginPro.setType(String.class);
        loginPro.setName("login");

        senhaPro.setValue(senha);
        senhaPro.setType(String.class);
        senhaPro.setName("senha");
        buscarUsuario.addProperty(loginPro);
        buscarUsuario.addProperty(senhaPro);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscarUsuario);

        envelope.implicitTypes = true;


        HttpTransportSE http = new HttpTransportSE(URL,80000);
        try {
            http.call(getUsuarioServiceNamespace() + getLOGIN(), envelope);
            SoapObject resposta = (SoapObject) envelope.getResponse();
           if (resposta!=null ){
                usr = new Usuario(); // instancia o objeto que vai receber as informações
                // Coloca as informações da resposta dentro do objeto...
                usr.setNome(resposta.getProperty("nome").toString());
                usr.setEmail(resposta.getProperty("email").toString());
                usr.setSenha(resposta.getProperty("senha").toString());
                usr.setLogin(resposta.getProperty("login").toString());
                usr.setID(Integer.parseInt(resposta.getPropertyAsString("id")));
            }




        } catch (Exception e) {
            e.printStackTrace();


        }
        return usr; // Se não der nenhum erro, retorna o objeto.
    }
    private PropertyInfo getSenhaProperty(String senha){
        PropertyInfo senhaPro=new PropertyInfo();
        senhaPro.setValue(senha);
        senhaPro.setType(String.class);
        senhaPro.setName("senha");
        return senhaPro;
    }
    private PropertyInfo getEmailProperty(String email){
        PropertyInfo emailPro=new PropertyInfo();
        emailPro.setValue(email);
        emailPro.setType(String.class);
        emailPro.setName("email");
        return emailPro;
    }


}