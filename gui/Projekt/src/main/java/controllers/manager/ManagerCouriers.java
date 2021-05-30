package main.java.controllers.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.SceneManager;

import java.io.IOException;

public class ManagerCouriers {

    @FXML
    public AnchorPane courierOptions;

    /**
     * Loading the add courier scene
     */
    public void addCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../../resources/view/manager/managerCouriersAdd.fxml", courierOptions);
    }

    /**
     * Loading the delete courier scene
     */
    public void deleteCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../../resources/view/manager/managerCouriersDelete.fxml", courierOptions);
    }

    /**
     * Loading the edit data courier scene
     */
    public void editCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../../resources/view/manager/managerCouriersEdit.fxml", courierOptions);
    }

    /**
     * Loading the unallocated packages scene
     */
    public void areaCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../../resources/view/manager/managerNullCourier.fxml", courierOptions);
    }
}
