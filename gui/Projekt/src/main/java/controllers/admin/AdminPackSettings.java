package main.java.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.java.dao.PackageTypeDAO;
import main.java.entity.PackageType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPackSettings implements Initializable {

    @FXML
    private TextField smallSize;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<PackageType> listOfTypeInfo = PackageTypeDAO.getTypeInfo();
        smallSize.setText(listOfTypeInfo.get(0).getSize().replaceAll("x"," x "));

    }
    public void saveChanges(ActionEvent actionEvent) {
    }
}
