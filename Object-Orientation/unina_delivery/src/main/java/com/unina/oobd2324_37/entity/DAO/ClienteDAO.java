package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Cliente;

/**
 * Interfaccia per la gestione dei dati relativi ai clienti
 */
public interface ClienteDAO {
    public Cliente getByEmailNPass(String username, String password) throws NullPointerException;

    public Cliente getById(String idCliente);
}
