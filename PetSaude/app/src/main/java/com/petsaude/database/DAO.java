package com.petsaude.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.petsaude.animal.dominio.Animal;
import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.medico.dominio.Medico;
import com.petsaude.usuario.dominio.Usuario;
import com.petsaude.vaga.dominio.Vaga;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Rodolfo Bispo on 26/11/2015.
 */
public abstract class  DAO {

    private static PetSaudeSQLiteHelper dataBaseHelper;
    private static SQLiteDatabase database;
    private Context context = null;

    public void setContextUp(Context context) {
        setAtributes(context);
    }

    protected void setAtributes(Context ctx) {
        setContext(ctx);
        setBDHelper(new PetSaudeSQLiteHelper(ctx));

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static PetSaudeSQLiteHelper getDataBaseHelper() {
        return dataBaseHelper;
    }

    public void setBDHelper(PetSaudeSQLiteHelper PetSaudeSQLiteHelper) {
        this.dataBaseHelper = PetSaudeSQLiteHelper;
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public void open() {
        setDatabase(getDataBaseHelper().getWritableDatabase());
    }

    public void close()
    {
        getDataBaseHelper().close();
    }

    //ESTRUTURA DO WEB SERVICE
    private static final String USUARIO_SERVICE_NAMESPACE="http://service.usuario.petsaude.com.br";

    public static String getAnimalServiceNamespace() {
        return ANIMAL_SERVICE_NAMESPACE;
    }

    public static String getClinicaNamespace() {
        return CLINICA_NAMESPACE;
    }

    public static String getMedicoNamespace() {
        return MEDICO_NAMESPACE;
    }

    public static String getVagaNamespace() {
        return VAGA_NAMESPACE;
    }

    private static final String VAGA_NAMESPACE ="http://service.vaga.petsaude.com.br";

    public static String getGetVaga() {
        return GET_VAGA;
    }

    public static String getLoginMedico() {
        return LOGIN_MEDICO;
    }

    private static final String LOGIN_MEDICO ="login";
    private static final String GET_VAGA ="getVaga";

    public static String getGetMedico() {
        return GET_MEDICO;
    }


    private static  final String RETRIEVE_VAGAS_USUARIO="retrieveVagasUsuario";

    public static String getRetrieveVagasMedico() {
        return RETRIEVE_VAGAS_MEDICO;
    }

    public static String getRetrieveVagasUsuario() {
        return RETRIEVE_VAGAS_USUARIO;
    }

    public static String getRetrieveVagasClinica() {
        return RETRIEVE_VAGAS_CLINICA;
    }

    private static  final String RETRIEVE_VAGAS_MEDICO="retrieveVagasMedico";
    private static  final String RETRIEVE_VAGAS_CLINICA="retrieveVagasClinica";
    private static final String GET_MEDICO ="getMedico";
    private static final String MEDICO_NAMESPACE ="http://service.medico.petsaude.com.br";
    private static final String CLINICA_NAMESPACE="http://service.clinica.petsaude.com.br";

    public static String getRetrieveClinicas() {
        return RETRIEVE_CLINICAS;
    }

    private static final String RETRIEVE_CLINICAS="retrieveClinicas";
    private static final String ANIMAL_SERVICE_NAMESPACE="http://service.animal.petsaude.com.br";
    private static final String LOGIN="login";
    private static final String EXISTE_EMAIL="existeEmail";
    private static final String ALTERAR_SENHA="alterarSenha";
    private static final String EXCLUIR_USUARIO="excluirUsuario";
    private static final String ALTERAR_EMAIL="alterarEmail";
    private static final String INSERIR_USUARIO="inserirUsuario";
    private static final String EXISTE_USUARIO="existeUsuario";
    //animal
    private static final String INSERIR_ANIMAL="inserirAnimal";
    private static final String ATUALIZAR_ANIMAL="atualizarAnimal";
    private static final String EXCLUIR_ANIMAL="excluirAnimal";
    private static final String BUSCAR_TODOS_ANIMAIS="buscarTodosAnimais";

    public static String getAtualizaProntuario() {
        return ATUALIZA_PRONTUARIO;
    }

    public static String getUpdateVaga() {
        return UPDATE_VAGA;
    }

    private static final String UPDATE_VAGA="updateVaga";
    private static final String ATUALIZA_PRONTUARIO="atualizaProntuario";

    public static String getBUSCAR_ANIMAl_POR_ID() {
        return BUSCAR_ANIMAl_POR_ID;
    }

    private static final String BUSCAR_ANIMAl_POR_ID ="buscarAnimalPorID";

    public static String getExisteAnimal() {
        return EXISTE_ANIMAL;
    }

    private static final String EXISTE_ANIMAL="existeAnimal";

    public static String getGetClinica() {
        return GET_CLINICA;
    }

    private static final String GET_CLINICA="getClinica";

    public static String getAtualizarAnimal() {
        return ATUALIZAR_ANIMAL;
    }

    public static String getExcluirAnimal() {
        return EXCLUIR_ANIMAL;
    }

    public static String getBuscarTodosAnimais() {
        return BUSCAR_TODOS_ANIMAIS;
    }

    public static String getInserirAnimal() {
        return INSERIR_ANIMAL;
    }



    public static String getExisteEmail() {
        return EXISTE_EMAIL;
    }

    public static String getAlterarSenha() {
        return ALTERAR_SENHA;
    }

    public static String getExcluirUsuario() {
        return EXCLUIR_USUARIO;
    }

    public static String getAlterarEmail() {
        return ALTERAR_EMAIL;
    }

    public static String getInserirUsuario() {
        return INSERIR_USUARIO;
    }

    public static String getExisteUsuario() {
        return EXISTE_USUARIO;
    }


    public static String getUsuarioServiceNamespace() {
        return USUARIO_SERVICE_NAMESPACE;
    }
    public static String getLOGIN() {
        return LOGIN;
    }
    public void envelopeSoapObject(SoapObject soapObject,String URL,String metodo) throws Exception {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL,80000);
        try {
            http.call(getUsuarioServiceNamespace()+ metodo, envelope);

        }catch (Exception e){
           // throw new Exception("ops!,não foi possivel acessar o servidor");
            e.printStackTrace();
        }
    }
    public SoapObject usuarioSoapObject(Usuario usuario){
        SoapObject user = new SoapObject(getUsuarioServiceNamespace(),"usuario");
        user.addProperty("email", usuario.getEmail());
        user.addProperty("id",usuario.getId());
        user.addProperty("login", usuario.getLogin());
        user.addProperty("nome", usuario.getNome());
        user.addProperty("senha",usuario.getSenha());

        return user;
    }
    public SoapObject medicoSoapObject(Medico usuario){
        SoapObject user = new SoapObject(getUsuarioServiceNamespace(),"usuario");
        user.addProperty("email", usuario.getEmail());
        user.addProperty("id",usuario.getId());
        user.addProperty("login", usuario.getLogin());
        user.addProperty("nome", usuario.getNome());
        user.addProperty("senha",usuario.getSenha());
        user.addProperty("CRMV",usuario.getCRMV());

        return user;
    }
    public SoapObject animalSoapObject(Animal animal){
        SoapObject animalObject = new SoapObject(getAnimalServiceNamespace(), "animal");
        animalObject.addProperty("idUsuario",animal.getUsuario());
        animalObject.addProperty("id",animal.getId());
        animalObject.addProperty("nome",animal.getNome());
        animalObject.addProperty("raca", animal.getRaca());
        animalObject.addProperty("dataNasc", animal.getDataNasc());
        animalObject.addProperty("peso", animal.getPeso());
        animalObject.addProperty("prontuario",animal.getProntuario());
        return animalObject;
    }
    public SoapObject clinicaSoapObject(Clinica clinica){
        SoapObject animalObject = new SoapObject(getClinicaNamespace(), "clinica");
        animalObject.addProperty("id", clinica.getId());
        animalObject.addProperty("nome", clinica.getNome());
        animalObject.addProperty("endereco", clinica.getEndereco());
        animalObject.addProperty("latitude", clinica.getLatitude());
        animalObject.addProperty("longitude", clinica.getLongitude());
        return  animalObject;


    }
    public SoapObject vagaSoapObject(Vaga vaga) {
        SoapObject vagaObject = new SoapObject(getVagaNamespace(), "vaga");
        vagaObject.addProperty("id", vaga.getId());
        vagaObject.addProperty("idClinica", vaga.getIdClinica());
        vagaObject.addProperty("idUsuario", vaga.getIdCliente());
        vagaObject.addProperty("medico",medicoSoapObject(vaga));
        vagaObject.addProperty("status", vaga.getStatus());
        return vagaObject;
    }
    public SoapObject medicoSoapObject(Vaga vaga){
        SoapObject medicoObject=new SoapObject(getVagaNamespace(),"medico");
        medicoObject.addProperty("email",vaga.getMedico().getEmail());
        medicoObject.addProperty("id",vaga.getMedico().getId());
        medicoObject.addProperty("login",vaga.getMedico().getLogin());
        medicoObject.addProperty("nome",vaga.getMedico().getNome());
        medicoObject.addProperty("senha",vaga.getMedico().getSenha());
        medicoObject.addProperty("CRMV",vaga.getMedico().getCRMV());
        medicoObject.addProperty("id_clinica",vaga.getMedico().getId_clinica());
        return medicoObject;
    }


}