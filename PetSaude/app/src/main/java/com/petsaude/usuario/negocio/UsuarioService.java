package com.petsaude.usuario.negocio;

import android.content.Context;
import android.util.Log;

import com.petsaude.animal.persistencia.AnimalDAO;
import com.petsaude.clinica.dominio.Clinica;
import com.petsaude.clinica.persistencia.ClinicaDAO;
import com.petsaude.medico.dominio.Medico;
import com.petsaude.medico.persistencia.MedicoDAO;
import com.petsaude.usuario.dominio.Session;
import com.petsaude.usuario.dominio.Usuario;
import com.petsaude.usuario.persistencia.UsuarioDAO;
import com.petsaude.vaga.dominio.Vaga;
import com.petsaude.vaga.persistencia.VagaDAO;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Uehara on 08/11/2014.
 */
public class UsuarioService {

    private UsuarioDAO usuarioDAO = null;
    private ClinicaDAO clinicaDAO = ClinicaDAO.getInstance();
    private AnimalDAO animalDAO = AnimalDAO.getInstance();
    private MedicoDAO medicoDAO = MedicoDAO.getInstance();
    private VagaDAO vagaDAO = VagaDAO.getInstance();

    public UsuarioService(Context context){
        this.usuarioDAO = UsuarioDAO.getInstance();
        this.usuarioDAO.setContextUp(context);
    }


    public boolean login(String login , String senha) throws Exception {
        StringBuilder message = new StringBuilder();
        boolean retorno = false;
        Usuario usuario = usuarioDAO.login(login, senha);
        Medico medico = medicoDAO.login(login, senha);
        if ( usuario != null) {
            Session.setUsuarioLogado(usuario);
            clinicaDAO.retrieveClinicas();
            animalDAO.retrieveAnimais(usuario);
            vagaDAO.retrieveVagas(usuario);
            retorno = true;
        }else if(medico != null) {
            Session.setMedicoLogado(medico);
            vagaDAO.retrieveVagas(medico);
            retorno = true;
        }
        else if (login.length() <= 0) {message.append("Insira o Usuário/Email.");
        } else if (senha.length() <= 0) {message.append("Insira a Senha.");
        } else {message.append("Usuário/Email ou Senha incorreto.");}
        if (message.length() > 0) {
           throw new Exception(message.toString());
        }
    return retorno;
    }

    public void atualizaClinica(Usuario usuario){
        vagaDAO.retrieveVagas(usuario);
    }

    public void atualizaClinica(Medico usuario){
        vagaDAO.retrieveVagas(usuario);
    }

    public void adicionar(Usuario usuario,String confirmarSenha) throws Exception {
        StringBuilder message = new StringBuilder();

        if((!validarLogin(usuario.getLogin())) || (usuarioDAO.existeUsuario(usuario))){
            message.append("O login já está cadastrado ou tem menos de 4 caracteres.");}
        else if(!validarEmail(usuario.getEmail()) || (usuarioDAO.existeEmail(usuario))){
            message.append("O email digitado não é válido ou já está cadastrado.");}
        else if (usuario.getNome().length()<=0){
            message.append("Digite um nome");}
        else if(usuario.getSenha().length()<=0){
            message.append("Digite uma senha.");}
        else if(confirmarSenha.length()<=0){
            message.append("Digite uma confirmação de senha.");}
        else if(!usuario.getSenha().equals(confirmarSenha)){
            message.append("As senhas digitadas não conferem.");}
        if(message.length() > 0){throw new Exception(
                message.toString());}
        else{
            usuarioDAO.adicionarUsuario(usuario);
        }
    }

    public boolean editarSenha(String senha ,String confirmacaoSenha , String senhaAntiga) throws Exception{
        StringBuilder message = new StringBuilder();
        boolean retorno = false;
        if (!senhaAntiga.equals(Session.usuarioLogado.getSenha())){message.append("A senha atual está errada.");}
        else if(senha.length() == 0){message.append("Digite uma senha.");}
        else if (confirmacaoSenha.length() == 0){message.append("Digite uma confirmação de senha.");}
        else if (!senha.equals(confirmacaoSenha)) {message.append("A senha e confirmação de senha são diferentes.");}
        if(message.length() > 0){throw new Exception(message.toString());}
        else{
            retorno = true;
            usuarioDAO.alterarSenha(senha);
            Session.usuarioLogado.setSenha(senha);
        }
        return retorno;
    }

    public boolean editarEmail(String senha , String email)throws Exception{
        StringBuilder message = new StringBuilder();
        boolean retorno = false;
        if (!senha.equals(Session.usuarioLogado.getSenha())){message.append("A senha está errada.");}
        else if(!validarEmail(email)){message.append("Insira um Email válido.");}
        if(message.length() > 0){throw new Exception(message.toString());}
        else{
            retorno = true;
            usuarioDAO.alterarEmail(email);
            Session.usuarioLogado.setEmail(email);
        }
        return retorno;
    }

    public boolean deleteUsuario(Usuario usuario)throws Exception{
        StringBuilder message = new StringBuilder();
        boolean onDelete = false;
        if ((usuarioDAO.deleteUsuario(usuario))){
            onDelete = true;
            Session.setUsuarioLogado(null);
        }else{
            message.append("Ops! O usuário não pode ser excluído.");
            throw new Exception(message.toString());
        }
        return onDelete;
    }

    public boolean validarEmail(String email) {
        boolean validadeEmail = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                validadeEmail = true;
            }
        }
        return validadeEmail;
    }

    public boolean validarLogin(String usuario) {
        boolean LoginValido = false;
        if ((usuario.length() < 25) && (usuario.length() >= 4)) {LoginValido = true;}
        return LoginValido;
    }

}
