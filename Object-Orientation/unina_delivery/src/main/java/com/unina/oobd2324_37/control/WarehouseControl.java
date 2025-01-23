package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.WarehouseController;
import com.unina.oobd2324_37.entity.DAO.ArticoloDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.ArticoloDAOimp;

/**
 * This class is used to manage the WarehouseControl.
 */
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

    /**
     * This method is used to apply the filter to the table.
     * @param articolo The articolo name
     * @param soloDisponibile The availability of the articolo
     */
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

    /**
     * This method is used to check if the articolo is valid.
     * @param articolo The articolo name
     * @return True if the articolo is valid, false otherwise
     */
    private boolean isArticoloValid(String articolo) {
        return articolo != null && !articolo.isEmpty();
    }
}
