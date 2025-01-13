package com.unina.oobd2324_37.entity.DBconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe per la connessione al DB
 */
public final class DBConnection {

    private static Connection connection = null;

    private static final String port = "5432";
    private static final String IP = "localhost";
    private static final String nomeDB = "postgres";
    private static final String schema = "unina_delivery";

    private static final String url = "jdbc:postgresql://" + IP + ":" + port + "/" +
                                                               nomeDB + "?currentSchema=" + schema;

    /**
     * Costruttore privato per evitare l'istanziazione della classe
     */
    private DBConnection(){}

    /**
     * Metodo per ottenere la connessione al DB
     * @return la connessione al DB
     * @throws SQLException
     */
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

    /**
     * Metodo per chiudere la connessione al DB
     */
    public static void closeConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
        }
    }
}