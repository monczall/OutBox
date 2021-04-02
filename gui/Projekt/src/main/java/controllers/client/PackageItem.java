package main.java.controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class PackageItem{

    @FXML
    private Label packageNumber;

    @FXML
    private Label packageStatus;

    @FXML
    private Label packageRecipient;

    //Method used for filling the labels in package preview
    public void setData(PackageTest tes){
        packageNumber.setText(tes.getPackageNumber());
        packageStatus.setText(tes.getStatus());
        packageRecipient.setText(tes.getSender());
    }

    //Method used to get package number for query in db
    public String getNumber(){
        return packageNumber.getText();
    }
}
