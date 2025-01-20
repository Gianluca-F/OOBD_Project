package com.unina.oobd2324_37.entity.DTO;

public class CompOrdine {
    private Ordine ordine;
    private Articolo articolo;
    private int quantita;
    private double pesoPar;
    private double prezzoPar;

    public CompOrdine(Ordine ordine, Articolo articolo, int quantita, double pesoPar, double prezzoPar) {
        if(quantita < 0){
            throw new IllegalArgumentException("Quantità non valida");
        }
        if(pesoPar <= 0){
            throw new IllegalArgumentException("Peso non valido");
        }
        if(prezzoPar < 0){
            throw new IllegalArgumentException("Prezzo non valido");
        }

        this.ordine = ordine;
        this.articolo = articolo;
        this.quantita = quantita;
        this.pesoPar = pesoPar;
        this.prezzoPar = prezzoPar;
    }

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public void setArticolo(Articolo articolo) {
        this.articolo = articolo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        if(quantita < 0){
            throw new IllegalArgumentException("Quantità non valida");
        }
        this.quantita = quantita;
    }

    public double getPesoPar() {
        return pesoPar;
    }

    public void setPesoPar(double pesoPar) {
        if(pesoPar <= 0){
            throw new IllegalArgumentException("Peso non valido");
        }
        this.pesoPar = pesoPar;
    }

    public double getPrezzoPar() {
        return prezzoPar;
    }

    public void setPrezzoPar(double prezzoPar) {
        if(prezzoPar < 0){
            throw new IllegalArgumentException("Prezzo non valido");
        }
        this.prezzoPar = prezzoPar;
    }


}
