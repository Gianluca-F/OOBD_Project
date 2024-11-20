package com.unina.oobd2324_37.entity.DTO;

public class Articolo {
    private String idArticolo;
    private String nome;
    private String descrizione;
    private double peso;
    private double prezzo;
    private int quantita;

    public Articolo(String idArticolo, String nome, String descrizione, double peso, double prezzo, int quantita) {
        this.idArticolo = idArticolo;
        this.nome = nome;
        this.descrizione = descrizione;
        this.peso = peso;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public String getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(String idArticolo) {
        this.idArticolo = idArticolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

}
