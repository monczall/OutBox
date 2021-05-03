package main.java.controllers.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    public void setData(PopulatePackageItem populatePackageItem){
        packageNumber.setText(populatePackageItem.getPackageNumber());
        packageStatus.setText(populatePackageItem.getStatus());
        packageRecipient.setText(populatePackageItem.getSender());
        itemId.setText(String.valueOf(populatePackageItem.getId()));
    }

    //Method used to get package number for query in db
    public int getId(){ return Integer.valueOf(itemId.getText()); }
}
