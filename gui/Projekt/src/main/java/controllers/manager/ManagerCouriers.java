package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;

public class ManagerCouriers {

    @FXML
    public AnchorPane courierOptions;

    public void addCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../../resources/view/manager/managerCouriersAdd.fxml", courierOptions);
    }

    public void deleteCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../../resources/view/manager/managerCouriersDelete.fxml", courierOptions);
    }

    public void editCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../../resources/view/manager/managerCouriersEdit.fxml", courierOptions);
    }

    public void areaCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        System.out.println("AREA COURIER");
        SceneManager.loadScene("../../../resources/view/manager/managerNullCourier.fxml", courierOptions);
    }
}
