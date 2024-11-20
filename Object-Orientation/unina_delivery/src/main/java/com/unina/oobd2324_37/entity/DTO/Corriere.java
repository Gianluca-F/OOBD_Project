package com.unina.oobd2324_37.entity.DTO;

public class Corriere {
    private String nome;
    private String cognome;
    private String codFiscale;
    private String cellulare;
    private boolean disponibilita;

    public Corriere(String nome, String cognome, String codFiscale, String username, boolean password) {
        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.cellulare = username;
        this.disponibilita = password;
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

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public boolean getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(boolean disponibilita) {
        this.disponibilita = disponibilita;
    }
}
