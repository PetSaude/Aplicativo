package com.petsaude.medico.persistencia;

import com.petsaude.database.DAO;

import android.database.Cursor;

import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.medico.dominio.Medico;
import com.petsaude.usuario.dominio.Usuario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Rodolfo Bispo on 17/11/2015.
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
    private static final String URL="http://7211a1d455b74b45b921350403a1fecd.cloudapp.net/PetSaudeWebservice/services/MedicoService?wsdl";

    public Medico getMedico(int id) throws Exception {
        Medico medico = new Medico();
        SoapObject getMedico = new SoapObject(getMedicoNamespace(), getGetMedico());
        PropertyInfo idPro = new PropertyInfo();
        idPro.setValue(id);
        idPro.setType(Integer.class);
        idPro.setName("id");
        getMedico.addProperty(idPro);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(getMedico);
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        envelope.implicitTypes = true;
        try {
            http.call(getClinicaNamespace() + getGetMedico(), envelope);
            SoapObject resposta = (SoapObject) envelope.getResponse();
            medico.setNome(resposta.getProperty("nome").toString());
            medico.setEmail(resposta.getProperty("email").toString());
            medico.setSenha(resposta.getProperty("senha").toString());
            medico.setLogin(resposta.getProperty("login").toString());
            medico.setID(Integer.parseInt(resposta.getPropertyAsString("id")));
            medico.setCRMV(Integer.parseInt(resposta.getPrimitivePropertyAsString("CRMV")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medico;
    }

    public Medico login(String login, String senha) throws Exception {
        Medico medico=null;
        SoapObject buscarUsuario = new SoapObject(getMedicoNamespace(),getLoginMedico());
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
            http.call(getMedicoNamespace() + getLoginMedico(), envelope);
            SoapObject resposta = (SoapObject) envelope.getResponse();
            if (resposta!=null ){
                medico = new Medico();
                medico.setNome(resposta.getProperty("nome").toString());
                medico.setEmail(resposta.getProperty("email").toString());
                medico.setSenha(resposta.getProperty("senha").toString());
                medico.setLogin(resposta.getProperty("login").toString());
                medico.setID(Integer.parseInt(resposta.getPropertyAsString("id")));
                medico.setCRMV(Integer.parseInt(resposta.getPrimitivePropertyAsString("CRMV")));
            }

        } catch (Exception e) {
            throw new Exception("ops!,não foi possivel estabelecer a conexão");


        }
        return medico;
    }



}