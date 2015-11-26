package com.petsaude.animal.negocio;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.petsaude.animal.dominio.Animal;
import com.petsaude.animal.persistencia.AnimalDAO;
import com.petsaude.usuario.dominio.Session;

/**
 * Created by alessondelmiro on 11/10/15.
 */
public class AnimalService {

    private AnimalDAO animalDAO = null;

    public AnimalService(Context context){
        this.animalDAO = AnimalDAO.getInstance();
        this.animalDAO.setContextUp(context);
    }

    public void adicionar(Animal animal) throws Exception {
        StringBuilder message = new StringBuilder();

        if(animalDAO.existeAnimal(animal)){
            message.append("Você já possui um animal cadastrado com esse nome.");}
        if(message.length() > 0){throw new Exception(
                message.toString());}
        else {
            animalDAO.adicionarAnimal(animal);
            animalDAO.retrieveAnimais(Session.getUsuarioLogado());
        }
    }

    public Animal getAnimal(int id){
        Animal animal = animalDAO.getAnimal(id);
        if ( animal != null){
            return animal;
        }else{
            return null;
        }
    };


    public boolean validarNome(String nome) {
        boolean validadeNome = false;
        if (nome != null && nome.length() > 0) {
            String expression = "^([\\w\\-]+\\.)+[A-Z]$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(nome);
            if (matcher.matches()) {
                validadeNome = true;
            }
        }
        return validadeNome;
    }

}
