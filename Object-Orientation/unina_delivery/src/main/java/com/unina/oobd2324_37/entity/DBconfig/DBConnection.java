package com.unina.oobd2324_37.entity.DBconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static Connection connection = null;

    private static final String port = "5432";
    private static final String IP = "localhost";
    private static final String nomeDB = "postgres";
    private static final String schema = "unina_delivery";

    private static final String url = "jdbc:postgresql://" + IP + ":" + port + "/" +
                                                               nomeDB + "?currentSchema=" + schema;

    private DBConnection(){}

    public static Connection getInstance() throws SQLException {
        if(connection == null || connection.isClosed()) {
            connection = getConnectionBySchema();
        }
        return connection;
    }

    /**
     * Metodo per ottenere la connessione al DB
     * @return la connessione al DB
     */
    private static Connection getConnectionBySchema() {
        try {
            // registra il driver
            Class.forName("org.postgresql.Driver");
            // chiama il DriverManager e chiedi la connessione
            return DriverManager.getConnection(url, "postgres", "superMachine");
        }
        catch (ClassNotFoundException e) {
            System.out.println("DB driver non trovato.");
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
        catch (SQLException e) {
            System.out.println("Connessione fallita.");
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

}