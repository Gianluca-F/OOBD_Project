package com.unina.oobd2324_37.entity.DTO;

import java.time.LocalDate;

public class Ordine {
    private String idOrdine;
    private LocalDate data;
    private String via;
    private String civico;
    private String CAP;
    private String citta;
    private String cellulare;
    private double prezzoTot;
    private boolean consegnato;
    private Cliente cliente;
    private Operatore operatore;    //nullable
    private Spedizione spedizione;  //nullable

    // Regex per validazioni
    private static final String VIA_REGEX = "^[A-Za-z][A-Za-z0-9\\s.,-]*$";
    private static final String CIVICO_REGEX = "^[0-9]{1,3}$";
    private static final String CAP_REGEX = "^[0-9]{5}$";
    private static final String CITTA_REGEX = "^[A-Za-z][A-Za-z\\s]*$";
    private static final String CELLULARE_REGEX = "^[0-9]{10}$";

    public Ordine(String idOrdine, LocalDate data, String via, String civico, String CAP, String citta, String cellulare, double prezzoTot, Cliente cliente) {
        // Validazione dei campi tramite regex
        if(!via.matches(VIA_REGEX)) {
            throw new IllegalArgumentException("Via non valida");
        }
        if(!civico.matches(CIVICO_REGEX)) {
            throw new IllegalArgumentException("Civico non valido");
        }
        if(!CAP.matches(CAP_REGEX)) {
            throw new IllegalArgumentException("CAP non valido");
        }
        if(!citta.matches(CITTA_REGEX)) {
            throw new IllegalArgumentException("Città non valida");
        }
        if(!cellulare.matches(CELLULARE_REGEX)) {
            throw new IllegalArgumentException("Numero cellulare non valido");
        }
        if(prezzoTot < 0) {
            throw new IllegalArgumentException("Prezzo non valido");
        }

        this.idOrdine = idOrdine;
        this.data = data;
        this.via = via;
        this.civico = civico;
        this.CAP = CAP;
        this.citta = citta;
        this.cellulare = cellulare;
        this.prezzoTot = prezzoTot;
        this.consegnato = false;
        this.cliente = cliente;
    }

    public Ordine(String idOrdine, LocalDate data, String via, String civico, String CAP, String citta, String cellulare, double prezzoTot, Cliente cliente, Operatore operatore, Spedizione spedizione) {
        this(idOrdine, data, via, civico, CAP, citta, cellulare, prezzoTot, cliente);
        this.operatore = operatore;
        this.spedizione = spedizione;
    }

    public Ordine(String idOrdine, LocalDate data, String via, String civico, String CAP, String citta, String cellulare, double prezzoTot, Cliente cliente, Operatore operatore, Spedizione spedizione, boolean consegnato) {
        this(idOrdine, data, via, civico, CAP, citta, cellulare, prezzoTot, cliente, operatore, spedizione);
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
        if(!via.matches(VIA_REGEX)) {
            throw new IllegalArgumentException("Via non valida");
        }
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        if(!civico.matches(CIVICO_REGEX)) {
            throw new IllegalArgumentException("Civico non valido");
        }
        this.civico = civico;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        if(!CAP.matches(CAP_REGEX)) {
            throw new IllegalArgumentException("CAP non valido");
        }
        this.CAP = CAP;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        if(!citta.matches(CITTA_REGEX)) {
            throw new IllegalArgumentException("Città non valida");
        }
        this.citta = citta;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        if(!cellulare.matches(CELLULARE_REGEX)) {
            throw new IllegalArgumentException("Numero cellulare non valido");
        }
        this.cellulare = cellulare;
    }

    public double getPrezzoTot() {
        return prezzoTot;
    }

    public void setPrezzoTot(double prezzoTot) {
        if(prezzoTot < 0) {
            throw new IllegalArgumentException("Prezzo non valido");
        }
        this.prezzoTot = prezzoTot;
    }

    public boolean isConsegnato() {
        return consegnato;
    }

    public void setConsegnato(boolean consegnato) {
        this.consegnato = consegnato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Operatore getOperatore() {
        return operatore;
    }

    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    public Spedizione getSpedizione() {
        return spedizione;
    }

    public void setSpedizione(Spedizione spedizione) {
        this.spedizione = spedizione;
    }
}
