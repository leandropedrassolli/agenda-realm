package br.edu.ifspsaocarlos.agenda.model;

import io.realm.RealmObject;

public class Telefone extends RealmObject {

    private int codArea;
    private String numero;

    public int getCodArea() { return codArea; }
    public void setCodArea(int codArea) { this.codArea = codArea; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}
