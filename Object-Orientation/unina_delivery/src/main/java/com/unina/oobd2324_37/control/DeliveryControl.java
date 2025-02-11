package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.DeliveryController;
import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DAO.SpedizioneDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.CorriereDAOimp;
import com.unina.oobd2324_37.entity.DAOimplementation.OrdineDAOimp;
import com.unina.oobd2324_37.entity.DAOimplementation.SpedizioneDAOimp;
import com.unina.oobd2324_37.entity.DAOimplementation.VeicoloDAOimp;
import com.unina.oobd2324_37.entity.DTO.Corriere;
import com.unina.oobd2324_37.entity.DTO.Ordine;
import com.unina.oobd2324_37.entity.DTO.Veicolo;

import java.util.List;

public class DeliveryControl {

    private static DeliveryControl instance = null;
    private DeliveryController deliveryController;

    /**
     * Private constructor to avoid instantiation.
     */
    private DeliveryControl() {}

    /**
     * This method is used to get the instance (singleton) of the DeliveryControl class.
     * @return The instance of the DeliveryControl class
     */
    public static DeliveryControl getInstance() {
        if(instance == null) {
            instance = new DeliveryControl();
        }
        return instance;
    }

    /**
     * This method is used to initialize the DeliveryControl class.
     */
    public void initialize(DeliveryController deliveryController) {
        this.deliveryController = deliveryController;
    }

    public void updateTable() {
        OrdineDAO ordineDAO = new OrdineDAOimp();

        deliveryController.updateTable(ordineDAO::getNotDelivered);
    }

    public List<Corriere> getCorrieriDispobibili() {
        return new CorriereDAOimp().getDisponibili();
    }

    public List<Veicolo> getVeicoliDisponibili() {
        return new VeicoloDAOimp().getDisponibili();
    }

    public void generateDelivery(List<Ordine> selectedOrders, String corriere, String veicolo) {
        SpedizioneDAO spedizioneDAO = new SpedizioneDAOimp();

        if(spedizioneDAO.insert(DashboardControl.getInstance().getOperatore(), selectedOrders, corriere, veicolo)) {
            deliveryController.showDeliveryGenerated();
        } else {
            deliveryController.showDeliveryNotGenerated();
        }
    }
}
