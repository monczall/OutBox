package main.java.controllers.manager;

import javafx.scene.input.MouseEvent;
import main.java.SceneManager;

public class ManagerHome {

    public void logout(MouseEvent mouseEvent) {
        System.out.println("ManagerHome > logout");
        SceneManager.renderScene("login");
    }

    public void openCouriers(MouseEvent mouseEvent) {
        System.out.println("ManagerHome > Couriers");
    }

    public void openPackages(MouseEvent mouseEvent) {
        System.out.println("ManagerHome > Packages");
    }

    public void openReports(MouseEvent mouseEvent) {
        System.out.println("ManagerHome > Raports");
    }

    public void openSettings(MouseEvent mouseEvent) {
        System.out.println("ManagerHome > Settings");
    }
}
