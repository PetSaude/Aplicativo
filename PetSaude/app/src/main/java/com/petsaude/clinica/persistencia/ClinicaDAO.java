package com.petsaude.clinica.persistencia;

import android.database.Cursor;
import android.util.Log;

import com.petsaude.animal.dominio.Animal;
import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.database.DAO;
import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.usuario.dominio.Session;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

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
    private static final String URL="http://7211a1d455b74b45b921350403a1fecd.cloudapp.net/PetSaudeWebservice/services/ClinicaService?wsdl";

    /**metodo que é usado para carregar e para atualiza as listas de Clinicas do APP
     *@param
     */
    public void retrieveClinicas() throws Exception {
        ArrayList<Clinica>clinicas = new ArrayList<Clinica>();
        SoapObject retrieveClinicas = new SoapObject(getClinicaNamespace(),getRetrieveClinicas());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(retrieveClinicas);
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        envelope.implicitTypes = true;
        try {
            http.call(getClinicaNamespace() + getRetrieveClinicas(), envelope);
            SoapObject resposta = (SoapObject) envelope.bodyIn;
            for (int i = 0; i < resposta.getPropertyCount(); ++i) {
                SoapObject aux = (SoapObject) resposta.getProperty(i);
            if(resposta!=null){
                    Clinica clinica = new Clinica();
                    clinica.setId(Integer.parseInt(aux.getProperty("id").toString()));
                    clinica.setNome(aux.getProperty("nome").toString());
                    clinica.setEndereco(aux.getProperty("endereco").toString());
                    clinica.setLatitude(aux.getProperty("latitude").toString());
                    clinica.setLongitude(aux.getProperty("longitude").toString());

                    clinicas.add(clinica);

                }
            }

            Session.setListaClinicas(clinicas);
            close();
        }catch(Exception e){
            throw new Exception(e);
            }
        }

    public Clinica getClinica(int id) throws Exception {
       Clinica clinica = null;
        SoapObject retrieveClinicas = new SoapObject(getClinicaNamespace(),getGetClinica());
        PropertyInfo prop = new PropertyInfo();
        prop.setValue(id);
        prop.setType(Integer.class);
        prop.setName("id");
        retrieveClinicas.addProperty(prop);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(retrieveClinicas);
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        envelope.implicitTypes = true;
        try {
            http.call(getClinicaNamespace() + getGetClinica(), envelope);
            SoapObject resposta = (SoapObject) envelope.getResponse();
            clinica = new Clinica();
            clinica.setId(Integer.parseInt(resposta.getProperty("id").toString()));
            clinica.setNome(resposta.getProperty("nome").toString());
            clinica.setEndereco(resposta.getProperty("endereco").toString());
            clinica.setLatitude(resposta.getProperty("latitude").toString());
            clinica.setLongitude(resposta.getProperty("longitude").toString());

        }catch (Exception e){
             throw new  Exception(e);
        }
        return clinica;
    }


}
