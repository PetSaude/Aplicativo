package com.petsaude.animal.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.Telephony;
import android.util.Log;
import com.petsaude.database.DAO;
import com.petsaude.animal.dominio.Animal;
import com.petsaude.database.PetSaudeSQLiteHelper;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.dominio.Usuario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


/**
 * Created by Rodolfo Bispo on 11/10/15.
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
    private static final String URL = "http://7211a1d455b74b45b921350403a1fecd.cloudapp.net/PetSaudeWebservice/services/AnimalService?wsdl";
    private static final String NAME_SPACE_GERAL ="http://service.animal.petsaude.com.br";

    /**metodo que adiciona o animal no banco de dados
     * @param animal
     */
    public void adicionarAnimal(Animal animal,Usuario usuario) throws Exception {
        SoapObject adicionarAnimal =new SoapObject(getAnimalServiceNamespace(), getInserirAnimal());
        adicionarAnimal.addSoapObject(animalSoapObject(animal));
        adicionarAnimal.addSoapObject(usuarioSoapObject(usuario));
       // envelopeSoapObject(adicionarAnimal, URL, getInserirAnimal());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(adicionarAnimal);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        try {
            http.call(getAnimalServiceNamespace()+ getInserirAnimal(), envelope);

        }catch (Exception e){
            throw new Exception("ops!,não foi possivel acessar o servidor");
        }
    }
    public Animal getAnimal(int id) throws Exception {
        Animal animal=null;
        SoapObject AnimalPorID = new SoapObject(getAnimalServiceNamespace(),getBUSCAR_ANIMAl_POR_ID());
        AnimalPorID.addProperty(getIdlProperty(id));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(AnimalPorID);
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        try {
            http.call(getAnimalServiceNamespace() + getBUSCAR_ANIMAl_POR_ID(), envelope);
            SoapObject resposta = (SoapObject) envelope.getResponse();
            if(resposta!=null){
                animal= new Animal ();
                animal.setId(Integer.parseInt(resposta.getProperty("id").toString()));
                animal.setNome(resposta.getProperty("nome").toString());
                animal.setRaca(resposta.getProperty("raca").toString());
                animal.setPeso(Integer.parseInt(resposta.getProperty("peso").toString()));
                animal.setDataNasc(resposta.getProperty("dataNasc").toString());
                animal.setUsuario(Integer.parseInt(resposta.getProperty("idUsuario").toString()));
                animal.setProntuario(""+resposta.getPropertyAsString("prontuario"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return animal;
    }


    /**metodo que confere se o animal que está sendo cadastrado já existe
     * @param animal
     * @return  condition
     */

        public boolean existeAnimal(Animal animal,Usuario usuario) throws Exception {
            SoapObject existeAnimal=new SoapObject(getAnimalServiceNamespace(),getExisteAnimal());
            existeAnimal.addSoapObject(animalSoapObject(animal));
            existeAnimal.addSoapObject(usuarioSoapObject(usuario));
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(existeAnimal);
            envelope.implicitTypes = true;

            HttpTransportSE http = new HttpTransportSE(URL,80000);
            try {
                http.call(getAnimalServiceNamespace() + getExisteAnimal(), envelope);
                SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
                return Boolean.parseBoolean(resposta.toString());
            } catch (Exception e) {
                throw new Exception("ops! algo deu errado:(");
            }
        }
    public void buscarTodosAnimais(Usuario usuario) throws Exception {
        ArrayList<Animal> animais = new ArrayList<Animal>();
        SoapObject retrieveAnimal=new SoapObject(getAnimalServiceNamespace(),getBuscarTodosAnimais());
        retrieveAnimal.addSoapObject(usuarioSoapObject(usuario));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(retrieveAnimal);
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        envelope.implicitTypes = true;
        try {
            http.call(getAnimalServiceNamespace() + getBuscarTodosAnimais(), envelope);
            SoapObject resposta = (SoapObject) envelope.bodyIn;
                for (int i = 0; i < resposta.getPropertyCount(); ++i) {
                    SoapObject aux = (SoapObject) resposta.getProperty(i);
                    Animal animal = new Animal();
                    animal.setId(Integer.parseInt(aux.getProperty("id").toString()));
                    animal.setNome(aux.getProperty("nome").toString());
                    animal.setDataNasc(aux.getProperty("dataNasc").toString());
                    animal.setPeso(Integer.parseInt(aux.getProperty("peso").toString()));
                    animal.setRaca(aux.getProperty("raca").toString());
                    animal.setUsuario(Integer.parseInt(aux.getProperty("idUsuario").toString()));
                    animal.setProntuario(""+aux.getPropertyAsString("prontuario"));
                    animais.add(animal);
                }

            Session.getUsuarioLogado().setListaAnimais(animais);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void atualizaProntuario(String prontuario, int id) throws Exception {
        SoapObject atualizaProntuario = new SoapObject(getAnimalServiceNamespace(), getAtualizaProntuario());
        PropertyInfo prontuarioInfo = new PropertyInfo();
        prontuarioInfo.setValue(prontuario);
        prontuarioInfo.setType(String.class);
        prontuarioInfo.setName("prontuario");
        atualizaProntuario.addProperty(prontuarioInfo);
        atualizaProntuario.addProperty(getIdlProperty(id));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(atualizaProntuario);
        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL,80000);
        try {
            http.call(getAnimalServiceNamespace() + getAtualizaProntuario(), envelope);
        } catch (IOException e) {
            throw new Exception("ocorreu um erro ao atualizar");
        }
    }

    private PropertyInfo getIdlProperty(int id){
        PropertyInfo idPro=new PropertyInfo();
        idPro.setValue(id);
        idPro.setType(Integer.class);
        idPro.setName("id");
        return idPro;
    }
}
