package main.java.controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PackageItem{

    @FXML
    private Label packageNumber;

    @FXML
    private Label packageStatus;

    @FXML
    private Label packageRecipient;

    @FXML
    private TextField itemId;

    //Method used for filling the labels in package preview
    public void setData(PackageTest tes){
        packageNumber.setText(tes.getPackageNumber());
        packageStatus.setText(tes.getStatus());
        packageRecipient.setText(tes.getSender());
        itemId.setText(String.valueOf(tes.getId()));
    }

    //Method used to get package number for query in db
    public int getId(){ return Integer.valueOf(itemId.getText()); }
}
