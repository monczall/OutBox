package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;

public class ManagerCouriers {

    @FXML
    public AnchorPane courierOptions;

    public void addCourier(MouseEvent mouseEvent) throws IOException {
        System.out.println("ManagerCouriers > addCourier");
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../resources/view/manager/managerCouriersAdd.fxml", courierOptions);
    }

    public void deleteCourier(MouseEvent mouseEvent) throws IOException {
        System.out.println("ManagerCouriers > deleteCourier");
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../resources/view/manager/managerCouriersDelete.fxml", courierOptions);
    }

    public void editCourier(MouseEvent mouseEvent) throws IOException {
        System.out.println("ManagerCouriers > editCourier");
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../resources/view/manager/managerCouriersEdit.fxml", courierOptions);
    }

    public void mapCourier(MouseEvent mouseEvent) throws IOException {
        System.out.println("ManagerCouriers > mapCourier");
        courierOptions.setVisible(true);
        SceneManager.loadScene("../../resources/view/manager/managerCouriersTerrain.fxml", courierOptions);
    }
}
