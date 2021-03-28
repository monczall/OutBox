package main.java.controllers.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PackageItem implements Initializable {

    @FXML
    private Label packageNumber;

    @FXML
    private Label packageStatus;

    @FXML
    private Label packageRecipient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setData(PackageTest tes){
        packageNumber.setText(tes.getPackageNumber());
        packageStatus.setText(tes.getStatus());
        packageRecipient.setText(tes.getSender());
    }
}
