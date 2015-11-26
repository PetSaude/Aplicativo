package com.petsaude.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Uehara on 08/11/2014.
 */
public class PetSaudeSQLiteHelper extends SQLiteOpenHelper  {

    private static final String DATABASE_NAME = "petsaude";

    private static int DATABASE_VERSION = 1;

    private static final String TAG = "DBAdapter";

    public PetSaudeSQLiteHelper(Context context) {
        super(context, getDataBaseName(), null, getDATABASE_VERSION());
    }

    public static String getDataBaseName() {
        return DATABASE_NAME;
    }

    public static int getDATABASE_VERSION() {
        return DATABASE_VERSION;
    }

    public static void setDATABASE_VERSION(int DATABASE_VERSION) {
        PetSaudeSQLiteHelper.DATABASE_VERSION = DATABASE_VERSION;
    }

    public static String getTag() {
        return TAG;
    }

    /*
    criacao da tabela usuario e suas colunas
    */

    private static final String LOGIN_USUARIO = "login";
    private static final String NOME_USUARIO = "nome";
    private static final String SENHA_USUARIO = "senha";
    private static final String EMAIL_USUARIO = "email";
    private static final String ID_USUARIO = "_id";
    private static final String TABLE_NAME_USUARIO = "usuario";
    private static final String CRMV_USUARIO = "CRMV";

    private static final String TABLE_DATABASE_USUARIO_CREATE =
            "create table usuario (_id integer primary key autoincrement, " +
                    "login text not null, "+
                    "nome text not null,"+
                    "senha text not null, "+
                    "email text not null," +
                    "CRMV integer);";

    public static String getTableDatabaseUsuarioCreate() {
        return TABLE_DATABASE_USUARIO_CREATE;
    }
    public static String getEmail(){
        return EMAIL_USUARIO;
    }
    public  static String getNomeUsuario(){
        return NOME_USUARIO;
    }
    public static String getLogin() {
        return LOGIN_USUARIO;
    }
    public static String getSenha() {
        return SENHA_USUARIO;
    }
    public static String getIdUsuario(){return ID_USUARIO;}

	public static String getTableNameUsuario() {
        return TABLE_NAME_USUARIO;
    }


    /*
    * Criação da tabela animal e suas colunas
    * */

    private static final String NOME_ANIMAL = "nome";
    private static final String RACA_ANIMAL = "raca";
    private static final String DATA_NASC_ANIMAL = "data_nasc";
    private static final String PESO_ANIMAL = "peso";
    private static final String ID_USUARIO_ANIMAL = "id_usuario_animal";
    private static final String ID_ANIMAL = "_id";
    private static final String TABLE_NAME_ANIMAL = "animal";

    private static final String TABLE_DATABASE_ANIMAL_CREATE =
            "create table animal (_id integer primary key autoincrement, " +
                    "nome text not null, "+
                    "raca text not null, "+
                    "data_nasc text not null," +
                    "peso integer," +
                    "id_usuario_animal int not null," +
                    "prontuario text,"+
                    "FOREIGN KEY(id_usuario_animal) REFERENCES usuario(_id));";

    public static String getTableDatabaseAnimalCreate() {
        return TABLE_DATABASE_ANIMAL_CREATE;
    }
    public static String getNomeAnimal(){
        return NOME_ANIMAL;
    }
    public static String getRacaAnimal() {
        return RACA_ANIMAL;
    }
    public static String getDataNascAnimal() {
        return DATA_NASC_ANIMAL;
    }
    public static String getPesoAnimal(){
        return PESO_ANIMAL;
    }
    public static String getIdUsuarioAnimal() {return ID_USUARIO_ANIMAL;}
    public static String getIdAnimal() {
        return ID_ANIMAL;
    }
    public static String getTableNameAnimal() {
        return TABLE_NAME_ANIMAL;
    }

    /*
    *
    *Criação da tabela Clinica e seus atributos
    *
    */
    private static final String ID_CLINICA = "_id";
    private static final String NOME_CLINICA = "nome";
    private static final String ENDERECO_CLINICA = "endereco";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String TABLE_NAME_CLINICA = "clinica";

    private static final String TABLE_DATABASE_CLINICA_CREATE =
            "create table clinica (_id integer primary key autoincrement, " +
                    "nome text not null, "+
                    "endereco text not null,"+
                    "latitude double not null, "+
                    "longitude double not null);";

