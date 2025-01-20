package com.unina.oobd2324_37.entity.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Spedizione {
    private String idSpedizione;
    private LocalDate data;
    private LocalDateTime orario;
    private boolean avviata;
    private boolean completata;
    private Operatore operatore;
    private Corriere corriere;
    private Veicolo veicolo;

    public Spedizione(String idSpedizione, LocalDate data, LocalDateTime orario, Operatore operatore, Corriere corriere, Veicolo veicolo) {
        this.idSpedizione = idSpedizione;
        this.data = data;
        this.orario = orario;
        this.avviata = false;
        this.completata = false;
        this.operatore = operatore;
        this.corriere = corriere;
        this.veicolo = veicolo;
    }

    public Spedizione(String idSpedizione, LocalDate data, LocalDateTime orario, boolean avviata, boolean completata, Operatore operatore, Corriere corriere, Veicolo veicolo) {
        this(idSpedizione, data, orario, operatore, corriere, veicolo);
        if(!avviata && completata){
            throw new IllegalArgumentException("Spedizione non valida");
        }
        this.avviata = avviata;
        this.completata = completata;
    }

    public String getIdSpedizione() {
        return idSpedizione;
    }

    public void setIdSpedizione(String idSpedizione) {
        this.idSpedizione = idSpedizione;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getOrario() {
        return orario;
    }

    public void setOrario(LocalDateTime orario) {
        this.orario = orario;
    }

    public boolean getAvviata() {
        return avviata;
    }

    public void setAvviata(boolean avviata) {
        if(!avviata && completata){
            throw new IllegalArgumentException("Spedizione non valida");
        }
        this.avviata = avviata;
    }

    public boolean getCompletata() {
        return completata;
    }

    public void setCompletata(boolean completata) {
        if(!avviata && completata){
            throw new IllegalArgumentException("Spedizione non valida");
        }
        this.completata = completata;
    }

    public Operatore getOperatore() {
        return operatore;
    }

    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    public Corriere getCorriere() {
        return corriere;
    }

    public void setCorriere(Corriere corriere) {
        this.corriere = corriere;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

}
