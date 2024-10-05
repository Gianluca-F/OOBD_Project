package com.unina.oobd2324_37.entity.DBconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public final class DBConnection {

    private static Connection connection = null;

    private static final String port = "5432";
    private static final String IP = "localhost";
    private static final String nomeDB = "postgres";

    private static String url = "jdbc:postgresql://" + IP + ":" + port + "/" + nomeDB;

    private DBConnection(){}

    /**
     * Metodo per ottenere la connessione al DB
     * @return la connessione al DB
     */
    public static Connection getConnectionBySchema(final String schemaName) {
        if(Objects.equals(schemaName, "")) {
            throw new RuntimeException("Schema name non puo' essere vuoto.");
        }

        try {   // se la connessione non esiste oppure è stata chiusa
            if(connection == null || connection.isClosed()) {
                // registra il driver
                Class.forName("org.postgresql.Driver");
                // chiama il DriverManager e chiedi la connessione
                url = url + "?currentSchema=" + schemaName;
                connection = DriverManager.getConnection(url, "postgres", "superMachine");
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("DB driver non trovato.");
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("Connessione fallita.");
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
        }

        return connection;
    }

}