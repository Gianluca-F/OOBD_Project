package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.WarehouseController;
import com.unina.oobd2324_37.entity.DAO.ArticoloDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.ArticoloDAOimp;

public class WarehouseControl {

    private static WarehouseControl instance = null;
    private WarehouseController warehouseController;

    /**
     * Private constructor to avoid instantiation.
     */
    private WarehouseControl() {}

    /**
     * This method is used to get the instance (singleton) of the WarehouseControl class.
     * @return The instance of the WarehouseControl class
     */
    public static WarehouseControl getInstance() {
        if(instance == null) {
            instance = new WarehouseControl();
        }
        return instance;
    }

    /**
     * This method is used to initialize the WarehouseControl class.
     * @param warehouseController The instance of WarehouseController class
     */
    public void initialize(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
    }

    public void applyFilter(String articolo, Boolean soloDisponibile) {
        ArticoloDAO articoloDAO = new ArticoloDAOimp();

        // without filter
        if(!isArticoloValid(articolo) && !soloDisponibile) {
            warehouseController.updateTable(articoloDAO::getAll);
            return;
        }

        // with just articolo filter
        if(isArticoloValid(articolo) && !soloDisponibile) {
            warehouseController.updateTable(() -> articoloDAO.getByNome(articolo));
            return;
        }

        // with just disponibile filter
        if(!isArticoloValid(articolo) && soloDisponibile) {
            warehouseController.updateTable(articoloDAO::getByDisponibilita);
            return;
        }

        // with articolo and disponibile filter
        if(isArticoloValid(articolo) && soloDisponibile) {
            warehouseController.updateTable(() -> articoloDAO.getByNomeNDisponibilita(articolo));
        }
    }

    private boolean isArticoloValid(String articolo) {
        return articolo != null && !articolo.isEmpty();
    }
}
