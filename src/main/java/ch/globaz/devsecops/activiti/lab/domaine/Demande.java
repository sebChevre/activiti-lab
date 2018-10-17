package ch.globaz.devsecops.activiti.lab.domaine;

import lombok.Getter;

@Getter
public class Demande {

    private String nom;

    public Demande(String nom){
        this.nom = nom;
    }
}
