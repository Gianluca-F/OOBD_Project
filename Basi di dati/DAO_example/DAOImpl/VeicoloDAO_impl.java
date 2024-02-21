package DAOImpl;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.ResultSetMetaData; // Per stampare sulla console qualora desiderato
import java.util.ArrayList;

import DAO.VeicoloDAO;
import entity.Veicolo;

public class VeicoloDAO_impl implements VeicoloDAO {

	private Connection connection;
	
	//Prepared Statement
	private PreparedStatement insertVeicoloPS;
	private PreparedStatement updateVeicoloPS;
	private PreparedStatement deleteVeicoloPS;
	private PreparedStatement getAllPS;
	private PreparedStatement getByTargaPS;
	private PreparedStatement getByGEPesoSuppPS;
	private PreparedStatement getByGEPesoSuppAndDisponibilitaPS;
	private PreparedStatement getByDisponibilitaPS;
	
	
	public VeicoloDAO_impl(Connection connection) {

		this.connection = connection;

		//1 statement
		try
		{
			insertVeicoloPS = this.connection.prepareStatement(
				"INSERT INTO VEICOLI (targa, pesoSupportato, disponibilita) VALUES(?, ?, ?)");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		//2 statement
		try
		{
			updateVeicoloPS = this.connection.prepareStatement(
				"UPDATE VEICOLI SET targa = ?, pesoSupportato = ?, disponibilita = ? WHERE targa = ?");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		//3 statement
		try
		{
			deleteVeicoloPS = this.connection.prepareStatement(
				"DELETE FROM VEICOLI WHERE targa = ?");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		//4 statement
		try
		{
			getAllPS = this.connection.prepareStatement(
				"SELECT * FROM VEICOLI");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
		//5 statement
		try
		{
			getByTargaPS = this.connection.prepareStatement(
				"SELECT * FROM VEICOLI WHERE targa = ?");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		//6 statement
		try
		{
			getByGEPesoSuppPS = this.connection.prepareStatement(
				"SELECT * FROM VEICOLI WHERE pesoSupportato >= ?");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		//7 statement
		try
		{
			getByGEPesoSuppAndDisponibilitaPS = this.connection.prepareStatement(
				"SELECT * FROM VEICOLI WHERE pesoSupportato >= ? AND disponibilita = ?");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		//8 statement
		try
		{
			getByDisponibilitaPS = this.connection.prepareStatement(
				"SELECT * FROM VEICOLI WHERE disponibilita = ?");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void insertVeicolo(Veicolo veicolo) throws SQLException {
		insertVeicoloPS.setString(1, veicolo.getTarga());
		insertVeicoloPS.setDouble(2, veicolo.getPesoSupportato());
		insertVeicoloPS.setBoolean(3, veicolo.getDisponibilita());
	}

	@Override
	public void updateVeicolo(Veicolo veicolo, String targa) throws SQLException {
		updateVeicoloPS.setString(1, veicolo.getTarga());
		updateVeicoloPS.setDouble(2, veicolo.getPesoSupportato());
		updateVeicoloPS.setBoolean(3, veicolo.getDisponibilita());
		updateVeicoloPS.setString(4, targa);
	}

	@Override
	public void deleteVeicolo(String targa) throws SQLException {
		deleteVeicoloPS.setString(1, targa);
	}

	@Override
	public ArrayList<Veicolo> getAll() throws SQLException {
		
		ArrayList<Veicolo> veicoli = new ArrayList<Veicolo>();
		ResultSet rs = getAllPS.executeQuery(); // recupero il result set
		/*
		 * Nel caso in cui si voglia stampare sulla console la tabella: 
		 * 
			ResultSetMetaData rsmd = rs.getMetaData(); // accedo ai metadati
			
			System.out.println("Numero di colonne nella tabella Veicoli: " + rsmd.getColumnCount());
	        System.out.println();
	        String header = "";
	        for (int i = 0;  i < rsmd.getColumnCount(); i++){
	            header += rsmd.getColumnLabel(i+1) + "\t";
	        }
	        System.out.println(header); 
	     *
	     *  
		 */
        while(rs.next()) //verifico se ho una ulteriore tupla da analizzare
        {
        	String targ = rs.getString(1);
            double pesoSupp = rs.getDouble(2);
            boolean disp = rs.getBoolean(3);
            
            Veicolo veicolo = new Veicolo(targ, pesoSupp, disp);
            veicoli.add(veicolo);
        }
        rs.close();
	
		return veicoli;
	}

	@Override
	public Veicolo getByTarga(String targa) throws SQLException {
		Veicolo veicolo = null;
		ResultSet rs = null;
		
		getByTargaPS.setString(1, targa);
		rs = getByTargaPS.executeQuery();
		
		// ho sicuramente solo una tupla da analizzare
		String targ = rs.getString(1);
        double pesoSupp = rs.getDouble(2);
        boolean disp = rs.getBoolean(3);
        
        veicolo = new Veicolo(targ, pesoSupp, disp);
        rs.close();
		
		return veicolo;
	}

	@Override
	public ArrayList<Veicolo> getByGEPesoSupp(double pesoSupportato) throws SQLException {
		ArrayList<Veicolo> veicoli = new ArrayList<Veicolo>();
		ResultSet rs = null;
		
		getByGEPesoSuppPS.setDouble(1, pesoSupportato);
		rs = getByGEPesoSuppPS.executeQuery();
		
        while(rs.next()) //verifico se ho una ulteriore tupla da analizzare
        {
        	String targ = rs.getString(1);
            double pesoSupp = rs.getDouble(2);
            boolean disp = rs.getBoolean(3);
            
            Veicolo veicolo = new Veicolo(targ, pesoSupp, disp);
            veicoli.add(veicolo);
        }
        rs.close();
	
		return veicoli;
	}

	@Override
	public ArrayList<Veicolo> getByGEPesoSuppAndDisponibilita(double pesoSupportato, boolean disponibilita) throws SQLException {
		ArrayList<Veicolo> veicoli = new ArrayList<Veicolo>();
		ResultSet rs = null;
		
		getByGEPesoSuppAndDisponibilitaPS.setDouble(1, pesoSupportato);
		getByGEPesoSuppAndDisponibilitaPS.setBoolean(2, disponibilita);
		rs = getByGEPesoSuppAndDisponibilitaPS.executeQuery();
		
        while(rs.next()) //verifico se ho una ulteriore tupla da analizzare
        {
        	String targ = rs.getString(1);
            double pesoSupp = rs.getDouble(2);
            boolean disp = rs.getBoolean(3);
            
            Veicolo veicolo = new Veicolo(targ, pesoSupp, disp);
            veicoli.add(veicolo);
        }
        rs.close();
	
		return veicoli;
	}

	@Override
	public ArrayList<Veicolo> getByDisponibilita(boolean disponibilita) throws SQLException {
		ArrayList<Veicolo> veicoli = new ArrayList<Veicolo>();
		ResultSet rs = null;
		
		getByDisponibilitaPS.setBoolean(1, disponibilita);
		rs = getByDisponibilitaPS.executeQuery();
		
        while(rs.next()) //verifico se ho una ulteriore tupla da analizzare
        {
        	String targ = rs.getString(1);
            double pesoSupp = rs.getDouble(2);
            boolean disp = rs.getBoolean(3);
            
            Veicolo veicolo = new Veicolo(targ, pesoSupp, disp);
            veicoli.add(veicolo);
        }
        rs.close();
	
		return veicoli;
	}

}
