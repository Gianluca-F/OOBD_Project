package com.unina.oobd2324_37.entity.DTO;

public class Veicolo {
    private String targa;
    private double pesoSupportato;
    private boolean disponibilita;

    public Veicolo(String targa, double pesoSupportato, boolean disponibilita) {
        this.targa = targa;
        this.pesoSupportato = pesoSupportato;
        this.disponibilita = disponibilita;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }
    
    public double getPesoSupportato() {
        return pesoSupportato;
    }

    public void setPesoSupportato(double pesoSupportato) {
        this.pesoSupportato = pesoSupportato;
    }

    public boolean isDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(boolean disponibilita) {
        this.disponibilita = disponibilita;
    }
}