    public static String getTableDatabaseClinicaCreate() {
        return TABLE_DATABASE_CLINICA_CREATE;
    }
    public static String getIdClinica() {return ID_CLINICA;}
    public static String getNomeClinica() {return NOME_CLINICA;}
    public static String getEnderecoClinica() {return ENDERECO_CLINICA;}
    public static String getLatitude() {return LATITUDE;}
    public static String getLongitude() {return LONGITUDE;}
    public static String getTableNameClinica(){return TABLE_NAME_CLINICA ;}

    /*
    *Criacao da tabela Vaga e suas colunas
    **/

    private static final String TABLE_DATABASE_VAGA_CREATE =
            "create table vaga (_id integer primary key autoincrement, " +
                    "id_clinica integer not null, "+
                    "id_usuario integer, "+
                    "id_animal integer, "+
                    "id_medico integer not null," +
                    "data String not null," +
                    "status String not null);";

    /*
    *Criacao da tabela Prontuario e suas colunas
    * */

    private static final String ID_PRONTUARIO = "_id";
    private static final String HISTORICO_MEDICO = "historico_medico";
    private static final String ID_ANIMAL_PRONT = "_id_animal_prontuario";
    private static final String TABLE_NAME_PRONTUARIO = "prontuario";

    private static final String TABLE_DATABASE_PRONTUARIO_CREATE =
            "create table prontuario (_id integer primary key autoincrement, "+
                    "historico_medico text not null, "+
                    "_id_animal_prontuario integer not null, "+
                    "FOREIGN KEY(id_animal_prontuario) REFERENCES animal(_id));";


    public static String getTableDatabasePrintuarioCreate() {return TABLE_DATABASE_PRONTUARIO_CREATE;}
    public static String getIdProntuario() {return ID_PRONTUARIO;}
    public static String getHistoricoMedico() {return HISTORICO_MEDICO;}
    public static String getIdAnimalPront() {return ID_ANIMAL_PRONT;}
    public static String getTableDatabaseProntuarioCreate() {return TABLE_NAME_PRONTUARIO;}

    /*
    * Criacao da tabela consulta e sua colunas
    * */

    private static final String ID_CONSULTA = "_id";
    private static final String DATA = "data";
    private static final String HORA = "hora";
    private static final String STATUS = "status";
    private static final String ID_ANIMAL_CONSULTA = "_id_animal";
    private static final String ID_MEDICO_CONSULTA = "_id_medico";
    private static final String TABLE_NAME_CONSULTA = "consulta";

    private static final String TABLE_DATABASE_CONSULTA_CREATE =
            "create table consulta (_id integer primary key autoincrement, "+
                    "data date not null, "+
                    "hora text not null, "+
                    "status text not null, "+
                    "_id_animal integer not null, "+
                    "_id_medico integer not null, "+
                    "FOREIGN KEY(_id_animal) REFERENCES animal(_id) "+
                    "FOREIGN KEY(_id_medico) REFENCES usuario(_id));";

    public static String getTableDatabaseConsutaCreate() {return TABLE_DATABASE_CONSULTA_CREATE;}
    public static String getIdConsulta() {return ID_CONSULTA;}
    public static String getData() {return DATA;}
    public static String getHora() {return HORA;}
    public static String getStatus() {return STATUS;}
    public static String getIdAnimalConsulta() {return ID_ANIMAL_CONSULTA;}
    public static String getIdMedicoConsulta() {return ID_MEDICO_CONSULTA;}
    public static String getTableNameConsulta() {return TABLE_NAME_CONSULTA;}

    /*
    * Criacao da tabela Vacina e suas colunas
    * */

    private static final String ID_VACINA = "_id";
    private static final String NOME_VACINA = "nome";
    private static final String TABLE_NAME_VACINA = "vacina";

    private static final String TABLE_DATABASE_VACINA_CREATE =
            "create table vacina (_id integer primary key autoincrement, "+
                    "nome text not null);";

    public static String getTableDatabaseVacinaCreate() {return TABLE_DATABASE_VACINA_CREATE;}
    public static String getIdVacina() {return ID_VACINA;}
    public static String getNomeVacina() {return NOME_VACINA;}
    public static String getTableNameVacina() {return TABLE_NAME_VACINA;}


