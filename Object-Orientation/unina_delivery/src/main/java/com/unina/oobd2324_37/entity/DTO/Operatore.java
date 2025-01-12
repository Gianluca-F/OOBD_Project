package com.unina.oobd2324_37.entity.DTO;

public class Operatore {
    private String nome;
    private String cognome;
    private String codFiscale;
    private String username;
    private String password;

    // Regex per validazioni
    private static final String NOME_COGNOME_PATTERN = "^[A-Za-z]+$";
    private static final String COD_FISCALE_PATTERN = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$";
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]+$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]+$";

    public Operatore(String nome, String cognome, String codFiscale, String username, String password) {
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
        if(!username.matches(USERNAME_PATTERN) || username.length() < 4) {
            throw new IllegalArgumentException("Username non valido");
        }
        if(!password.matches(PASSWORD_PATTERN) || password.length() < 8) {
            throw new IllegalArgumentException("Password non valida");
        }

        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(!username.matches(USERNAME_PATTERN) || username.length() < 4) {
            throw new IllegalArgumentException("Username non valido");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(!password.matches(PASSWORD_PATTERN) || password.length() < 8) {
            throw new IllegalArgumentException("Password non valida");
        }
        this.password = password;
    }

}
