package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerPackages implements Initializable {

    @FXML
    private TableView<?> tableView;


    public void findPackages(MouseEvent mouseEvent) {
        System.out.println("managerPackages > findPackage");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        tableView.setPlaceholder(new Label("Brak danych"));
    }
}
