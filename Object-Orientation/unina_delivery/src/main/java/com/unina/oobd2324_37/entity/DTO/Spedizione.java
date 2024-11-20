package com.unina.oobd2324_37.entity.DTO;

import java.time.LocalDateTime;

public class Spedizione {
    private String idSpedizione;
    private LocalDate data;
    private LocalDateTime orario;
    private String avviata;
    private String completata;

    public Spedizione(String idSpedizione, LocalDate data, LocalDateTime orario, String avviata, String completata) {
        this.idSpedizione = idSpedizione;
        this.data = data;
        this.orario = orario;
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

    public String getAvviata() {
        return avviata;
    }

    public void setAvviata(String avviata) {
        this.avviata = avviata;
    }

    public String getCompletata() {
        return completata;
    }

    public void setCompletata(String completata) {
        this.completata = completata;
    }

}
