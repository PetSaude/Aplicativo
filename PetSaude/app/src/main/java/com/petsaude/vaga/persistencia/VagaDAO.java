package com.petsaude.vaga.persistencia;

import android.database.Cursor;
import android.util.Log;

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


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

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

    private static final String URL = "http://7211a1d455b74b45b921350403a1fecd.cloudapp.net/PetSaudeWebservice/services/VagaService?wsdl";

    public ArrayList<Vaga> retrieveVagas(Clinica clinica) throws Exception {
        ArrayList<Vaga> vagas=  new ArrayList<>();
        SoapObject retrieveVagas = new SoapObject(getVagaNamespace(),getRetrieveVagasClinica());
        retrieveVagas.addSoapObject(clinicaSoapObject(clinica));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(retrieveVagas);
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        envelope.implicitTypes = true;
        try {
            http.call(getVagaNamespace() + getRetrieveVagasClinica(), envelope);
            SoapObject resposta = (SoapObject) envelope.bodyIn;
            for (int i = 0; i < resposta.getPropertyCount(); ++i) {
                SoapObject aux = (SoapObject) resposta.getProperty(i);
            if(resposta!=null){
                    Medico medico = new Medico();
                    SoapObject medicoObject= (SoapObject) aux.getProperty("medico");
                    medico.setNome(medicoObject.getProperty("nome").toString());
                    medico.setId(Integer.parseInt(medicoObject.getPrimitivePropertyAsString("id")));


                    Vaga vaga = new Vaga();
                    vaga.setId(Integer.parseInt(aux.getProperty("id").toString()));
                    vaga.setIdClinica(Integer.parseInt(aux.getPrimitivePropertyAsString("idClinica")));
                    vaga.setIdCliente(Integer.parseInt(aux.getPrimitivePropertyAsString("idUsuario")));
                    vaga.setData(aux.getPrimitivePropertyAsString("data"));
                    vaga.setMedico(medico);
                    vaga.setStatus(aux.getProperty("status").toString());
                    vagas.add(vaga);
                }
            }

            close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return vagas;
    }


    public ArrayList<Vaga> retrieveVagas(Usuario usuario) throws Exception {
        ArrayList<Vaga> vagas = new ArrayList<>();
        SoapObject retrieveVagas = new SoapObject(getVagaNamespace(),getRetrieveVagasUsuario());
        retrieveVagas.addSoapObject(usuarioSoapObject(usuario));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(retrieveVagas);
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        envelope.implicitTypes = true;
        try {
            http.call(getVagaNamespace() + getRetrieveVagasUsuario(), envelope);
            SoapObject resposta = (SoapObject) envelope.bodyIn;
            for (int i = 0; i < resposta.getPropertyCount(); ++i) {
                SoapObject aux = (SoapObject) resposta.getProperty(i);
           if(resposta!=null){
               SoapObject medicoObject= (SoapObject) aux.getProperty("medico");
               Medico medico = new Medico();
               medico.setId(Integer.parseInt(medicoObject.getProperty("id").toString()));
               medico.setNome(medicoObject.getProperty("nome").toString());
               Vaga vaga = new Vaga();
               vaga.setId(Integer.parseInt(aux.getProperty("id").toString()));
               vaga.setIdClinica(Integer.parseInt(aux.getPrimitivePropertyAsString("idClinica")));
               vaga.setIdCliente(Integer.parseInt(aux.getPrimitivePropertyAsString("idUsuario")));
               vaga.setData(aux.getPrimitivePropertyAsString("data"));
               vaga.setIdAnimal(Integer.parseInt(aux.getPrimitivePropertyAsString("idAnimal")));
               vaga.setMedico(medico);
               vaga.setStatus(aux.getProperty("status").toString());
               vagas.add(vaga);
           }

            }
            close();
        }catch(Exception e){
            //throw new Exception(e);
            e.printStackTrace();
        }
        Session.getUsuarioLogado().setListaConsultas(vagas);
        return vagas;
    }


    public ArrayList<Vaga> retrieveVagas(Medico usuario) throws Exception {
        ArrayList<Vaga> vagas = new ArrayList<>();
        SoapObject retrieveVagas = new SoapObject(getVagaNamespace(),getRetrieveVagasMedico());
        retrieveVagas.addSoapObject(medicoSoapObject(usuario));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(retrieveVagas);
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        envelope.implicitTypes = true;
        try {
            http.call(getVagaNamespace() + getRetrieveVagasMedico(), envelope);
            SoapObject resposta = (SoapObject) envelope.bodyIn;
            for (int i = 0; i < resposta.getPropertyCount(); ++i) {
                SoapObject aux = (SoapObject) resposta.getProperty(i);
            if(resposta!=null){
                    SoapObject medicoObject= (SoapObject) aux.getProperty("medico");
                    Medico medico = new Medico();
                    if(medicoObject!=null){
                        medico.setId(Integer.parseInt(medicoObject.getProperty("id").toString()));
                        medico.setNome(medicoObject.getProperty("nome").toString());
                    }

                    Vaga vaga = new Vaga();
                    vaga.setId(Integer.parseInt(aux.getProperty("id").toString()));
                vaga.setIdClinica(Integer.parseInt(aux.getPrimitivePropertyAsString("idClinica")));
                vaga.setIdCliente(Integer.parseInt(aux.getPrimitivePropertyAsString("idUsuario")));
                vaga.setIdAnimal(Integer.parseInt(aux.getPrimitivePropertyAsString("idAnimal")));
                vaga.setMedico(medico);
                    vaga.setData(aux.getPrimitivePropertyAsString("data"));
                    vaga.setStatus(aux.getProperty("status").toString());
                    vagas.add(vaga);
                }
            }


            close();
        }catch(Exception e){
            e.printStackTrace();
        }
        Session.getMedicoLogado().setListaConsultas(vagas);
        return vagas;
    }

    public void updateVaga(Usuario usuario, Animal animal, Vaga vaga) throws Exception {
        SoapObject updateVaga = new SoapObject(getVagaNamespace(), getUpdateVaga());
        updateVaga.addSoapObject(usuarioSoapObject(usuario));
        updateVaga.addSoapObject(animalSoapObject(animal));
        updateVaga.addSoapObject(vagaSoapObject(vaga));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(updateVaga);
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        envelope.implicitTypes = true;
        try {
            http.call(getVagaNamespace() + getUpdateVaga(), envelope);

    } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }
