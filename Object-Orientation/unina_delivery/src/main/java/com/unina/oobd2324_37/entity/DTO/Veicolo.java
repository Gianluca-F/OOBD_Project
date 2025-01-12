package com.unina.oobd2324_37.entity.DTO;

public class Veicolo {
    private String targa;
    private double pesoSupportato;
    private boolean disponibilita;

    private static final String TARGA_PATTERN = "^[A-Z]{2}\\d{3}[A-Z]{2}$";

    public Veicolo(String targa, double pesoSupportato) {
        if(!targa.matches(TARGA_PATTERN)){
            throw new IllegalArgumentException("Targa non valida");
        }
        if(pesoSupportato <= 0){
            throw new IllegalArgumentException("Peso supportato non valido");
        }
        this.targa = targa;
        this.pesoSupportato = pesoSupportato;
        this.disponibilita = true;
    }

    public Veicolo(String targa, double pesoSupportato, boolean disponibilita) {
        this(targa, pesoSupportato);
        this.disponibilita = disponibilita;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        if(!targa.matches(TARGA_PATTERN)){
            throw new IllegalArgumentException("Targa non valida");
        }
        this.targa = targa;
    }
    
    public double getPesoSupportato() {
        return pesoSupportato;
    }

    public void setPesoSupportato(double pesoSupportato) {
        if(pesoSupportato <= 0){
            throw new IllegalArgumentException("Peso supportato non valido");
        }
        this.pesoSupportato = pesoSupportato;
    }

    public boolean isDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(boolean disponibilita) {
        this.disponibilita = disponibilita;
    }
}
