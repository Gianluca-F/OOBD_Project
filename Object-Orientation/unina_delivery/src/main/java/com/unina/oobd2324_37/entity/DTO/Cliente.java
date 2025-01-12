package com.unina.oobd2324_37.entity.DTO;

public class Cliente {
    private String nome;
    private String cognome;
    private String via;
    private String civico;
    private String CAP;
    private String cellulare;
    private String email;    //nullable

    // Regex per validazioni
    private static final String NOME_COGNOME_REGEX = "^[A-Za-z]+$";
    private static final String VIA_REGEX = "^[A-Za-z][A-Za-z0-9\\s.,-]*$";
    private static final String CIVICO_REGEX = "^[0-9]{1,3}$";
    private static final String CAP_REGEX = "^[0-9]{5}$";
    private static final String CELLULARE_REGEX = "^[0-9]{10}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]{2,}$";

    public Cliente(String nome, String cognome, String via, String civico, String CAP, String cellulare) {
        // Validazione dei campi tramite regex
        if(!nome.matches(NOME_COGNOME_REGEX)){
            throw new IllegalArgumentException("Nome non valido");
        }
        if(!cognome.matches(NOME_COGNOME_REGEX)){
            throw new IllegalArgumentException("Cognome non valido");
        }
        if(!via.matches(VIA_REGEX)) {
            throw new IllegalArgumentException("Via non valida");
        }
        if(!civico.matches(CIVICO_REGEX)) {
            throw new IllegalArgumentException("Civico non valido");
        }
        if(!CAP.matches(CAP_REGEX)) {
            throw new IllegalArgumentException("CAP non valido");
        }
        if(!cellulare.matches(CELLULARE_REGEX)) {
            throw new IllegalArgumentException("Numero cellulare non valido");
        }

        this.nome = nome;
        this.cognome = cognome;
        this.via = via;
        this.civico = civico;
        this.CAP = CAP;
        this.cellulare = cellulare;
    }

    public Cliente(String nome, String cognome, String via, String civico, String CAP, String cellulare, String email) {
        this(nome, cognome, via, civico, CAP, cellulare);
        if(!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Email non valida");
        }
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(!nome.matches(NOME_COGNOME_REGEX)){
            throw new IllegalArgumentException("Nome non valido");
        }
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        if(!cognome.matches(NOME_COGNOME_REGEX)){
            throw new IllegalArgumentException("Cognome non valido");
        }
        this.cognome = cognome;
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

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        if(!via.matches(VIA_REGEX)) {
            throw new IllegalArgumentException("Via non valida");
        }
        this.via = via;
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

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        if(!cellulare.matches(CELLULARE_REGEX)) {
            throw new IllegalArgumentException("Numero cellulare non valido");
        }
        this.cellulare = cellulare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Email non valida");
        }
        this.email = email;
    }
}

