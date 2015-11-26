package com.petsaude.usuario.dominio;

import android.content.Context;

import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.medico.dominio.Medico;
import com.petsaude.usuario.dominio.Usuario;

import java.util.ArrayList;


/**
 * Created by Raissa on 17/11/2014.
 */

public class Session {

    public static Usuario usuarioLogado;

    public static Medico medicoLogado;

    public static Clinica ClinicaSelecionada;

    private static ArrayList<Clinica> listaClinicas;


    public static Medico getMedicoLogado() {
        return medicoLogado;
    }

    public static void setMedicoLogado(Medico medicoLogado) {
        Session.medicoLogado = medicoLogado;
    }

    public static ArrayList<Clinica> getListaClinicas() {
        return listaClinicas;
    }

    public static Clinica getClinicaSelecionada() {
        return ClinicaSelecionada;
    }

    public static void setClinicaSelecionada(Clinica clinicaSelecionada) {
        ClinicaSelecionada = clinicaSelecionada;
    }

    public static void setListaClinicas(ArrayList<Clinica> listaClinica) {
        listaClinicas = listaClinica;
    }

    private static Context contexto = null;

    public static void setUsuarioLogado(Usuario usuario){
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado(){
        return usuarioLogado;
    }

    public static void setContexto(Context context){contexto = context;}

    public static Context getContexto(){return contexto;}
}
