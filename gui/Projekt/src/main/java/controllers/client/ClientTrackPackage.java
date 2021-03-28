package main.java.controllers.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientTrackPackage implements Initializable {

    @FXML
    private VBox packageLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Testing how ClientTrackPackage view will look like with example data
        List<PackageTest> list = new ArrayList<>(packageTest());
        for(int i=0; i<list.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../../resources/view/client/packageItem.fxml"));

            try {
                Pane pane = fxmlLoader.load();

                pane.setPadding(new Insets(70,0,100,70));

                PackageItem packageItem = fxmlLoader.getController();
                packageItem.setData(list.get(i));
                packageLayout.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //Filing list with example data
    private List<PackageTest> packageTest(){
        List<PackageTest> ls = new ArrayList<>();
        PackageTest tes = new PackageTest();

        tes.setPackageNumber("3232323232");
        tes.setSender("FILIP");
        tes.setStatus("ZAREJESTROWANA");
        ls.add(tes);

        tes = new PackageTest();
        tes.setPackageNumber("121212121");
        tes.setSender("ANDRZEJ");
        tes.setStatus("DOSTARCZONA");
        ls.add(tes);

        tes = new PackageTest();
        tes.setPackageNumber("5151515151");
        tes.setSender("STEVE");
        tes.setStatus("DOSTARCZONA");
        ls.add(tes);

        return ls;
    }
}
