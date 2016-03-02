package br.edu.ifspsaocarlos.agenda.model;
import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Contato extends RealmObject implements Serializable{
    private static final long serialVersionUID = 1L;

    private long id;
    @Required
    private String nome;
    private RealmList<Telefone> fones;
    private String email;

    public Contato() { }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public RealmList<Telefone> getFones() { return fones; }
    public void setFones(RealmList<Telefone> fones) { this.fones = fones; }
}