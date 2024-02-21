package entity;

public class Veicolo {

	private String targa;
	private double pesoSupportato;
	private boolean disponibilita;
	
	// Costruttore con dati completi
	public Veicolo(String targa, double pesoSupportato, boolean disponibilita)
	{
		targa = targa.toUpperCase();
		
		if (!targa.matches("[A-Z]{2}\\d{3}[A-Z]{2}"))
		{
            throw new IllegalArgumentException("La targa non è valida.");
        }
        
        if (pesoSupportato <= 0)
        {
            throw new IllegalArgumentException("Il peso supportato deve essere maggiore di zero.");
        }
        
        pesoSupportato = Math.floor(pesoSupportato * 100) / 100;
		
		this.targa = targa;
		this.pesoSupportato = pesoSupportato;
		this.disponibilita = disponibilita;
	}
	
	// Costruttore con disponibilita default
	public Veicolo(String targa, double pesoSupportato)
	{
		targa = targa.toUpperCase();
		
		if (!targa.matches("[A-Z]{2}\\d{3}[A-Z]{2}"))
            throw new IllegalArgumentException("La targa non è valida.");
		
        if (pesoSupportato <= 0) 
            throw new IllegalArgumentException("Il peso supportato deve essere maggiore di zero.");
        
        pesoSupportato = Math.floor(pesoSupportato * 100) / 100;
		
		this.targa = targa;
		this.pesoSupportato = pesoSupportato;
		this.disponibilita = false;
	}
	
	// Getter e Setter
	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		targa = targa.toUpperCase();
		
		if (!targa.matches("[A-Z]{2}\\d{3}[A-Z]{2}")) 
            throw new IllegalArgumentException("La targa non è valida.");
		
		this.targa = targa;
	}

	public double getPesoSupportato() {
		return pesoSupportato;
	}

	public void setPesoSupportato(double pesoSupportato) {
		
		if (pesoSupportato <= 0) 
            throw new IllegalArgumentException("Il peso supportato deve essere maggiore di zero.");
		
		this.pesoSupportato = pesoSupportato;
	}

	public boolean getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(boolean disponibilita) {
		this.disponibilita = disponibilita;
	}
	
	
	@Override
	public String toString()
	{
		return String.format("Targa: %s%nPeso supportato: %.2f%nDisponibilita': %b%n", targa, pesoSupportato, disponibilita);
	}

}
