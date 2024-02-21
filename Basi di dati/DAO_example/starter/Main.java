package starter;

import java.sql.*;
import DBConfig.DBConnection;
// import entity.Veicolo;

public class Main {
	
	public static void main(String[] args) {
		
		String nome_schema = "unina_delivery";
		DBConnection dbConnection = null;
        Connection connection = null;
        
        try 
        {
	        // istanzia la classe di connessione al DB
	        dbConnection = DBConnection.getDBConnection();
	        // recupera la connessione di uno schema che conosco
	        connection = dbConnection.getConnectionBySchema(nome_schema);
	
	        if (connection == null) 
	        {
	            System.out.println("Connessione NON riuscita!");
	            System.exit(0);
	        }
	        System.out.println("Connessione OK!");
	        
	        // definisce lo statement
	        Statement stmt = null;
	        
            // crea uno statement semplice
            stmt = connection.createStatement();

            //imposta il path
            String comando1 ="SET search_path TO " + nome_schema;
            stmt.executeUpdate(comando1);

            stmt.close();
            connection.close();
        } 
        catch (SQLException e) 
        {
            //e.printStackTrace();	// <- per stampare tutta la catena di errori
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
		
	}
}

