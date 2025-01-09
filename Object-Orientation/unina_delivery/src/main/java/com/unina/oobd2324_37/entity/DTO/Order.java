package com.unina.oobd2324_37.entity.DTO;

import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleStringProperty idOrdine;
    private final SimpleStringProperty dataOrdine;
    private final SimpleStringProperty prezzoTotale;
    private final SimpleStringProperty utente;
    private final SimpleStringProperty email;
    private final SimpleStringProperty indirizzo;
    private final SimpleStringProperty citta;
    private final SimpleStringProperty cellulare;
    private final SimpleStringProperty consegnato;
    private final SimpleStringProperty idSpedizione;
    private final SimpleStringProperty operatore;

    public Order(String idOrdine, String dataOrdine, String prezzoTotale, String utente, String email,
                 String indirizzo, String citta, String cellulare, String consegnato,
                 String idSpedizione, String operatore) {
        this.idOrdine = new SimpleStringProperty(idOrdine);
        this.dataOrdine = new SimpleStringProperty(dataOrdine);
        this.prezzoTotale = new SimpleStringProperty(prezzoTotale);
        this.utente = new SimpleStringProperty(utente);
        this.email = new SimpleStringProperty(email);
        this.indirizzo = new SimpleStringProperty(indirizzo);
        this.citta = new SimpleStringProperty(citta);
        this.cellulare = new SimpleStringProperty(cellulare);
        this.consegnato = new SimpleStringProperty(consegnato);
        this.idSpedizione = new SimpleStringProperty(idSpedizione);
        this.operatore = new SimpleStringProperty(operatore);
    }

    // Getters e Setters
    public String getIdOrdine() { return idOrdine.get(); }
    public void setIdOrdine(String id) { idOrdine.set(id); }
    public String getDataOrdine() { return dataOrdine.get(); }
    public void setDataOrdine(String data) { dataOrdine.set(data); }
    public String getPrezzoTotale() { return prezzoTotale.get(); }
    public void setPrezzoTotale(String prezzo) { prezzoTotale.set(prezzo); }
    public String getUtente() { return utente.get(); }
    public void setUtente(String user) { utente.set(user); }
    public String getEmail() { return email.get(); }
    public void setEmail(String mail) { email.set(mail); }
    public String getIndirizzo() { return indirizzo.get(); }
    public void setIndirizzo(String address) { indirizzo.set(address); }
    public String getCitta() { return citta.get(); }
    public void setCitta(String city) { citta.set(city); }
    public String getCellulare() { return cellulare.get(); }
    public void setCellulare(String phone) { cellulare.set(phone); }
    public String getConsegnato() { return consegnato.get(); }
    public void setConsegnato(String delivered) { consegnato.set(delivered); }
    public String getIdSpedizione() { return idSpedizione.get(); }
    public void setIdSpedizione(String idSped) { idSpedizione.set(idSped); }
    public String getOperatore() { return operatore.get(); }
    public void setOperatore(String operator) { operatore.set(operator); }
}
