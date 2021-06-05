package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;
import main.java.controllers.auth.Login;
import main.java.dao.AreasDAO;
import main.java.dao.UsersDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerCouriers implements Initializable {

    @FXML
    public AnchorPane courierOptions;
    @FXML
    public Button areaButton;
    @FXML
    public Button courierButton;

    /**
     * Loading the add courier scene
     */
    public void addCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("main/resources/view/manager/managerCouriersAdd.fxml", courierOptions);
    }

    /**
     * Loading the delete courier scene
     */
    public void deleteCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("main/resources/view/manager/managerCouriersDelete.fxml", courierOptions);
    }

    /**
     * Loading the edit data courier scene
     */
    public void editCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("main/resources/view/manager/managerCouriersEdit.fxml", courierOptions);
    }

    /**
     * Loading the unallocated packages scene
     */
    public void areaCourier(MouseEvent mouseEvent) throws IOException {
        courierOptions.setVisible(true);
        SceneManager.loadScene("main/resources/view/manager/managerNullCourier.fxml", courierOptions);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (!AreasDAO.getAreasById(UsersDAO.getUsersById(Login.getUserID()).get(0).getAreaId()).get(0).getName().contains(
                UsersDAO.getUsersById(Login.getUserID()).get(0).getAreasByAreaId().getVoivodeship())) {
            areaButton.setVisible(false);
            courierButton.setPrefWidth(435);
        }
    }
}
