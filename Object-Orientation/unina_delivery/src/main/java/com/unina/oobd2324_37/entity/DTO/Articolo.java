package com.unina.oobd2324_37.entity.DTO;

public class Articolo {
    private String idArticolo;
    private String nome;
    private String descrizione;
    private int quantita;
    private double peso;
    private double prezzo;

    public Articolo(String idArticolo, String nome, String descrizione, int quantita, double peso, double prezzo) {
        if(quantita < 0){
            throw new IllegalArgumentException("Quantità non valida");
        }
        if(peso <= 0){
            throw new IllegalArgumentException("Peso non valido");
        }
        if(prezzo < 0){
            throw new IllegalArgumentException("Prezzo non valido");
        }

        this.idArticolo = idArticolo;
        this.nome = nome;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.peso = peso;
        this.prezzo = prezzo;
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

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        if(quantita < 0){
            throw new IllegalArgumentException("Quantità non valida");
        }
        this.quantita = quantita;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if(peso <= 0){
            throw new IllegalArgumentException("Peso non valido");
        }
        this.peso = peso;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        if(prezzo < 0){
            throw new IllegalArgumentException("Prezzo non valido");
        }
        this.prezzo = prezzo;
    }

}
