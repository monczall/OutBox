package main.java.controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.features.Animations;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientHistoryPackage implements Initializable {

    @FXML
    private AnchorPane trackPackagePane;

    @FXML
    private VBox packageLayout;

    @FXML
    private AnchorPane moreInformationPane;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnPlus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnBack.setVisible(false);
        moreInformationPane.setTranslateX(+850);

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

        tes.setPackageNumber("8688686868");
        tes.setSender("ANDRZEJ");
        tes.setStatus("ODEBRANA");
        ls.add(tes);

        tes = new PackageTest();
        tes.setPackageNumber("6632626262");
        tes.setSender("ANDRZEJ");
        tes.setStatus("ODEBRANA");
        ls.add(tes);

        return ls;
    }

    @FXML
    void showMore(ActionEvent event) {
        btnPlus.setDisable(true);
        btnBack.setVisible(true);
        Animations.changePane(trackPackagePane,moreInformationPane,-850,0.5);
    }

    @FXML
    void backToTrackPackage(ActionEvent event) {
        btnBack.setVisible(false);
        btnPlus.setDisable(false);
        Animations.changePane(moreInformationPane,trackPackagePane,+850,0.5);
    }
}
