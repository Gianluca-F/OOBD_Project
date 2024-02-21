package DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DBConnection {
	
    private static DBConnection dbConnection = null;
    private Connection connection = null;
    
    private final String user = "postgres";
    private final String pwd = "superMachine";
    private final String port = "5432";
	private final String IP = "localhost";
	private final String nomeDB = "oobd_project";
	
	private String url = "jdbc:postgresql://" + IP + ":" + port + "/" + nomeDB;

    // costruttore private
    private DBConnection(){}

    // metodo pubblico per ottenere una istanza della classe privata
    public static DBConnection getDBConnection()
    {   
        if (dbConnection == null) 
        {
            dbConnection = new DBConnection();
        }
        
        return dbConnection;
    }
    
    // metodo pubblico per ottenere la connessione, in cui passo anche il nome dello schema a cui connettermi
    public Connection getConnectionBySchema(String schema_name)
    {   
        if(Objects.equals(schema_name, ""))
        {
        	throw new RuntimeException("Schema name non puo' essere vuoto.");
        }
           
        try
        {   // se la connessione non esiste oppure è stata chiusa
            if(connection == null || connection.isClosed())
            {   
            	// registra il driver
                Class.forName("org.postgresql.Driver");
                // chiama il DriverManager e chiedi la connessione
                url = url + "?currentSchema=" + schema_name;
                connection = DriverManager.getConnection(url, user, pwd);
            }
        }
        catch (ClassNotFoundException e)
        {
        	System.out.println("DB driver non trovato.");
        	System.out.println(e);
        }
        catch (SQLException e) 
        {
        	System.out.println("Connessione fallita.");
        	System.out.println(e);
        }

        return connection;
    }
    
}
