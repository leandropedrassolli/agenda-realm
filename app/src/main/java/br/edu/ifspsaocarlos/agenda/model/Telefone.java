package br.edu.ifspsaocarlos.agenda.model;

import java.io.Serializable;

import io.realm.RealmObject;

public class Telefone extends RealmObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codArea;
    private String numero;

    public int getCodArea() { return codArea; }
    public void setCodArea(int codArea) { this.codArea = codArea; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}
