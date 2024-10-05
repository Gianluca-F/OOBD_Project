package com.unina.oobd2324_37.entity.DTO;

public class Operatore {
    private String nome;
    private String cognome;
    public String codFiscale;
    private String username;
    private String password;

    public Operatore(String nome, String cognome, String codFiscale, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.username = username;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Operatore{" + "nome=" + nome + ", cognome=" + cognome + ", codFiscale=" + codFiscale + ", username=" + username + ", password=" + password + '}';
    }

}
