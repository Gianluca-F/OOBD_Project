package com.unina.oobd2324_37.entity.DTO;

public class Cliente {
    private String nome;
    private String cognome;
    private String civico;
    private String via;
    private String CAP;
    private String cellulare;
    private String email;

    public Cliente(String nome, String cognome, String civico, String via, String CAP, String cellulare, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.civico = civico;
        this.via = via;
        this.CAP = CAP;
        this.cellulare = cellulare;
        this.email = email;
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

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        this.CAP = CAP;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

