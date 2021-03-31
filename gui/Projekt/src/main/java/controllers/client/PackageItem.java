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

    public void setData(PackageTest tes){
        packageNumber.setText(tes.getPackageNumber());
        packageStatus.setText(tes.getStatus());
        packageRecipient.setText(tes.getSender());
    }

    @FXML
    public void showMore(ActionEvent event){
//        ClientTrackPackage clientTrackPackage = new ClientTrackPackage();
//        clientTrackPackage.test();
    }
}
