package com.unina.oobd2324_37.entity.DTO;

public class Corriere {
    private String nome;
    private String cognome;
    private String codFiscale;
    private String cellulare;
    private boolean disponibilita;

    // Regex per validazioni
    private static final String NOME_COGNOME_PATTERN = "^[A-Za-z]+$";
    private static final String COD_FISCALE_PATTERN = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$";
    private static final String CELLULARE_REGEX = "^[0-9]{10}$";

    public Corriere(String nome, String cognome, String codFiscale, String cellulare, boolean disponibilita) {
        // Validazione dei campi tramite regex
        if(!nome.matches(NOME_COGNOME_PATTERN)){
            throw new IllegalArgumentException("Nome non valido");
        }
        if(!cognome.matches(NOME_COGNOME_PATTERN)){
            throw new IllegalArgumentException("Cognome non valido");
        }
        if(!codFiscale.matches(COD_FISCALE_PATTERN)) {
            throw new IllegalArgumentException("Codice fiscale non valido");
        }
        if(!cellulare.matches(CELLULARE_REGEX)) {
            throw new IllegalArgumentException("Numero cellulare non valido");
        }

        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.cellulare = cellulare;
        this.disponibilita = disponibilita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(!nome.matches(NOME_COGNOME_PATTERN)){
            throw new IllegalArgumentException("Nome non valido");
        }
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        if(!cognome.matches(NOME_COGNOME_PATTERN)){
            throw new IllegalArgumentException("Cognome non valido");
        }
        this.cognome = cognome;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        if(!codFiscale.matches(COD_FISCALE_PATTERN)) {
            throw new IllegalArgumentException("Codice fiscale non valido");
        }
        this.codFiscale = codFiscale;
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

    public boolean getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(boolean disponibilita) {
        this.disponibilita = disponibilita;
    }
}
