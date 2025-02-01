package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Articolo;

import java.util.List;

public interface ArticoloDAO {
    public List<Articolo> getAll();

    public Articolo getById(String id);

    public List<Articolo> getByNome(String articolo);

    public List<Articolo> getByDisponibilita();

    public List<Articolo> getByNomeNDisponibilita(String articolo);

    public boolean update(Articolo articolo);
}