    /*
    * Criacao da tabela vacina_ministrada e suas colunas
    * */

    private static final String ID_VACINA_PRONTUARIO = "_id_prontuario";
    private static final String ID_VACINA_APLICADA = "_id_vacina";
    private static final String DATA_APLICADA = "data";
    private static final String INTERVALO = "intervalo";
    private static final String QUANTIDADE = "quantidade";
    private static final String TABLE_NAME_VACINA_MISNISTRADA = "vacina_ministrada";

    private static final String TABLE_DATABASE_VACINA_MINISTRADA_CREATE =
            "create table vacina_ministrada (_id_prontuario integer primary key null, "+
                    "_id_vacina integer primary key null, "+
                    "data date not null, "+
                    "intervalo text not null, "+
                    "quantidade text not null, "+
                    "FOREIGN KEY(_id_prontuario) REFERENCES prontuario(_id),  "+
                    "FOREIGN KEY(_id_vacina) REFERENCES Vacina(_id));";

    public static String getTableDatabaseVacinaMinistradaCreate() {return TABLE_DATABASE_VACINA_MINISTRADA_CREATE;}
    public static String getIdVacinaProntuario() {return ID_VACINA_PRONTUARIO;}
    public static String getIdVacinaAplicada() {return ID_VACINA_APLICADA;}
    public static String getDataAplicada() {return DATA_APLICADA;}
    public static String getIntervalo() {return INTERVALO;}
    public static String getQuantidade() {return QUANTIDADE;}
    public static String getTableNameVacinaMisnistrada() {return TABLE_NAME_VACINA_MISNISTRADA;}

    /*
    * Criacao da tabela medicamento e suas colunas
    * */

    private static final String ID_MEDICAMENTO = "_id";
    private static final String FINALIDADE = "finalidade";
    private static final String PRINCIPIO_ATIVO = "principio_ativo";
    private static final String TABLE_NAME_MEDICAMENTO = "medicamento";

    private static final String TABLE_DATABASE_MEDICAMENTO_CREATE =
            "create table medicamento (_id integer primary key autoincrement, "+
                    "finalidade text not null, "+
                    "principio_ativo text not null);";

    public static String getTableDatabaseMedicamentoCreate() {return TABLE_DATABASE_MEDICAMENTO_CREATE;}
    public static String getIdMedicamento() {return ID_MEDICAMENTO;}
    public static String getFinalidade() {return FINALIDADE;}
    public static String getPrincipioAtivo() {return PRINCIPIO_ATIVO;}
    public static String getTableNameMedicamento() {return TABLE_NAME_MEDICAMENTO;}


    /*
    * Criacao da tabela medicamento_ministrado e suas colunas
    * */

    private static final String ID_MEDICAMENTO_PRONTUARIO = "_id_prontuario";
    private static final String ID_MEDICAMENTO_MINISTRADO = "_id_medicamento";
    private static final String INICIO = "inicio";
    private static final String FIM = "fim";
    private static final String QUANTIDADE_MEDICAMENTO = "quantidade";
    private static final String INTERVALO_MEDICAMENTO = "intervalo";
    private static final String TABLE_NAME_MEDICAMENTO_MINISTRADO = "medicamento_ministrado";

    private static final String TABLE_DATABASE_MEDICAMENTO_MINISTRADO_CREATE =
            "create table medicamento_ministrado (_id_prontuario integer primary key, "+
                    "_id_medicamento integer primary key, "+
                    "inicio date not null, "+
                    "fim date not null, "+
                    "quantidade text not null, "+
                    "intervalo text not null, "+
                    "FOREIGN KEY(_id_prontuario REFERENCES prontuario(_id), "+
                    "FOREIGN KEY(_id_medicamento) REFERENCES medicamento(_id));";

    public static String getTableDatabaseMedicamentoMinistradoCreate() {return TABLE_DATABASE_MEDICAMENTO_MINISTRADO_CREATE;}
    public static String getIdMedicamentoProntuario() {return ID_MEDICAMENTO_PRONTUARIO;}
    public static String getIdMedicamentoMinistrado() {return ID_MEDICAMENTO_MINISTRADO;}
    public static String getInicio() {return INICIO;}
    public static String getFim() {return FIM;}
    public static String getQuantidadeMedicamento() {return QUANTIDADE_MEDICAMENTO;}
    public static String getIntervaloMedicamento() {return INTERVALO_MEDICAMENTO;}
    public static String getTableNameMedicamentoMinistrado() {return TABLE_NAME_MEDICAMENTO_MINISTRADO;}


    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL(getTableDatabaseUsuarioCreate());
        dataBase.execSQL(getTableDatabaseAnimalCreate());
        dataBase.execSQL(getTableDatabaseClinicaCreate());
        dataBase.execSQL(TABLE_DATABASE_VAGA_CREATE);
        dataBase.execSQL("insert into usuario (login,nome,senha,email) values ('math','math','math','math@math.com')");

