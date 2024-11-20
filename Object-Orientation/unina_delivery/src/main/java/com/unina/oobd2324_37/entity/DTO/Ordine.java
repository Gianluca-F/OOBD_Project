package com.unina.oobd2324_37.entity.DTO;

import java.time.LocalDate;
import java.util.Currency;

public class Ordine {
    private String idOrdine;
    private LocalDate data;
    private String via;
    private String civico;
    private String CAP;
    private String citta;
    private String cellulare;
    private double prezzoTotale;
    private boolean consegnato;

    public Ordine(String idOrdine, LocalDate data, String via, String civico, String CAP, String citta, String cellulare, double prezzoTotale, boolean consegnato) {
        this.idOrdine = idOrdine;
        this.data = data;
        this.via = via;
        this.civico = civico;
        this.CAP = CAP;
        this.citta = citta;
        this.cellulare = cellulare;
        this.prezzoTotale = prezzoTotale;
        this.consegnato = consegnato;
    }

    public String getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(String idOrdine) {
        this.idOrdine = idOrdine;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        this.CAP = CAP;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public boolean isConsegnato() {
        return consegnato;
    }

    public void setConsegnato(boolean consegnato) {
        this.consegnato = consegnato;
    }
}