        dataBase.execSQL("insert into usuario (login,nome,senha,email,CRMV) values ('DrUehara','Dr Uehara','math','mattth@math.com',1234)");
        dataBase.execSQL("insert into usuario (login,nome,senha,email,CRMV) values ('DrHouse','Dr House','math','maasdth@math.com',123456)");
        dataBase.execSQL("insert into usuario (login,nome,senha,email,CRMV) values ('DrIgor','Dr nigga','math','maasdth@haath.com',32111)");

        dataBase.execSQL("insert into vaga(id_clinica,id_medico,data,status) values (2,2,'26/11/2015 12:00 as 13:00','DISPONIVEL')");
        dataBase.execSQL("insert into vaga(id_clinica,id_medico,data,status) values (2,3,'25/11/2015 14:00 as 14:40','DISPONIVEL')");
        dataBase.execSQL("insert into vaga(id_clinica,id_medico,data,status) values (2,2,'03/12/2015 14:00 as 13:00','DISPONIVEL')");
        dataBase.execSQL("insert into vaga(id_clinica,id_medico,data,status) values (2,3,'02/12/2015 14:00 as 14:40','DISPONIVEL')");




        dataBase.execSQL("insert into clinica (nome,endereco,latitude,longitude) values " +
                "('Pet Dream Clinica veterinária','rua das ruas',-8.191706,-34.924004)," +
                "('Amigo Bixo','rua das ruas',-8.195558,-34.920701)," +
                "('Mundo Animal','rua das ruas',-8.2216625,-34.9334426)," +
                "('Mary Rações','rua das ruas',-8.2216625,-34.9334426)," +
                "('JJ Rações','rua das ruas',-8.2205582,-34.9337859)," +
                "('Bom pra cachorro','rua das ruas',-8.2176274,-34.9282069)," +
                "('Bicho Mania Pet Shop','rua das ruas',-8.2166717,-34.927649)," +
                "('CliniPet','rua das ruas',-8.2170752,-34.9256535)," +
                "('Star Dog','rua das ruas',-8.2049858,-34.9230318)," +
                "('Pet Ninas Clinica Veterinaria','rua das ruas',-8.2007208,-34.9230015)," +
                "('Cantinho do Pet','rua das ruas',-8.1982279,-34.9316196)," +
                "('Clinica Bixos','rua das ruas',-8.1918002,-34.9271892)," +
                "('Pet Shop Bixos','rua das ruas',-8.1881658,-34.9243367)," +
                "('Super Amigos Pet','rua das ruas',-8.1881658,-34.9243367)," +
                "('Mundo do criador','rua das ruas',-8.1861534,-34.9221821)," +
                "('Cão delivery Pet Shop','rua das ruas',-8.1861534,-34.9221821)," +
                "('Planet Dog','rua das ruas',-8.1851621,-34.9211807)," +
                "('Cão delivery Pet Shop','rua das ruas',-8.1793951,-34.9297079)," +
                "('Belos Cães','rua das ruas',-8.1667321,-34.9274042)," +
                "('Dog House Pet Shop','rua das ruas',-8.1667321,-34.9274042)," +
                "('Pet Show','rua das ruas',-8.1667321,-34.9274042)," +
                "('Cao Qri','rua das ruas',-8.1714273,-34.9258095)," +
                "('Fiel Companheiro','rua das ruas',-8.1650978,-34.9211747)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int newVersion, int oldVersion) {
        Log.w(getTag(), "Atualizando o banco de dados da versao " + oldVersion
                + " para "
                + newVersion + ", que destruira todos os dados antigos");
        dataBase.execSQL("DROP TABLE IF EXISTS login");
        onCreate(dataBase);/// query para recriar o banco
        setDATABASE_VERSION(newVersion);
    }


}
